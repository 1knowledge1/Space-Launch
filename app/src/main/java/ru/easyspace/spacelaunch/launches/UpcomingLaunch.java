package ru.easyspace.spacelaunch.launches;


public class UpcomingLaunch {
    public static final String TITLE = "Untitled mission";
    public static final String ROCKET = "Unknown rocket";
    public static final String AGENCY = "Unknown agency";
    public static final String PAD = "Unknown pad";
    public static final String LOCATION = "Unknown location";
    public static final String START_DATE = "Unknown start date";
    public static final String START_TIME = "Unknown start time";

    private String mTitle;
    private String mRocket;
    private String mAgency;
    private String mPad;
    private String mLocation;
    private String mStartDate;
    private String mStartTime;
    private String mImage;

    public UpcomingLaunch() {}

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
