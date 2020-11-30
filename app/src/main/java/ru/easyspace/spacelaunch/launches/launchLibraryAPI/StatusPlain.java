package ru.easyspace.spacelaunch.launches.launchLibraryAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusPlain {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
}
