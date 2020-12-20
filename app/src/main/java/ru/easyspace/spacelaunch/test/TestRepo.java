package ru.easyspace.spacelaunch.test;

import android.app.Application;
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

    public TestRepo(Application application) {
        db = FirebaseFirestore.getInstance();
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
        return new SpaceTest(plain.name, plain.image, plain.description,
                plain.success_image, plain.failure_image, plain.success,
                plain.failure, plain.questions, 0);
    }
}
