package com.centanet.turman;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.baidu.mapapi.SDKInitializer;
import com.centanet.turman.ui.util.UiContent;
import com.centanet.turman.ui.widget.DialogHelper;
import com.centanet.turman.util.LocationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by diaoqf on 2016/6/22.
 */
public class BaseApplication extends Application {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedEditor;
    private List<Activity> mLiveActivityList;
    private LocationUtil mLocationUtil;

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPreferences = getSharedPreferences(UiContent.SHARED_FILE_NAME, Activity.MODE_PRIVATE);
        mSharedEditor = mSharedPreferences.edit();
        mLiveActivityList = new ArrayList<>();

        SDKInitializer.initialize(getApplicationContext());
        //开启定位
        mLocationUtil = new LocationUtil(getApplicationContext());
    }

    //保存基本数据
    public void saveString(String name, String value){
        mSharedEditor.putString(name,value);
        mSharedEditor.commit();
    }

    public void saveStrings(Map<String,String> map) {
        for (String key:map.keySet()) {
            mSharedEditor.putString(key,map.get(key));
        }
        mSharedEditor.commit();
    }

    public void saveBoolen(String name, boolean value){
        mSharedEditor.putBoolean(name,value);
        mSharedEditor.commit();
    }

    public String getSpString(String name) {
       return mSharedPreferences.getString(name, "");
    }

    public boolean getSpBoolean(String name) {
       return mSharedPreferences.getBoolean(name, false);
    }

    public void putActivity(Activity activity){
        mLiveActivityList.add(activity);
    }

    public void removeActivity(Activity activity){
        mLiveActivityList.remove(activity);
    }

    public void exitApplication(){
        for (int i=0;i<mLiveActivityList.size();i++) {
            mLiveActivityList.get(i).finish();
        }
        mLiveActivityList.clear();
    }

    //暴力终止
    public void killApplication(){
        System.exit(0);
    }

    public LocationUtil getmLocationUtil() {
        return mLocationUtil;
    }

    public double getLatitude(){
        return mLocationUtil.getLatitude();
    }

    public double getLongtitude(){
        return mLocationUtil.getLongtitude();
    }
}
