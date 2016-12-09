package com.sp.rerofittry;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;


/**
 * Created by my on 2016/12/9.
 */

public interface WeatherApi {
    @GET("?city=beijing")
    Call<ResponseBody> getWeather(@Header("apikey") String key);

    @GET("?city=beijing")
    Observable<Weather> get(@Header("apikey") String key);
}
