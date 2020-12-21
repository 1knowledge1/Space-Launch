package ru.easyspace.spacelaunch.test;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class TestViewModel extends AndroidViewModel {

    private TestRepo repo;
    private LiveData<List<SpaceTest>> tests;

    public TestViewModel(@NonNull Application application) {
        super(application);
        repo = new TestRepo(application);
        tests = repo.getTests();
    }

    public LiveData<List<SpaceTest>> getTests() {
        return tests;
    }

    public void refresh() {
        repo.refresh();
    }

    public void insertTestScore(String testName, int score) {
        repo.insertTestScore(testName, score);
    }
}
