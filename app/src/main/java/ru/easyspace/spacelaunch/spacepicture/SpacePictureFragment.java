package ru.easyspace.spacelaunch.spacepicture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import ru.easyspace.spacelaunch.R;

public class SpacePictureFragment extends Fragment {
    private ImageView imageSpace;
    private TextView titleText;
    private TextView explorationText;
    private Button ApiButton;
    private SpacePictureViewModule mViewModule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_space_picture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        imageSpace = view.findViewById(R.id.spaceImageview);
        titleText = view.findViewById(R.id.titleTextview);
        explorationText = view.findViewById(R.id.explorationTextview);
        ApiButton = view.findViewById(R.id.APIbutton);
        mViewModule=new ViewModelProvider(getActivity()).get(SpacePictureViewModule.class);
        mViewModule.getSpacePicture().observe(this.getViewLifecycleOwner(), new Observer<SpacePictureJSON>() {
            @Override
            public void onChanged(SpacePictureJSON spacePictureJSON) {
                Glide.with(SpacePictureFragment.this)
                        .load(spacePictureJSON.url)
                        .into(SpacePictureFragment.this.imageSpace);
                SpacePictureFragment.this.titleText.setText(spacePictureJSON.title);
                SpacePictureFragment.this.explorationText.setText(spacePictureJSON.explanation);
            }
        });
        ApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModule.requestPicture();

            }
        });

    }
}
