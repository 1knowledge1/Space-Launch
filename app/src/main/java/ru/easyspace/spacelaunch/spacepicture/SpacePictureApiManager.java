package ru.easyspace.spacelaunch.spacepicture;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class SpacePictureApiManager {

    private static final SpacePictureApiManager INSTANCE = new SpacePictureApiManager();
    private static final String BASE_URL="https://api.nasa.gov/planetary/";
    private static onSpacePictureUpdatedListener mListener;
    static SpacePictureApiManager getInstance(onSpacePictureUpdatedListener Listener)
    {
        mListener=Listener;
        return INSTANCE;
    }

    public interface SpacePictureApi{
        @GET("apod?api_key=DEMO_KEY")
        Call<SpacePictureJSON> getPicture();
    }

    public void performSpacePictureRequest(final MutableLiveData<SpacePictureJSON> SpacePicture){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpacePictureApi SpacePictureApiInstance=retrofit.create(SpacePictureApi.class);
        Call<SpacePictureJSON> SpacePictureJSONCall=SpacePictureApiInstance.getPicture();
        SpacePictureJSONCall.enqueue(new Callback<SpacePictureJSON>() {
            @Override
            public void onResponse(Call<SpacePictureJSON> call, Response<SpacePictureJSON> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SpacePicture.postValue(response.body());
                    mListener.onSpacePictureUpdated(response.body());
                }

            }

            @Override
            public void onFailure(Call<SpacePictureJSON> call, Throwable t) {
                SpacePicture.postValue(null);
            }
        });
    }
    public  interface onSpacePictureUpdatedListener{
        public void onSpacePictureUpdated(SpacePictureJSON SpacePicture);
    }
}
