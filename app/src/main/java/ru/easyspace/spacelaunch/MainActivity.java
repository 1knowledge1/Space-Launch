package ru.easyspace.spacelaunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import ru.easyspace.spacelaunch.launches.UpLaunchesFragment;
import ru.easyspace.spacelaunch.rockets.RocketsFragment;
import ru.easyspace.spacelaunch.spacepicture.SpacePictureFragment;
import ru.easyspace.spacelaunch.test.TestFragment;

public class MainActivity extends AppCompatActivity {
    int mItemId;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_test:
                    loadFragment(TestFragment.newInstance());
                    mItemId = R.id.page_test;
                    return true;
                case R.id.page_launches:
                    loadFragment(new UpLaunchesFragment());
                    mItemId = R.id.page_launches;
                    return true;
                case R.id.page_space_picture:
                    loadFragment(new SpacePictureFragment());
                    mItemId = R.id.page_space_picture;
                    return true;
                case R.id.page_rockets:
                    loadFragment(RocketsFragment.newInstance());
                    mItemId = R.id.page_rockets;
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitAllowingStateLoss();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState==null) {
           navigation.setSelectedItemId(R.id.page_launches);
        } else {
           navigation.setSelectedItemId(savedInstanceState.getInt("SelectedItem"));
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SelectedItem", mItemId);
    }
}
