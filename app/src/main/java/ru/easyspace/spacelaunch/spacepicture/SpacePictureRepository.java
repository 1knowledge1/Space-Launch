package ru.easyspace.spacelaunch.spacepicture;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SpacePictureRepository implements SpacePictureApiManager.onSpacePictureUpdatedListener{

    private Application mApplication;

    public SpacePictureRepository(Application application) {
        mApplication = application;
    }

    private boolean checkDate(Calendar d1, Calendar d2) {
        if (d1.get(Calendar.YEAR) != d2.get(Calendar.YEAR)) {
            return false;
        }
        if (d1.get(Calendar.MONTH) != d2.get(Calendar.MONTH)) {
            return false;
        }
        if (d1.get(Calendar.DAY_OF_MONTH) != d2.get(Calendar.DAY_OF_MONTH)) {
            return false;
        }
        return true;
    }

    public void updateSpacePicture(final MutableLiveData<SpacePictureJSON> SpacePicture) {
        SpacePictureDBManager spacePictureDBManager = SpacePictureDBManager.getInstance(mApplication);
        spacePictureDBManager.getPictureFromDatabase(new SpacePictureDBManager.pictureDBCallback() {

            @Override
            public void onSuccess(SpacePictureJSON SpacePictureDB) {
                SimpleDateFormat apidateformat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar currentCalendar = new GregorianCalendar();
                Calendar pictureCalendar = new GregorianCalendar();
                try {
                    Date pictureData = apidateformat.parse(SpacePictureDB.date);
                    currentCalendar.setTime(new Date());
                    pictureCalendar.setTime(pictureData);
                } catch (ParseException e) {
                    Log.e("Database", "Exception " + e);
                }
                if (checkDate(currentCalendar, pictureCalendar)) {
                    SpacePicture.postValue(SpacePictureDB);
                } else {
                    SpacePictureApiManager ApiManager = SpacePictureApiManager.getInstance(SpacePictureRepository.this);
                    ApiManager.performSpacePictureRequest(SpacePicture);
                }
            }

            @Override
            public void onFailure() {
                SpacePictureApiManager ApiManager = SpacePictureApiManager.getInstance(SpacePictureRepository.this);
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
