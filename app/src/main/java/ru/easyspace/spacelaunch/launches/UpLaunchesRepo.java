package ru.easyspace.spacelaunch.launches;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import ru.easyspace.spacelaunch.launches.launchLibraryAPI.LaunchLibraryAPI;
import ru.easyspace.spacelaunch.launches.launchLibraryAPI.UpcomingLaunchPlain;
import ru.easyspace.spacelaunch.launches.launchLibraryAPI.WrapperUpcomingLaunches;

public class UpLaunchesRepo {

    private final static SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
    private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss' UTC'", Locale.US);
    private final static MutableLiveData<List<UpcomingLaunch>> mLaunches = new MutableLiveData<>();

    static {
        mLaunches.setValue(Collections.emptyList());
    }

    private LaunchLibraryAPI launchAPI;
    private int LIMIT = 30;
    // TODO: BD
    private List<UpcomingLaunch> cache = new ArrayList<>();

    public UpLaunchesRepo() {
        launchAPI = ApiRepo.getInstance().getLaunchAPI();
    }

    public LiveData<List<UpcomingLaunch>> getLaunches() {
        return mLaunches;
    }

    public void refresh() {
        launchAPI.getUpcomingLaunches(LIMIT).enqueue(new Callback<WrapperUpcomingLaunches>() {

            @Override
            public void onResponse(Call<WrapperUpcomingLaunches> call, Response<WrapperUpcomingLaunches> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cache = transform(response.body());
                    mLaunches.postValue(cache);
                    Log.d("Network", "Response code " + response.code());
                } else {
                    Log.e("Network", "Response code " + response.code());
                    mLaunches.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<WrapperUpcomingLaunches> call, Throwable t) {
                Log.e("Network", "Failed to load " + t);
                mLaunches.postValue(null);
            }
        });
    }

    public boolean isCacheEmpty() {
        return cache.isEmpty();
    }
    public void getFromCache() {
        mLaunches.postValue(cache);
    }

    private List<UpcomingLaunch> transform (WrapperUpcomingLaunches wrapper) {
        List<UpcomingLaunch> result = new ArrayList<>();
        for (UpcomingLaunchPlain launchPlain : wrapper.results) {
            if (launchPlain == null) { continue; }
            try {
                UpcomingLaunch launch = map(launchPlain);
                result.add(launch);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private UpcomingLaunch map (UpcomingLaunchPlain launchPlain) throws ParseException {
        String title = UpcomingLaunch.TITLE;
        if (launchPlain.name != null) {
            title = launchPlain.name;
        }
        if (launchPlain.missionPlain != null) {
            if (launchPlain.missionPlain.name != null) {
                title = launchPlain.missionPlain.name;
            }
        }

        String rocket = UpcomingLaunch.ROCKET;
        if (launchPlain.rocketPlain != null) {
            if (launchPlain.rocketPlain.configuration != null) {
                if (launchPlain.rocketPlain.configuration.name != null) {
                    rocket = launchPlain.rocketPlain.configuration.name;
                }
            }
        }

        String agency = UpcomingLaunch.AGENCY;
        if (launchPlain.launchServiceProviderPlain != null) {
            if (launchPlain.launchServiceProviderPlain.name != null) {
                agency = launchPlain.launchServiceProviderPlain.name;
            }
        }

        String pad = UpcomingLaunch.PAD;
        String location = UpcomingLaunch.LOCATION;
        if (launchPlain.padPlain != null) {
            if (launchPlain.padPlain.name != null) {
                pad = launchPlain.padPlain.name;
            }
            if (launchPlain.padPlain.locationPlain != null) {
                if (launchPlain.padPlain.locationPlain.name != null) {
                    location = launchPlain.padPlain.locationPlain.name;
                } else if (launchPlain.padPlain.locationPlain.countryCode != null) {
                    location = launchPlain.padPlain.locationPlain.countryCode;
                }
            }
        }

        String startDate = UpcomingLaunch.START_DATE;
        String startTime = UpcomingLaunch.START_TIME;
        if (launchPlain.windowStart != null) {
            Date mDate = apiDateFormat.parse(launchPlain.windowStart);
            startDate = dateFormat.format(mDate);
            startTime = timeFormat.format(mDate);
        }

        String image = null;
        if (launchPlain.image != null) {
            image = launchPlain.image;
        }

        return new UpcomingLaunch(title, rocket, agency, pad,
                location, startDate, startTime, image);
    }
}
