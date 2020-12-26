package ru.easyspace.spacelaunch.launches;

import android.app.Application;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import ru.easyspace.spacelaunch.launches.database.UpLaunchDAO;
import ru.easyspace.spacelaunch.RoomDatabase;
import ru.easyspace.spacelaunch.launches.launchLibraryAPI.LaunchLibraryAPI;
import ru.easyspace.spacelaunch.launches.launchLibraryAPI.UpcomingLaunchPlain;
import ru.easyspace.spacelaunch.launches.launchLibraryAPI.WrapperUpcomingLaunches;

public class UpLaunchesRepo {

    private final static SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
    private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss' UTC'", Locale.US);
    private final static MutableLiveData<List<UpcomingLaunch>> mLaunches = new MutableLiveData<>();

    static {
        mLaunches.setValue(Collections.<UpcomingLaunch>emptyList());
    }

    private LaunchLibraryAPI launchAPI;
    private int LIMIT = 30;
    private UpLaunchDAO launchDAO;

    public UpLaunchesRepo(Application application) {
        launchAPI = ApiRepo.getInstance().getLaunchAPI();
        launchDAO = RoomDatabase.getINSTANCE(application).upLaunchDAO();
    }

    public LiveData<List<UpcomingLaunch>> getLaunches() {
        return mLaunches;
    }

    public void refresh() {
        launchAPI.getUpcomingLaunches(LIMIT).enqueue(new Callback<WrapperUpcomingLaunches>() {

            @Override
            public void onResponse(Call<WrapperUpcomingLaunches> call, Response<WrapperUpcomingLaunches> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UpcomingLaunch> launches = transform(response.body());

                    RoomDatabase.getExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            for(UpcomingLaunch launch: launches){
                                if(launchDAO.getLaunch(launch.getTitle())==null){
                                    launchDAO.insert(launch);
                                }else{
                                    upadteDatabaseLaunch(launch);
                                }
                            }
                            List<UpcomingLaunch> launches_db=launchDAO.getLaunches();
                            mLaunches.postValue(launches_db);
                        }
                    });

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
    public void updateNotifiction(List<UpcomingLaunch> launches,int position,Boolean notificted) {
               launches.get(position).setIsNotificated(notificted);
               insert(launches);
               update(launches);
    }
    public void getFromDatabase(onReadDatabaseListener listener) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<UpcomingLaunch> launches = launchDAO.getLaunches();
                listener.onReadAll(launches);
            }
        });
    }

    public void update(List<UpcomingLaunch> launches) {
        mLaunches.postValue(launches);
    }

    private void insert(List<UpcomingLaunch> launches) {
        RoomDatabase.getExecutor().execute(() -> {
            for (UpcomingLaunch launch : launches) {
                launchDAO.insert(launch);
            }
        });
    }

    private void delete() {
        RoomDatabase.getExecutor().execute(() -> {
            launchDAO.deleteAll();
        });
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
        String title = launchPlain.name;
        String description = null;
        if (launchPlain.missionPlain != null) {
            if (launchPlain.missionPlain.name != null) {
                title = launchPlain.missionPlain.name;
            }
            if (launchPlain.missionPlain.description != null) {
                description = launchPlain.missionPlain.description;
            }
        }

        String rocket = null;
        if (launchPlain.rocketPlain != null) {
            if (launchPlain.rocketPlain.configuration != null) {
                if (launchPlain.rocketPlain.configuration.name != null) {
                    rocket = launchPlain.rocketPlain.configuration.name;
                }
            }
        }

        String agency = null;
        if (launchPlain.launchServiceProviderPlain != null) {
            if (launchPlain.launchServiceProviderPlain.name != null) {
                agency = launchPlain.launchServiceProviderPlain.name;
            }
        }

        String pad = null;
        String location = null;
        String mapImage = null;
        if (launchPlain.padPlain != null) {
            if (launchPlain.padPlain.name != null) {
                pad = launchPlain.padPlain.name;
            }
            if (launchPlain.padPlain.mapImage != null) {
                mapImage = launchPlain.padPlain.mapImage;
            }
            if (launchPlain.padPlain.locationPlain != null) {
                if (launchPlain.padPlain.locationPlain.name != null) {
                    location = launchPlain.padPlain.locationPlain.name;
                } else if (launchPlain.padPlain.locationPlain.countryCode != null) {
                    location = launchPlain.padPlain.locationPlain.countryCode;
                }
            }
        }

        String startDate = null;
        String startTime = null;
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
                location, startDate, startTime, image, description,
                mapImage);
    }
    public void upadteDatabaseLaunch(UpcomingLaunch launch){
        RoomDatabase.getExecutor().execute(() -> {

                launchDAO.update(launch.getTitle(),launch.getRocket(),launch.getAgency(),launch.getPad(),
                        launch.getLocation(),launch.getStartDate(),launch.getStartTime(),launch.getImage(),
                        launch.getDescription(),launch.getMapImage());

        });
    }
    public interface onReadDatabaseListener {
        void onReadAll(List<UpcomingLaunch> launches);
    }
}
