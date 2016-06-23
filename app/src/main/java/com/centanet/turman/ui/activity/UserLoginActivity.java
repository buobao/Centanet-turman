package com.centanet.turman.ui.activity;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.centanet.turman.R;
import com.centanet.turman.entity.LoginResult;
import com.centanet.turman.net.NetContent;
import com.centanet.turman.net.NetHelper;
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

        Map<String, Object> params = new HashMap<>();
        params.put("userName",userName);
        params.put("password",password);
        params.put("version", NetContent.VERSION);

        sendRequest(Observable.just(params)
                .flatMap((stringObjectMap)-> {return NetHelper.getCommonService().login((String)params.get("userName"),(String)params.get("password"),(int)params.get("version"));})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        mSubmit.setEnabled(true);
                        Log.d("Turman",e.getMessage());
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                            mLoadingDialog.dismiss();
                        }

                        if (loginResult.isSuccess){
                            mApplication.saveString(UiContent.STORE_USERNAME,userName);
                            mApplication.saveString(UiContent.STORE_PASSWORD,password);
                            mApplication.saveString(UiContent.STORE_TOKEN,loginResult.token);
                            mApplication.saveString(UiContent.STORE_EMPID,loginResult.empId);
                            mApplication.saveBoolen(UiContent.STORE_ISLOGIN,true);
                            showAlert(R.string.login_success);
                            UiUtil.gotoHome(UserLoginActivity.this);
                        } else {
                            mSubmit.setEnabled(true);
                            showAlert(R.string.login_fail);
                        }
                    }
                }));
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
            mUserName.setText(mApplication.getSpString(UiContent.STORE_USERNAME));
            mPassword.setText(mApplication.getSpString(UiContent.STORE_PASSWORD));
            formSubmit();
        }

        mSubmit.setOnClickListener( v -> {
            mValidator.validate();
        });
    }
}
