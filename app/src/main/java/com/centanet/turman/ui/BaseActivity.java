package com.centanet.turman.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by diaoqf on 2016/6/22.
 */
@SuppressWarnings("ALL")
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeCreate();
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(getLayout(),null);
        setContentView(view);

        ButterKnife.bind(this);
        if (hasToolbar()){
            initToolbar();
        }
        initView(view);
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
    }
}

