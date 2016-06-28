package com.centanet.turman.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.centanet.turman.BaseApplication;
import com.centanet.turman.R;
import com.centanet.turman.ui.widget.DialogHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by diaoqf on 2016/6/23.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @SuppressLint("HandlerLeak")
    protected Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            handleFunc(msg);
        }
    };
    protected List<Subscription> mSubscriptions = new ArrayList<>();

    protected abstract int getLayout();
    protected abstract void handleFunc(Message msg);
    protected abstract boolean hasToolbar();
    protected void initView(View view){}
    protected void initToolbar(){}
    protected void beforeCreate() {}
    protected abstract boolean backExitApp();

    protected BaseApplication mApplication;
    protected Dialog mLoadingDialog;

    private long mLastPress = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeCreate();
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(getLayout(),null);
        setContentView(view);

        mApplication = (BaseApplication) getApplication();
        mLoadingDialog = DialogHelper.getLoadingDialog(this);

        ButterKnife.bind(this);
        if (hasToolbar()){
            initToolbar();
        }
        initView(view);
        mApplication.putActivity(this);
    }

    protected void sendRequest(Subscription subscription){
        mSubscriptions.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mHandler.removeCallbacksAndMessages(null);

        if (mSubscriptions.size() > 0) {
            for (Subscription s : mSubscriptions) {
                if (s != null && !s.isUnsubscribed()) {
                    s.unsubscribe();
                }
            }
        }
        mApplication.removeActivity(this);
    }

    protected void showAlert(int msg){
        Toast.makeText(BaseActivity.this,getResources().getString(msg),Toast.LENGTH_SHORT).show();
    }

    protected void showAlert(String msg){
        Toast.makeText(BaseActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (backExitApp()){
            if (mLastPress < 0) {
                showAlert(R.string.exit_message);
                mLastPress = System.currentTimeMillis();
            } else {
                long this_press_time = System.currentTimeMillis();
                if (this_press_time - mLastPress <= 3000) {
                    mApplication.exitApplication();
                } else {
                    showAlert(R.string.exit_message);
                    mLastPress = this_press_time;
                }
            }
        } else {
            super.onBackPressed();
        }
    }
}
