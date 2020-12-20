package ru.easyspace.spacelaunch.rockets;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.easyspace.spacelaunch.rockets.rocketLibraryAPI.RocketJSON;
import ru.easyspace.spacelaunch.rockets.rocketLibraryAPI.RocketsDB;
import ru.easyspace.spacelaunch.spacepicture.SpacePictureJSON;


public class RocketsRepository implements RocketsApiManager.onRocketsUpdatedListener {
    private Application mApplication;
    public RocketsRepository(Application application) {
        mApplication=application;
    }
    public void updateRockets(final MutableLiveData<List<RocketJSON>> Rockets) {
        RocketsDBManager rocketsDBManager = RocketsDBManager.getInstance(mApplication);
        rocketsDBManager.getRocketsFromDatabase(new RocketsDBManager.rocketDBCallback() {
            @Override
            public void onSuccess(List<RocketJSON> rocketsJSONList) {
                Rockets.postValue(rocketsJSONList);
            }

            @Override
            public void onFailure() {
                RocketsApiManager ApiManager = RocketsApiManager.getInstance(RocketsRepository.this);
                ApiManager.performRocketsRequest(Rockets);
            }
        });

    }
    public void updateRocketsInDatabase(List<RocketJSON> Rockets) {
        RocketsDBManager  rocketsDBManager = RocketsDBManager.getInstance(mApplication);
        rocketsDBManager.InsertRocketsToDataBase(Rockets);

    }
    public void updateRocketsfromNetwork(final MutableLiveData<List<RocketJSON>> Rockets){
        RocketsApiManager ApiManager = RocketsApiManager.getInstance(RocketsRepository.this);
        ApiManager.performRocketsRequest(Rockets);
    }
    @Override
    public void onRocketsUpdated(List<RocketJSON> Rockets) {
        this.updateRocketsInDatabase(Rockets);
    }
}
