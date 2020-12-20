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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RocketsDB Rocket);


}
