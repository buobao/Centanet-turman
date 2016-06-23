package com.centanet.turman.net;

import com.centanet.turman.net.service.CommonService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dqf on 2016/5/6.
 */
public class NetHelper {
    private static Retrofit client = null;

    public static final String LIST = "list";
    public static final String NEWS = "news";
    public static final String TYPE = "classify";
    public static final String DETAIL = "show";

    private static CommonService commonService;

    private NetHelper(){}

    public static Retrofit getClient(){
        OkHttpClient okHttpClient = new OkHttpClient();

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
