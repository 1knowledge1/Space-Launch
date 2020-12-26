package ru.easyspace.spacelaunch.launches.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.easyspace.spacelaunch.launches.UpcomingLaunch;

@Dao
public interface UpLaunchDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UpcomingLaunch launch);
    @Query("DELETE FROM up_launch_table")
    void deleteAll();
    @Query("UPDATE up_launch_table  " +
            "SET  startDate= :startDate, startTime= :startTime" +
            ", rocket= :rocket, image= :image, description= :desription" +
            ", agency= :agency, mapImage= :mapImge" +
            ", pad= :pad" +
            ", location= :location WHERE title= :title")
    void update(String title, String rocket, String agency, String pad,String location,String startDate,
                String startTime, String image, String desription, String mapImge);
    @Query("SELECT * FROM up_launch_table")
    List<UpcomingLaunch> getLaunches();
    @Query("SELECT  * FROM up_launch_table WHERE title= :title")
    List<UpcomingLaunch> getLaunch(String title);
}
