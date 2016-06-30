package com.centanet.turman;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.centanet.turman.ui.activity.UserLoginActivity;
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

    private double last_latitude;
    private double last_longtitude;

    private boolean isMute;

    //login user information
    public String empId;
    public String token;
    public String username;
    public String password;


    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPreferences = getSharedPreferences(UiContent.SHARED_FILE_NAME, Activity.MODE_PRIVATE);
        mSharedEditor = mSharedPreferences.edit();
        mLiveActivityList = new ArrayList<>();

        SDKInitializer.initialize(getApplicationContext());
        //开启定位
        mLocationUtil = new LocationUtil(this);
        isMute = getSpBoolean(UiContent.STORE_IS_MUTE);

        last_latitude = getSpDouble(UiContent.STORE_LATITUDE);
        last_longtitude = getSpDouble(UiContent.STORE_LONGTITUDE);

        saveDouble(UiContent.STORE_LATITUDE, getLatitude());
        saveDouble(UiContent.STORE_LONGTITUDE, getLongtitude());

        //load login user information
        empId = getSpString(UiContent.STORE_EMPID);
        token = getSpString(UiContent.STORE_TOKEN);
        username = getSpString(UiContent.STORE_USERNAME);
        password = getSpString(UiContent.STORE_PASSWORD);
    }

    /**
     * 设置静音模式
     * @param mute
     */
    public void setMute(boolean mute) {
        isMute = mute;
        saveBoolen("isMute",isMute);
    }

    public boolean isMute(){
        return isMute;
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

    public void saveDouble(String name,double value) {
        mSharedEditor.putFloat(name, (float) value);
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

    public double getSpDouble(String name){
        return mSharedPreferences.getFloat(name,0);
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

    /**
     * 跳转Activity
     * @param activity
     * @param clzz
     * @param bundle
     */
    public void goActivity(Activity activity, Class clzz, Bundle bundle){
        Intent intent = new Intent(activity,clzz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    //强制终止
    public void killApplication(){
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    //登出
    public void logout(Activity activity){
        saveString(UiContent.STORE_EMPID,"");
        saveString(UiContent.STORE_PASSWORD,"");
        saveString(UiContent.STORE_TOKEN,"");
        saveString(UiContent.STORE_USERNAME,"");
        saveBoolen(UiContent.STORE_ISLOGIN,false);
        saveDouble(UiContent.STORE_LATITUDE,getLatitude());
        saveDouble(UiContent.STORE_LONGTITUDE,getLongtitude());
        saveBoolen(UiContent.STORE_IS_MUTE,false);
        Intent intent = new Intent(activity, UserLoginActivity.class);
        activity.startActivity(intent);
        exitApplication();
    }

    public LocationUtil getLocationUtil() {
        return mLocationUtil;
    }

    public double getLatitude(){
        return mLocationUtil.getLatitude();
    }

    public double getLongtitude(){
        return mLocationUtil.getLongtitude();
    }

    public double getLastLatitude() {
        return last_latitude;
    }

    public double getLastLongtitude() {
        return last_longtitude;
    }
}
