package ru.easyspace.spacelaunch.rockets.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.easyspace.spacelaunch.rockets.rocketLibraryAPI.RocketJSON;
import ru.easyspace.spacelaunch.rockets.rocketLibraryAPI.RocketsDB;
import ru.easyspace.spacelaunch.spacepicture.SpacePictureJSON;
@Dao
public interface RocketsDAO {

    @Query("DELETE FROM rockets_table")
    void deleteAll();

    @Query("SELECT * FROM rockets_table")
    List<RocketsDB> getRockets();
    @Query("SELECT * FROM rockets_table WHERE Rname LIKE :searchtext OR Family LIKE :searchtext" +
            " OR serialNumber LIKE :searchtext OR details LIKE :searchtext " +
            "OR firstLaunchDate LIKE :searchtext OR lastLaunchDate LIKE :searchtext")
    List<RocketsDB> getRocketsSearch(String searchtext);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RocketsDB Rocket);


}
