package ru.easyspace.spacelaunch.rockets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.easyspace.spacelaunch.R;

public class RocketsFragment extends Fragment {

    public static RocketsFragment newInstance() {
        return new RocketsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rockets, container, false);
    }
}