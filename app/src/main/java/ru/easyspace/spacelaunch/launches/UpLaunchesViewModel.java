package ru.easyspace.spacelaunch.launches;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class UpLaunchesViewModel extends AndroidViewModel {

    private UpLaunchesRepo repo;
    private LiveData<List<UpcomingLaunch>> launches;

    public UpLaunchesViewModel(@NonNull Application application) {
        super(application);
        repo = new UpLaunchesRepo(application);
        launches = repo.getLaunches();
    }

    public LiveData<List<UpcomingLaunch>> getLaunches() {
        return launches;
    }

    public void getFromDatabase() {
        repo.getFromDatabase(new UpLaunchesRepo.onReadDatabaseListener() {
            @Override
            public void onReadAll(List<UpcomingLaunch> launches) {
                if (launches == null) {
                    repo.refresh();
                } else if (launches.isEmpty()) {
                    repo.refresh();
                } else {
                    repo.update(launches);
                }
            }
        });
    }

    public void refresh() {
        repo.refresh();
    }
}
