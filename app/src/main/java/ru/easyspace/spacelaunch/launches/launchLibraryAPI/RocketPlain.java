package ru.easyspace.spacelaunch.launches.launchLibraryAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RocketPlain {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("configuration")
    @Expose
    public RocketConfigurationPlain configuration;
}
