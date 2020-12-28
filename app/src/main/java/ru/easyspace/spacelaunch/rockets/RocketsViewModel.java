package ru.easyspace.spacelaunch.rockets;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.easyspace.spacelaunch.rockets.rocketLibraryAPI.RocketJSON;


public class RocketsViewModel extends AndroidViewModel {
    private MutableLiveData<List<RocketJSON>> mRockets = new MutableLiveData<>();
    private Application mApplication;
    private RocketsRepository mRepository;

    public RocketsViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mRepository = new RocketsRepository(mApplication);
    }

    public LiveData<List<RocketJSON>> getRockets(){
        return mRockets;
    }

    public void updateRockets(){
             mRepository.updateRockets(mRockets);
    }

    public void updateRocketsfromNetwork(){
        mRepository.updateRocketsfromNetwork(mRockets);
    }

    public void updateRocketsSearch(String searchtext) {
        mRepository.updateRocketsSearch(searchtext,mRockets);
    }
}
