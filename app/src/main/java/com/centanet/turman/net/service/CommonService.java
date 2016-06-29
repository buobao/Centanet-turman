package com.centanet.turman.net.service;

import com.centanet.turman.entity.HouseEntity;
import com.centanet.turman.entity.HouseListResult;
import com.centanet.turman.entity.LoginResult;
import com.centanet.turman.net.NetContent;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by diaoqf on 2016/6/23.
 */
public interface CommonService {

    //login
    @GET(NetContent.LOGIN_URL)
    Observable<LoginResult> login(
            @Query("username")String userName,
            @Query("password")String password,
            @Query("version")String version
    );

    //house list
    @GET(NetContent.HOUSE_LIST_URL)
    Observable<HouseListResult> getHouseList(
        @Query("empId") String empId,
        @Query("token") String token,
        @Query("lat") String lat,
        @Query("att") String att,
        @Query("searchId") String searchId,
        @Query("searchType") String searchType,
        @Query("delType") String delType,
        @Query("price") String price,
        @Query("square") String square,
        @Query("frame") String frame,
        @Query("tag") String tag,
        @Query("buildingName") String buildingName,
        @Query("roomNo") String roomNo,
        @Query("page") String page,
        @Query("pageSize") String pageSize,
        @Query("sidx") String sidx,
        @Query("sord") String sord,
        @Query("listType") String listType
    );



}
