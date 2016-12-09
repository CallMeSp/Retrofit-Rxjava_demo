package com.sp.rerofittry;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

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
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by my on 2016/12/5.
 */
public class MainActivity2 extends Activity {
    @BindView(R.id.response)TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Retrofit zhuangRetrofit=new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl("http://www.zhuangbi.info/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        final ZhuangbiApi zhuangbiApi=zhuangRetrofit.create(ZhuangbiApi.class);
        zhuangbiApi .search("110")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<ZhuangbiImage>>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable throwable) {
                        }
                        @Override
                        public void onNext(List<ZhuangbiImage> zhuangbiImages) {
                            for (int i=0;i<zhuangbiImages.size();i++){
                                Log.e("list",zhuangbiImages.get(i).image_url+"   "+zhuangbiImages.get(i).description);
                                Log.e("name",zhuangbiImages.get(i).upload.created_at);
                            }
                        }
                    });
    }
    Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message message){
            textView.setText(message.obj.toString());
        }
    };
}

