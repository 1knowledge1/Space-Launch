package ru.easyspace.spacelaunch.spacepicture.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.easyspace.spacelaunch.launches.UpcomingLaunch;
import ru.easyspace.spacelaunch.spacepicture.SpacePictureJSON;

@Dao
public interface SpacePictureDAO {

    @Query("DELETE FROM space_picture_table")
    void deleteAll();

    @Query("SELECT * FROM space_picture_table")
    List<SpacePictureJSON> getSpacePicture();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SpacePictureJSON spacePicture);

}
