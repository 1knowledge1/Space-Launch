package ru.easyspace.spacelaunch.spacepicture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.easyspace.spacelaunch.R;

public class SpacePictureFragment extends Fragment {
    private ImageView imageSpace;
    private TextView titleText;
    private TextView explorationText;
    private TextView dateText;
    private TextView copyrightText;
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
        dateText = view.findViewById(R.id.dateTextview);
        copyrightText = view.findViewById(R.id.copyrightTextview);
        mViewModule = new ViewModelProvider(getActivity()).get(SpacePictureViewModule.class);
        mViewModule.getSpacePicture().observe(this.getViewLifecycleOwner(), new Observer<SpacePictureJSON>() {
            @Override
            public void onChanged(SpacePictureJSON spacePictureJSON) {
                if (spacePictureJSON == null) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            "Ошибка загрузки. Проверьте подключение к сети.", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                Glide.with(SpacePictureFragment.this)
                        .load(spacePictureJSON.url)
                        .into(SpacePictureFragment.this.imageSpace);
                SimpleDateFormat apidateformat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat picturedateformat = new SimpleDateFormat("MMM dd, yyyy");
                try {
                    Date pictureData = apidateformat.parse(spacePictureJSON.date);
                    SpacePictureFragment.this.dateText.setText(picturedateformat.format(pictureData));
                } catch (ParseException e) {
                    SpacePictureFragment.this.dateText.setText("wrong date format");
                }
                SpacePictureFragment.this.copyrightText.setText(spacePictureJSON.copyright);
                SpacePictureFragment.this.titleText.setText(spacePictureJSON.title);
                SpacePictureFragment.this.explorationText.setText(spacePictureJSON.explanation);
            }
        });

        if (mViewModule.getSpacePicture().getValue() == null) {
             mViewModule.updatePicture();
        }
    }
}
