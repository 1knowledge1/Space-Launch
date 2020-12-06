package ru.easyspace.spacelaunch.spacepicture;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpacePictureRepository implements SpacePictureApiManager.onSpacePictureUpdatedListener{

    private Application mApplication;

    public SpacePictureRepository(Application application) {
        mApplication=application;


    }

    public void updateSpacePicture(final MutableLiveData<SpacePictureJSON> SpacePicture) {
        SpacePictureDBManager spacePictureDBManager = SpacePictureDBManager.getInstance(mApplication);
        spacePictureDBManager.getPictureFromDatabase(new SpacePictureDBManager.pictureDBCallback() {
            @Override
            public void onSuccess(SpacePictureJSON SpacePictureDB) {
                SpacePicture.postValue(SpacePictureDB);
                
            }

            @Override
            public void onFailure() {
                SpacePictureApiManager ApiManager=SpacePictureApiManager.getInstance(SpacePictureRepository.this);
                ApiManager.performSpacePictureRequest(SpacePicture);

            }
        });
    }
    public void updatePictureInDatabase(SpacePictureJSON SpacePicture) {
        SpacePictureDBManager spacePictureDBManager = SpacePictureDBManager.getInstance(mApplication);
        spacePictureDBManager.InsertPictureToDataBase(SpacePicture);

    }
    @Override
    public void onSpacePictureUpdated(SpacePictureJSON SpacePicture) {
        this.updatePictureInDatabase(SpacePicture);
    }


}
