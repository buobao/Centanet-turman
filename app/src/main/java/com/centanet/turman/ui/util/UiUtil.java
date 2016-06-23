package com.centanet.turman.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.centanet.turman.ui.activity.HomeActivity;

/**
 * Created by diaoqf on 2016/6/23.
 */
public class UiUtil {
    //跳转到首页
    public static void gotoHome(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
