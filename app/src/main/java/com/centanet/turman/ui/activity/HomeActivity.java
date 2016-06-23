package com.centanet.turman.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.centanet.turman.R;
import com.centanet.turman.ui.BaseActivity;

import butterknife.Bind;

/**
 * Created by diaoqf on 2016/6/23.
 */
public class HomeActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    protected int getLayout() {
        return R.layout.act_home;
    }

    @Override
    protected void handleFunc(Message msg) {

    }

    @Override
    protected boolean hasToolbar() {
        return true;
    }

    @Override
    protected void beforeCreate() {
        setTheme(R.style.AppThemeRed);
    }

    @Override
    protected boolean backExitApp() {
        return true;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        setTitle("");
    }

}
