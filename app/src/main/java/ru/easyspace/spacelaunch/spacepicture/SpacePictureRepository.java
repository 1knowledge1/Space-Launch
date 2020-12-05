package ru.easyspace.spacelaunch.spacepicture;

import androidx.lifecycle.MutableLiveData;

public class SpacePictureRepository {
    private static final SpacePictureRepository INSTANCE = new SpacePictureRepository();

    static SpacePictureRepository getInstance() {
        return INSTANCE;
    }

    public void performSpacePictureRequest(final MutableLiveData<SpacePictureJSON> SpacePicture) {
        SpacePictureApiManager ApiManager = SpacePictureApiManager.getInstance();
        ApiManager.performSpacePictureRequest(SpacePicture);
    }
}
