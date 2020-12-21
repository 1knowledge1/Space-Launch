package ru.easyspace.spacelaunch.rockets;


import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.easyspace.spacelaunch.rockets.rocketLibraryAPI.RocketJSON;
import ru.easyspace.spacelaunch.rockets.rocketLibraryAPI.RocketJSONDetailed;
import ru.easyspace.spacelaunch.rockets.rocketLibraryAPI.WrapperRockets;
import ru.easyspace.spacelaunch.spacepicture.SpacePictureApiManager;
import ru.easyspace.spacelaunch.spacepicture.SpacePictureJSON;

public class RocketsApiManager {
    private static final RocketsApiManager INSTANCE = new RocketsApiManager();
    private static final String BASE_URL="https://ll.thespacedevs.com/2.0.0/";
    private static RocketsApiManager.onRocketsUpdatedListener mListener;
    static RocketsApiManager getInstance(onRocketsUpdatedListener Listener)
    {
        mListener=Listener;
        return INSTANCE;
    }
    public interface RocketsApi{
        @GET("launcher/?limit=50")
        Call<WrapperRockets> getRockets();
        @GET("launcher/{id}")
        Call<RocketJSONDetailed> getRocketDetail(@Path("id") int id);
    }
    public void performRocketsRequest(final MutableLiveData<List<RocketJSON>> Rockets){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RocketsApiManager.RocketsApi RocketsApiInstance=retrofit.create(RocketsApiManager.RocketsApi.class);
        Call<WrapperRockets> RocketsCall=RocketsApiInstance.getRockets();
        RocketsCall.enqueue(new Callback<WrapperRockets>() {
            @Override
            public void onResponse(Call<WrapperRockets> call, Response<WrapperRockets> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Rockets.postValue(response.body().results);
                    mListener.onRocketsUpdated(response.body().results);
                }

            }

            @Override
            public void onFailure(Call<WrapperRockets> call, Throwable t) {
                Rockets.postValue(null);
            }
        });
    }

    public  interface onRocketsUpdatedListener{
        public void onRocketsUpdated(List<RocketJSON> Rockets);
    }

}
