package ru.easyspace.spacelaunch.rockets;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

import ru.easyspace.spacelaunch.RoomDatabase;
import ru.easyspace.spacelaunch.rockets.database.RocketsDAO;
import ru.easyspace.spacelaunch.rockets.rocketLibraryAPI.RocketJSON;
import ru.easyspace.spacelaunch.rockets.rocketLibraryAPI.RocketsDB;
import ru.easyspace.spacelaunch.spacepicture.SpacePictureDBManager;
import ru.easyspace.spacelaunch.spacepicture.SpacePictureJSON;


public class RocketsDBManager {
    private static final RocketsDBManager INSTANCE = new RocketsDBManager();
    private static Application mApplication;
    private static RocketsDAO mRocketsDAO;

    static RocketsDBManager getInstance(Application application) {
        mApplication = application;
        mRocketsDAO = RoomDatabase.getINSTANCE(mApplication).rocketsDAO();
        return INSTANCE;
    }

    public void  getRocketsFromDatabase(RocketsDBManager.rocketDBCallback callback){
        RoomDatabase.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Handler mainThreadHandler = new Handler(Looper.getMainLooper());
                List<RocketsDB> rocketsDBList = mRocketsDAO.getRockets();
                if (rocketsDBList == null || rocketsDBList.isEmpty()) {
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure();
                        }
                    });
                } else {
                    List<RocketJSON> rocketJSONList = new ArrayList<RocketJSON>();
                    for(RocketsDB i : rocketsDBList) {
                        rocketJSONList.add(new RocketJSON(i));
                    }
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(rocketJSONList);
                        }
                    });
                }
            }
        });
    }

    public void getRocketsFromDatabaseSearch(String searchtext, RocketsDBManager.rocketDBCallback callback) {
        RoomDatabase.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Handler mainThreadHandler = new Handler(Looper.getMainLooper());
                List<RocketsDB> rocketsDBList = mRocketsDAO.getRocketsSearch('%' + searchtext + '%');
                if (rocketsDBList == null || rocketsDBList.isEmpty()) {
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure();
                        }
                    });
                } else {
                    List<RocketJSON> rocketJSONList = new ArrayList<RocketJSON>();
                    for(RocketsDB i : rocketsDBList) {
                        rocketJSONList.add(new RocketJSON(i));
                    }
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(rocketJSONList);
                        }
                    });
                }
            }
        });
    }

    public void InsertRocketsToDataBase(List<RocketJSON> Rockets) {
        RoomDatabase.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mRocketsDAO.deleteAll();
                for(RocketJSON i : Rockets) {
                    mRocketsDAO.insert(new RocketsDB(i));
                }
            }
        });
    }

    public interface rocketDBCallback {
        public void onSuccess(List<RocketJSON> RocketsDBList);
        public void onFailure();
    }
}
