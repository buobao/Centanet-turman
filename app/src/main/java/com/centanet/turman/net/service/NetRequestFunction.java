package com.centanet.turman.net.service;

import com.centanet.turman.entity.HouseListResult;
import com.centanet.turman.entity.LoginResult;
import com.centanet.turman.net.NetHelper;

import java.util.Map;

import rx.Observable;

/**
 * Created by diaoqf on 2016/6/29.
 */
public class NetRequestFunction {

    public static final String LOGIN = "login";
    public static final String GET_HOUSE_LIST = "getHouseList";

    public static Observable<LoginResult> login(Map<String,String> params){
        return NetHelper.getCommonService().login(
                params.get("userName"),
                params.get("password"),
                params.get("version"));
    }

    public static Observable<HouseListResult> getHouseList(Map<String, String> params){
        return NetHelper.getCommonService().getHouseList(
                params.get("empId"),
                params.get("token"),
                params.get("lat"),
                params.get("att"),
                params.get("searchId"),
                params.get("searchType"),
                params.get("delType"),
                params.get("price"),
                params.get("square"),
                params.get("frame"),
                params.get("tag"),
                params.get("buildingName"),
                params.get("roomNo"),
                params.get("page"),
                params.get("pageSize"),
                params.get("sidx"),
                params.get("sord"),
                params.get("listType"));
    }
}
