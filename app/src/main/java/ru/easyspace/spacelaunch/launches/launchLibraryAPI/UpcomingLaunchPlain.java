package ru.easyspace.spacelaunch.launches.launchLibraryAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingLaunchPlain {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("status")
    @Expose
    public StatusPlain statusPlain;
    @SerializedName("window_start")
    @Expose
    public String windowStart;
    @SerializedName("inhold")
    @Expose
    public Boolean inhold;
    @SerializedName("holdreason")
    @Expose
    public String holdreason;
    @SerializedName("failreason")
    @Expose
    public String failreason;
    @SerializedName("launch_service_provider")
    @Expose
    public LaunchServiceProviderPlain launchServiceProviderPlain;
    @SerializedName("rocket")
    @Expose
    public RocketPlain rocketPlain;
    @SerializedName("mission")
    @Expose
    public MissionPlain missionPlain;
    @SerializedName("pad")
    @Expose
    public PadPlain padPlain;
    @SerializedName("webcast_live")
    @Expose
    public Boolean webcastLive;
    @SerializedName("image")
    @Expose
    public String image;
}
