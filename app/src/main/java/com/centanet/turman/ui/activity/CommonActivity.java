package com.centanet.turman.ui.activity;

import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.centanet.turman.R;
import com.centanet.turman.entity.BaseResult;
import com.centanet.turman.entity.HouseEntity;
import com.centanet.turman.entity.HouseListResult;
import com.centanet.turman.net.NetHelper;
import com.centanet.turman.net.service.NetRequestFunction;
import com.centanet.turman.ui.BaseActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by diaoqf on 2016/6/24.
 */
public class CommonActivity extends BaseActivity {

    private int page;

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;
    @Bind(R.id.pull_refresh_list)
    protected PullToRefreshListView mPullToRefreshListView;

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

    private LinkedList<String> mListItems;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void initView(View view) {
        page = 1;
        //初始化请求数据
        requestParams.put("token",mApplication.token);
        requestParams.put("pageSize","20");
        requestParams.put("delType","s");
        requestParams.put("listType","NEAR_BY");
        requestParams.put("empId",mApplication.empId);
        requestParams.put("att",mApplication.getLatitude()+"");
        requestParams.put("lat",mApplication.getLongtitude()+"");
        requestParams.put("page",page+"");

        mPullToRefreshListView.setOnRefreshListener((refreshView)-> {
            String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                    DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
            // Update the LastUpdatedLabel
            refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

            // 数据刷新请求
            mListItems.clear();
            page = 1;
            loadDate();
        });

        // Add an end-of-list listener
        mPullToRefreshListView.setOnLastItemVisibleListener(()-> {
            //添加翻页layout
            loadDate();
        });

        ListView actualListView = mPullToRefreshListView.getRefreshableView();

        // Need to use the Actual ListView when registering for Context Menu
        registerForContextMenu(actualListView);

        mListItems = new LinkedList<String>();

        //init loading data
        loadDate();

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);

        /**
         * Add Sound Event Listener
         */
        if (!mApplication.isMute()) {
            SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this);
            soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
            soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
            soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
            mPullToRefreshListView.setOnPullEventListener(soundListener);
        }

        // You can also just use setListAdapter(mAdapter) or
        // mPullRefreshListView.setAdapter(mAdapter)
        actualListView.setAdapter(mAdapter);
    }

    private void loadDate(){
        sendRequest(NetRequestFunction.GET_HOUSE_LIST,null,new MyObserver(){
            @Override
            public void onNext(BaseResult baseResult) {
                super.onNext(baseResult);
                HouseListResult houseHouseListResult = (HouseListResult) baseResult;
                if (houseHouseListResult.isSuccess) {
                    List<HouseEntity> houseEntities = houseHouseListResult.content.rows;
                    if (houseEntities.size() > 0){
                        page++;
                        for (HouseEntity houseEntity:houseEntities) {
                            mListItems.add(houseEntity.houAddr);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showAlert("没有更多数据");
                    }
                } else {
                    showAlert("数据读取失败!");
                }
                mPullToRefreshListView.onRefreshComplete();
            }
        });
    }
}























