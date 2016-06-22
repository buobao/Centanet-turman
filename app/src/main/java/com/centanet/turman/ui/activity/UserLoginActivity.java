package com.centanet.turman.ui.activity;

import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.centanet.turman.R;
import com.centanet.turman.ui.BaseActivity;

import butterknife.Bind;

/**
 * Created by diaoqf on 2016/6/22.
 */
public class UserLoginActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.act_userlogin;
    }

    @Override
    protected void handleFunc(Message msg) {

    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected void beforeCreate() {
        setTheme(R.style.AppThemeRed);
    }

//    @Override
//    protected void initToolbar() {
//        setSupportActionBar(mToolbar);
//        setTitle(getString(R.string.app_name));
//        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
//    }

    @Override
    protected void initView(View view) {

    }
}
