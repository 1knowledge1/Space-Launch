package ru.easyspace.spacelaunch.launches.launchLibraryAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LaunchLibraryAPI {
    String BASE_URL = "https://ll.thespacedevs.com/2.0.0/";

    @GET("launch/upcoming")
    Call<WrapperUpcomingLaunches> getUpcomingLaunches(@Query("limit") int limit);
}
