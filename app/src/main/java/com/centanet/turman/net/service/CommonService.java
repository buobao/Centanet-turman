package com.centanet.turman.net.service;

import com.centanet.turman.entity.LoginResult;
import com.centanet.turman.net.NetContent;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by diaoqf on 2016/6/23.
 */
public interface CommonService {
    @GET(NetContent.LOGIN_URL)
    Observable<LoginResult> login(@Query("username")String userName, @Query("password")String password, @Query("version")int version);
}
