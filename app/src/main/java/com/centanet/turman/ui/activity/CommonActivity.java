package com.centanet.turman.ui.activity;

import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.centanet.turman.R;
import com.centanet.turman.ui.BaseActivity;

import butterknife.Bind;

/**
 * Created by diaoqf on 2016/6/24.
 */
public class CommonActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    protected int getLayout() {
        return R.layout.act_common;
    }

    @Override
    protected void handleFunc(Message msg) {

    }

    @Override
    protected boolean hasToolbar() {
        return true;
    }

    @Override
    protected boolean backExitApp() {
        return false;
    }

    @Override
    protected void beforeCreate() {
        setTheme(R.style.AppThemeRed);
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.universal_button_back);
        setTitle("");
        mToolbar.setNavigationOnClickListener(v->onBackPressed());
    }
}
