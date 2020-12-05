package ru.easyspace.spacelaunch.launches.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.easyspace.spacelaunch.launches.UpcomingLaunch;

@Dao
public interface UpLaunchDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UpcomingLaunch launch);

    @Query("DELETE FROM up_launch_table")
    void deleteAll();

    @Query("SELECT * FROM up_launch_table")
    List<UpcomingLaunch> getLaunches();
}
