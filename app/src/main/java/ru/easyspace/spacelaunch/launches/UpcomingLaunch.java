package ru.easyspace.spacelaunch.launches;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "up_launch_table")
public class UpcomingLaunch implements Parcelable {
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
    @ColumnInfo(name = "description")
    private String mDescription;
    @ColumnInfo(name = "mapImage")
    private String mMapImage;

    public static final Creator<UpcomingLaunch> CREATOR = new Creator<UpcomingLaunch>() {
        @Override
        public UpcomingLaunch createFromParcel(Parcel source) {
            String title = source.readString();
            String rocket = source.readString();
            String agency = source.readString();
            String pad = source.readString();
            String location = source.readString();
            String startDate = source.readString();
            String startTime = source.readString();
            String image = source.readString();
            String description = source.readString();
            String mapImage = source.readString();
            return new UpcomingLaunch(title, rocket, agency, pad,
                    location, startDate, startTime, image, description, mapImage);
        }

        @Override
        public UpcomingLaunch[] newArray(int size) {
            return new UpcomingLaunch[0];
        }
    };

    public UpcomingLaunch() {}

    @Ignore
    public UpcomingLaunch (String title, String rocket, String agency, String pad,
                           String location, String startDate, String startTime, String image,
                           String description, String mapImage) {
        mTitle = title;
        mRocket = rocket;
        mAgency = agency;
        mPad = pad;
        mLocation = location;
        mStartDate = startDate;
        mStartTime = startTime;
        mImage = image;
        mDescription = description;
        mMapImage = mapImage;
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getMapImage() {
        return mMapImage;
    }

    public void setMapImage(String mMapImage) {
        this.mMapImage = mMapImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mRocket);
        dest.writeString(mAgency);
        dest.writeString(mPad);
        dest.writeString(mLocation);
        dest.writeString(mStartDate);
        dest.writeString(mStartTime);
        dest.writeString(mImage);
        dest.writeString(mDescription);
        dest.writeString(mMapImage);
    }
}
