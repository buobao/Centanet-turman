package com.centanet.turman.ui.activity;

import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.centanet.turman.R;
import com.centanet.turman.entity.HouseEntity;
import com.centanet.turman.entity.ListResult;
import com.centanet.turman.net.NetHelper;
import com.centanet.turman.ui.BaseActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
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



    private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler" };

    private LinkedList<String> mListItems;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void initView(View view) {
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // 数据刷新请求

            }
        });

        // Add an end-of-list listener
        mPullToRefreshListView.setOnLastItemVisibleListener(() -> Toast.makeText(CommonActivity.this, "没有更多数据!", Toast.LENGTH_SHORT).show());

        ListView actualListView = mPullToRefreshListView.getRefreshableView();

        // Need to use the Actual ListView when registering for Context Menu
        registerForContextMenu(actualListView);

        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(mStrings));

        //初始化请求数据
        Map<String, String> params = new HashMap<>();
        params.put("token",mApplication.token);
        params.put("pageSize",20+"");
        params.put("delType","s");
        params.put("listType","NEAR_BY");
        params.put("empId",mApplication.empId);
        params.put("att",mApplication.getLatitude()+"");
        params.put("lat",mApplication.getLongtitude()+"");
        params.put("page","1");

        sendRequest(Observable.just(params).flatMap(
                (stringStringMap) ->
                        NetHelper.getCommonService().getHouseList(
                                stringStringMap.get("empId"),
                                stringStringMap.get("token"),
                                stringStringMap.get("lat"),
                                stringStringMap.get("att"),
                                stringStringMap.get("searchId"),
                                stringStringMap.get("searchType"),
                                stringStringMap.get("delType"),
                                stringStringMap.get("price"),
                                stringStringMap.get("square"),
                                stringStringMap.get("frame"),
                                stringStringMap.get("tag"),
                                stringStringMap.get("buildingName"),
                                stringStringMap.get("roomNo"),
                                stringStringMap.get("page"),
                                stringStringMap.get("pageSize"),
                                stringStringMap.get("sidx"),
                                stringStringMap.get("sord")
                                )).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<ListResult<HouseEntity>>() {
                @Override
                public void onCompleted() {}

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    showAlert("error");
                }

                @Override
                public void onNext(ListResult<HouseEntity> houseListResult) {
                    showAlert(houseListResult.msg);
                }
            }));

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
}























