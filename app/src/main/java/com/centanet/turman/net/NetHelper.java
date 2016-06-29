package com.centanet.turman.net;

import android.util.Log;

import com.centanet.turman.net.service.CommonService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dqf on 2016/5/6.
 */
public class NetHelper {
    private static Retrofit client = null;

    private static CommonService commonService;

    private NetHelper(){}

    public static Retrofit getClient(){
        //logging interface define
        Interceptor loggingInterceptor = chain -> {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            double time = (t2 - t1) / 1e6d;

            if (request.method().equals("GET")) {
                Log.i("Turman","GET\n" + "url->"+request.url()+"\ntime->"+time+"\nheaders->"+request.headers()+"\nresponse code->"+response.code()+"\nresponse headers->"+response.headers()+"\nbody->"+response.body().string());
            } else if (request.method().equals("POST")) {
                Log.i("Turman","POST" + "url->"+request.url()+"\ntime->"+time+"\nheaders->"+request.headers()+"\nresponse code->"+response.code()+"\nresponse headers->"+response.headers()+"\nbody->"+response.body().string());
            } else if (request.method().equals("PUT")) {
                Log.i("Turman","PUT" + "url->"+request.url()+"\ntime->"+time+"\nheaders->"+request.headers()+"\nresponse code->"+response.code()+"\nresponse headers->"+response.headers()+"\nbody->"+response.body().string());
            } else if (request.method().equals("DELETE")) {
                Log.i("Turman","DELETE" + "url->"+request.url()+"\ntime->"+time+"\nheaders->"+request.headers()+"\nresponse code->"+response.code()+"\nresponse headers->"+response.headers()+"\nbody->"+response.body().string());
            }

            return chain.proceed(request);
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        if (client == null) {
            client = new Retrofit.Builder()
                    .baseUrl(NetContent.TEST_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return client;
    }

    public static CommonService getCommonService(){
        if (commonService == null) {
            commonService = getClient().create(CommonService.class);
        }

        return commonService;
    }

}
