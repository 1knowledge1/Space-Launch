package ru.easyspace.spacelaunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.easyspace.spacelaunch.launches.DetailedUpLaunchFragment;
import ru.easyspace.spacelaunch.launches.UpLaunchesFragment;
import ru.easyspace.spacelaunch.launches.UpcomingLaunch;
import ru.easyspace.spacelaunch.rockets.RocketsFragment;
import ru.easyspace.spacelaunch.spacepicture.SpacePictureFragment;
import ru.easyspace.spacelaunch.test.QuestionFragment;
import ru.easyspace.spacelaunch.test.ResultFragment;
import ru.easyspace.spacelaunch.test.SpaceTest;
import ru.easyspace.spacelaunch.test.TestFragment;


public class MainActivity extends AppCompatActivity implements StartFragmentListener {

    private static final String SAVED_STATE_CONTAINER_KEY = "ContainerKey";
    private static final String SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey";
    private SparseArray<Fragment.SavedState> savedStateSparseArray = new SparseArray<>();
    int currentSelectItemId;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_test:
                    swapFragments(R.id.page_test, "Test", new TestFragment());
                    return true;
                case R.id.page_launches:
                    swapFragments(R.id.page_launches, "Launches", new UpLaunchesFragment());
                    return true;
                case R.id.page_space_picture:
                    swapFragments(R.id.page_space_picture, "Picture", new SpacePictureFragment());
                    return true;
                case R.id.page_rockets:
                    swapFragments(R.id.page_rockets, "Rockets", new RocketsFragment());
                    return true;
            }
            return false;
        }
    };

    private void swapFragments(int newSelectItemId, String key, Fragment fragment) {
        if (getSupportFragmentManager().findFragmentByTag(key) == null) {
            savedFragmentState();
            currentSelectItemId = newSelectItemId;
            createFragment(newSelectItemId, key, fragment);
        }
    }

    private void savedFragmentState() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment != null) {
            savedStateSparseArray.put(currentSelectItemId,
                    getSupportFragmentManager().saveFragmentInstanceState(currentFragment));
        }
    }

    private void createFragment(int newSelectedItemId, String key, Fragment fragment) {
        fragment.setInitialSavedState(savedStateSparseArray.get(newSelectedItemId));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, key)
                .commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState != null) {
            SparseArray<Fragment.SavedState> savedState = savedInstanceState
                    .getSparseParcelableArray(SAVED_STATE_CONTAINER_KEY);
            if (savedState != null) {
                savedStateSparseArray = savedState;
            }
            currentSelectItemId = savedInstanceState.getInt(SAVED_STATE_CURRENT_TAB_KEY);
        } else {
            navigation.setSelectedItemId(R.id.page_launches);
            currentSelectItemId = R.id.page_launches;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSparseParcelableArray(SAVED_STATE_CONTAINER_KEY, savedStateSparseArray);
        outState.putInt(SAVED_STATE_CURRENT_TAB_KEY, currentSelectItemId);
    }

    @Override
    public void startDetailedLaunchFragment(UpcomingLaunch launch) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, DetailedUpLaunchFragment.newInstance(launch))
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    @Override
    public void startQuestionFragment(SpaceTest test) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, QuestionFragment.newInstance(test))
                .commitAllowingStateLoss();
    }

    @Override
    public void startResultFragment(SpaceTest test, int score) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ResultFragment.newInstance(test, score))
                .commitAllowingStateLoss();
    }

    @Override
    public void returnTestFragment(String testName, int score) {
        if (testName == null) {
            createFragment(R.id.page_test, "Test", new TestFragment());
        } else {
            createFragment(R.id.page_test, "Test", TestFragment.newInstance(testName, score));
        }
    }
}
