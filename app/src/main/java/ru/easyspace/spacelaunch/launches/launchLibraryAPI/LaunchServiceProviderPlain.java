package ru.easyspace.spacelaunch.launches.launchLibraryAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LaunchServiceProviderPlain {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
}
