package ru.easyspace.spacelaunch.launches.launchLibraryAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WrapperUpcomingLaunches {
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("next")
    @Expose
    public String next;
    @SerializedName("results")
    @Expose
    public List<UpcomingLaunchPlain> results = null;
}
