package com.centanet.turman;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.centanet.turman.ui.activity.UserLoginActivity;
import com.robotium.solo.Solo;

/**
 * Created by diaoqf on 2016/6/29.
 */
@SuppressWarnings("rawtypes")
public class ExampleTests extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ExampleTests() {
        super("com.centanet.turman", UserLoginActivity.class);
    }

//    public ExampleTests(String pkg, Class<UserLoginActivity> activityClass) {
//        super(pkg, activityClass);
//    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }


    public void test1() throws Exception{
        solo.assertCurrentActivity("wrong activity", UserLoginActivity.class);

        //输入用户名、密码
        solo.enterText((EditText) solo.getView(R.id.et_userName),"qinqi02");
        solo.enterText((EditText) solo.getView(R.id.et_password),"1");

        //点击登录
        solo.clickOnView(solo.getView(R.id.btn_submit));
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }
}
