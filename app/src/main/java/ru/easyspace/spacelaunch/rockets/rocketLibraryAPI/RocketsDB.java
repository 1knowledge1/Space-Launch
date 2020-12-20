package ru.easyspace.spacelaunch.rockets.rocketLibraryAPI;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rockets_table")

public class RocketsDB {
    public RocketsDB(RocketJSON Rocket){
        id=Rocket.id;
        url=Rocket.url;
        flightProven=Rocket.flight_proven;
        serialNumber=Rocket.serial_number;
        status=Rocket.status;
        details=Rocket.details;
        imageUrl=Rocket.image_url;
        flights=Rocket.flights;
        lastLaunchDate=Rocket.last_launch_date;
        firstLaunchDate=Rocket.first_launch_date;
        Rname=Rocket.launcher_config.name;
        Family=Rocket.launcher_config.family;
    }
    public RocketsDB(){

    }
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public Integer id;
    @ColumnInfo(name = "url")
    public String url;
    @ColumnInfo(name = "flightProven")
    public Boolean flightProven;
    @ColumnInfo(name = "serialNumber")
    public String serialNumber;
    @ColumnInfo(name = "status")
    public String status;
    @ColumnInfo(name = "details")
    public String details;
    @ColumnInfo(name = " imageUrl")
    public String imageUrl;
    @ColumnInfo(name = "flights")
    public Integer flights;
    @ColumnInfo(name = "lastLaunchDate")
    public String lastLaunchDate;
    @ColumnInfo(name = "firstLaunchDate")
    public String firstLaunchDate;
    @ColumnInfo(name = "Rname")
    public String Rname;
    @ColumnInfo(name = "Family")
    public String Family;
}
