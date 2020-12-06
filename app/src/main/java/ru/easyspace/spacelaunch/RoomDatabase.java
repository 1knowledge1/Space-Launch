package ru.easyspace.spacelaunch;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.easyspace.spacelaunch.launches.UpcomingLaunch;
import ru.easyspace.spacelaunch.launches.database.UpLaunchDAO;
import ru.easyspace.spacelaunch.spacepicture.SpacePictureJSON;
import ru.easyspace.spacelaunch.spacepicture.database.SpacePictureDAO;

@Database(entities = {UpcomingLaunch.class, SpacePictureJSON.class}, version = 1, exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    public abstract UpLaunchDAO upLaunchDAO();
    public abstract SpacePictureDAO spacePictureDAO();
    private static volatile RoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREAD = 1;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREAD);

    public static ExecutorService getExecutor() {
        return databaseWriteExecutor;
    }

    public static RoomDatabase getINSTANCE(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, "database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
