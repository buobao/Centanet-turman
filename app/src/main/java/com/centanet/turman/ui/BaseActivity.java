package com.centanet.turman.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.centanet.turman.BaseApplication;
import com.centanet.turman.R;
import com.centanet.turman.entity.BaseEntity;
import com.centanet.turman.entity.BaseResult;
import com.centanet.turman.entity.LoginResult;
import com.centanet.turman.net.service.NetRequestFunction;
import com.centanet.turman.ui.util.UiContent;
import com.centanet.turman.ui.util.UiUtil;
import com.centanet.turman.ui.widget.DialogHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
    protected Map<String,String> requestParams;


    private long mLastPress = -1;

    public class MyObserver<T extends BaseResult> implements Observer<T> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
            e.printStackTrace();
            showAlert(R.string.net_work_error);
        }

        @Override
        public void onNext(T t) {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeCreate();
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(getLayout(),null);
        setContentView(view);

        mApplication = (BaseApplication) getApplication();
        mLoadingDialog = DialogHelper.getLoadingDialog(this);
        requestParams = new HashMap<>();

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

    protected void sendRequest(String functionName,Map<String, String> params,MyObserver observer ){
        final Method[] method = new Method[1];
        try {
            method[0] = NetRequestFunction.class.getMethod(functionName,new Class[]{Map.class});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        params = params == null ? requestParams : params;
        sendRequest(Observable.just(params)
                .flatMap((stringStringMap)-> {
                    try {
                        return (Observable<?>) method[0].invoke(null, stringStringMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer));
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
