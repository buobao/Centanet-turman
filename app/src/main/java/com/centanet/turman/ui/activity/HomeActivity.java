package com.centanet.turman.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.centanet.turman.R;
import com.centanet.turman.ui.BaseActivity;

import butterknife.Bind;

/**
 * Created by diaoqf on 2016/6/23.
 */
public class HomeActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.home_container)
    protected DrawerLayout mDrawerLayout;
    @Bind(R.id.home_content)
    protected View mContent;
    @Bind(R.id.home_drawer)
    protected View mDrawer;

    private boolean isMenuOpen = false;

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
        mToolbar.setNavigationIcon(R.mipmap.home_menu);
        setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawable();
            }
        });
    }

    @Override
    protected void initView(View view) {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isMenuOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isMenuOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    public void toggleDrawable(){
        if (isMenuOpen) {
            mDrawerLayout.closeDrawer(mDrawer);
        } else {
            mDrawerLayout.openDrawer(mDrawer);
        }
    }

    @Override
    public void onBackPressed() {
        if (isMenuOpen) {
            toggleDrawable();
        } else {
            super.onBackPressed();
        }
    }
}
