package com.centanet.turman.ui.activity;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.centanet.turman.R;
import com.centanet.turman.entity.BaseEntity;
import com.centanet.turman.entity.BaseResult;
import com.centanet.turman.entity.LoginResult;
import com.centanet.turman.net.NetContent;
import com.centanet.turman.net.NetHelper;
import com.centanet.turman.net.service.NetRequestFunction;
import com.centanet.turman.ui.BaseFormActivity;
import com.centanet.turman.ui.util.UiContent;
import com.centanet.turman.ui.util.UiUtil;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by diaoqf on 2016/6/22.
 */
public class UserLoginActivity extends BaseFormActivity {
    @NotEmpty(messageResId=R.string.user_name_hint)
    @Bind(R.id.et_userName)
    protected EditText mUserName;

    @NotEmpty(messageResId = R.string.user_password_hint)
    @Bind(R.id.et_password)
    protected EditText mPassword;

    @Bind(R.id.btn_submit)
    protected Button mSubmit;

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
    protected void formSubmit() {
        String userName = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        mSubmit.setEnabled(false);
        mLoadingDialog.show();

        requestParams.put("userName",userName);
        requestParams.put("password",password);
        requestParams.put("version", NetContent.VERSION);

        sendRequest(NetRequestFunction.LOGIN,null,new MyObserver(){
            @Override
            public void onNext(BaseResult baseResult) {
                super.onNext(baseResult);
                LoginResult result = (LoginResult) baseResult;
                if (result.isSuccess){
                    mApplication.saveString(UiContent.STORE_USERNAME,userName);
                    mApplication.saveString(UiContent.STORE_PASSWORD,password);
                    mApplication.saveString(UiContent.STORE_TOKEN,result.token);
                    mApplication.saveString(UiContent.STORE_EMPID,result.empId);
                    mApplication.saveBoolen(UiContent.STORE_ISLOGIN,true);
                    showAlert(R.string.login_success);
                    UiUtil.gotoHome(UserLoginActivity.this);
                } else {
                    mSubmit.setEnabled(true);
                    showAlert(R.string.login_fail);
                }
            }
        });
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
    protected void initView(View view) {
        if (mApplication.getSpBoolean(UiContent.STORE_ISLOGIN)) {
            mUserName.setText(mApplication.username);
            mPassword.setText(mApplication.password);
            formSubmit();
        }

        mSubmit.setOnClickListener( v -> mValidator.validate());
    }
}
