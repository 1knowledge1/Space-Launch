package ru.easyspace.spacelaunch.launches.launchLibraryAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PadPlain {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("info_url")
    @Expose
    public Object infoUrl;
    @SerializedName("wiki_url")
    @Expose
    public String wikiUrl;
    @SerializedName("location")
    @Expose
    public LocationPlain locationPlain;
    @SerializedName("map_image")
    @Expose
    public String mapImage;
}
