package ru.easyspace.spacelaunch.launches;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class UpLaunchesViewModel extends AndroidViewModel {

    private UpLaunchesRepo repo = new UpLaunchesRepo();
    private LiveData<List<UpcomingLaunch>> launches = repo.getLaunches();

    public UpLaunchesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<UpcomingLaunch>> getLaunches() {
        return launches;
    }

    // temp cache
    public void refresh() {
        if (repo.isCacheEmpty() ) {
            repo.refresh();
        } else {
            repo.getFromCache();
        }
    }
}
