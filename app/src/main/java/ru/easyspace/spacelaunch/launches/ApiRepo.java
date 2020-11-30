package ru.easyspace.spacelaunch.launches;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.easyspace.spacelaunch.launches.launchLibraryAPI.LaunchLibraryAPI;

public class ApiRepo {
    private static final ApiRepo INSTANCE = new ApiRepo();
    private final LaunchLibraryAPI launchAPI;

    private ApiRepo() {
        Retrofit retrofitLaunch = new Retrofit.Builder()
                .baseUrl(LaunchLibraryAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        launchAPI = retrofitLaunch.create(LaunchLibraryAPI.class);
    }

    static ApiRepo getInstance() {
        return INSTANCE;
    }

    public LaunchLibraryAPI getLaunchAPI () {
        return launchAPI;
    }
}
