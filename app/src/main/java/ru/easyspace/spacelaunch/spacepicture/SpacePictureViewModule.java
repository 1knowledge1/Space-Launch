package ru.easyspace.spacelaunch.spacepicture;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SpacePictureViewModule extends AndroidViewModel {
    private MutableLiveData<SpacePictureJSON> mSpacePicture = new MutableLiveData<>();
    private Application mApplication;
    private SpacePictureRepository mRepository;
    public SpacePictureViewModule(@NonNull Application application) {
        super(application);
        mApplication=application;
        mRepository=new SpacePictureRepository(mApplication);
    }
    public LiveData<SpacePictureJSON> getSpacePicture()
    {
        return mSpacePicture;
    }
    public void updatePicture() {
         mRepository.updateSpacePicture(mSpacePicture);
    }


}
