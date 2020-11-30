package ru.easyspace.spacelaunch.launches.launchLibraryAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RocketConfigurationPlain {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("full_name")
    @Expose
    public String name;
    @SerializedName("family")
    @Expose
    public String family;
}
