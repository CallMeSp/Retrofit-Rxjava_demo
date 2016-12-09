package com.sp.rerofittry;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by my on 2016/12/9.
 */

public class MainActivity extends Activity{
    @BindView(R.id.response)TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Retrofit weatherTry=new Retrofit.Builder()
                                        .baseUrl("http://apis.baidu.com/heweather/weather/free/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                        .client(new OkHttpClient())
                                        .build();
        WeatherApi weatherApi=weatherTry.create(WeatherApi.class);
        final Call<ResponseBody> call=weatherApi.getWeather("a79124c4594c2e5a0799a39ea8f64c87");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s=response.body().string();
                    Log.e("onResponse", "response=" + s);
                    Document document= Jsoup.parse(s);
                    Elements items=document.select("basic");
                    Log.e("item",items.toString());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
            }
        });
        weatherApi.get("a79124c4594c2e5a0799a39ea8f64c87")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onCompleted() {
                        Log.e("0","completed");
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("0","error");
                    }
                    @Override
                    public void onNext(Weather weather) {
                        Log.e("0","weather="+weather.aqi.city.aqi);
                    }
                });
    }
}
