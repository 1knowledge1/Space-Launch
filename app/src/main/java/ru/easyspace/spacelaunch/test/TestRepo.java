package ru.easyspace.spacelaunch.test;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TestRepo {

    private final static MutableLiveData<List<SpaceTest>> mTests = new MutableLiveData<>();

    static {
        mTests.setValue(Collections.<SpaceTest>emptyList());
    }

    private FirebaseFirestore db;
    private SharedPreferences prefs;

    public TestRepo(Application application) {
        db = FirebaseFirestore.getInstance();
        prefs = application.getSharedPreferences("TestResult", Context.MODE_PRIVATE);
    }

    public LiveData<List<SpaceTest>> getTests() {
        return mTests;
    }

    public void refresh() {
        db.collection("tests")
                .whereEqualTo("ready", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<TestPlain> testPlains =  task.getResult().toObjects(TestPlain.class);
                            List<SpaceTest> tests = transform(testPlains);
                            mTests.postValue(tests);
                        } else {
                            Log.w("Firestore", "Error getting documents.", task.getException());
                            mTests.postValue(null);
                        }
                    }
                });
    }

    public void insertTestScore(String testName, int score) {
        if (score > getTestScore(testName)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(testName, score);
            editor.apply();
        }
    }

    private List<SpaceTest> transform (List<TestPlain> plains) {
        List<SpaceTest> result = new ArrayList<>();
        for (TestPlain testPlain : plains) {
            if (testPlain == null) { continue; }
            SpaceTest test = map(testPlain);
            result.add(test);
        }
        return result;
    }

    private SpaceTest map(TestPlain plain) {
        int score = getTestScore(plain.name);
        return new SpaceTest(plain.name, plain.image, plain.description,
                plain.success_image, plain.failure_image, plain.success,
                plain.failure, plain.questions, score);
    }

    private int getTestScore(String testName) {
        return prefs.getInt(testName, 0);
    }
}
