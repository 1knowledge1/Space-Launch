package ru.easyspace.spacelaunch.spacepicture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SpacePictureViewModule extends AndroidViewModel {
    private MutableLiveData<SpacePictureJSON> mSpacePicture = new MutableLiveData<>();
    public SpacePictureViewModule(@NonNull Application application) {
        super(application);
    }
    public LiveData<SpacePictureJSON> getSpacePicture() {
        return mSpacePicture;
    }
    public void requestPicture() {
         SpacePictureRepository Repository=SpacePictureRepository.getInstance();
         Repository.performSpacePictureRequest(mSpacePicture);
    }
}
