package ru.easyspace.spacelaunch.spacepicture;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import ru.easyspace.spacelaunch.RoomDatabase;
import ru.easyspace.spacelaunch.spacepicture.database.SpacePictureDAO;

public class SpacePictureDBManager {
    private static final SpacePictureDBManager INSTANCE = new SpacePictureDBManager();
    private static Application mApplication;
    private static SpacePictureDAO mSpacePictureDAO;
    static SpacePictureDBManager getInstance(Application application) {
        mApplication=application;
        mSpacePictureDAO=RoomDatabase.getINSTANCE(mApplication).spacePictureDAO();
        return INSTANCE;
    }
    public void  getPictureFromDatabase(pictureDBCallback callback){
        RoomDatabase.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Handler mainThreadHandler = new Handler(Looper.getMainLooper());
                List<SpacePictureJSON> SpacePictureList=mSpacePictureDAO.getSpacePicture();
                if(SpacePictureList==null||SpacePictureList.isEmpty()){
                       mainThreadHandler.post(new Runnable() {
                           @Override
                           public void run() {
                             callback.onFailure();
                           }
                       });
                }else{
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(SpacePictureList.get(0));
                        }
                    });
                }
            }
        });


    }
    public void InsertPictureToDataBase(SpacePictureJSON SpacePicture){
       
        RoomDatabase.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mSpacePictureDAO.deleteAll();
                mSpacePictureDAO.insert(SpacePicture);
            }
        });

    }
    public interface pictureDBCallback{
        public void onSuccess(SpacePictureJSON SpacePictureDB);
        public void onFailure();
    }
}
