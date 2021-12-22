package kz.xbase.mstroy.network;


import android.content.Context;

import java.util.concurrent.TimeUnit;

import kz.xbase.a_pay.network.API;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIBuilder {
    private final Retrofit retrofit;
    private final API api;
    public final String BASE_URL = "https://java.marketstroy.net";

    public APIBuilder(Context context){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                //.authenticator(new TokenAuthenticator(context))
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1,TimeUnit.MINUTES)
                .build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory( RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        this.api = retrofit.create(API.class);
    }
    public API getApi(){
        return  api;
    }

}
