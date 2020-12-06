package ru.easyspace.spacelaunch.launches;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "up_launch_table")
public class UpcomingLaunch {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "rocket")
    private String mRocket;
    @ColumnInfo(name = "agency")
    private String mAgency;
    @ColumnInfo(name = "pad")
    private String mPad;
    @ColumnInfo(name = "location")
    private String mLocation;
    @ColumnInfo(name = "startDate")
    private String mStartDate;
    @ColumnInfo(name = "startTime")
    private String mStartTime;
    @ColumnInfo(name = "image")
    private String mImage;

    public UpcomingLaunch() {}

    @Ignore
    public UpcomingLaunch (String title, String rocket, String agency, String pad,
                           String location, String startDate, String startTime, String image) {
        mTitle = title;
        mRocket = rocket;
        mAgency = agency;
        mPad = pad;
        mLocation = location;
        mStartDate = startDate;
        mStartTime = startTime;
        mImage = image;

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getRocket() {
        return mRocket;
    }

    public void setRocket(String rocket) {
        mRocket = rocket;
    }

    public String getAgency() {
        return mAgency;
    }

    public void setAgency(String agency) {
        mAgency = agency;
    }

    public String getPad() {
        return mPad;
    }

    public void setPad(String pad) {
        mPad = pad;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String date) {
        mStartDate = date;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }
}
