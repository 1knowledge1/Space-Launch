package ru.easyspace.spacelaunch.launches;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.easyspace.spacelaunch.R;

public class UpLaunchesFragment extends Fragment {

    public static UpLaunchesFragment newInstance() {
        return new UpLaunchesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_up_launches, container, false);
    }
}
