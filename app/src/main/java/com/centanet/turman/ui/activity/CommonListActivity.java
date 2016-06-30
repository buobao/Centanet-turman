package com.centanet.turman.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.centanet.turman.R;
import com.centanet.turman.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by diaoqf on 2016/6/30.
 */
public class CommonListActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;
    @Bind(R.id.vp_container)
    protected ViewPager mViewpager;
    @Bind(R.id.tv_title)
    protected TextView mTitle;

    private List<Integer> mPageList;
    private List<String> mTitleList = new ArrayList<String>(){{add("A");add("B");add("C");}};

    //viewpager adapter
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public int getCount() {
            return mPageList.size();
        }
        @Override
        public void destroyItem(ViewGroup container, int position,Object object) {
//            container.removeView(mPageList.get(position));
            container.removeView((View) object);
        }
        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //add listeners here if necessary
            LayoutInflater lf = getLayoutInflater().from(CommonListActivity.this);
            View v = lf.inflate(mPageList.get(position),null);
            container.addView(v);
            return v;
        }
    };

    //page transform animation define
    private ViewPager.PageTransformer mZoomOutPageTransformer = new ViewPager.PageTransformer(){
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();
            int pageHeight = page.getHeight();

            Log.e("TAG", page + " , " + position + "");

            if (position < -1)
            { // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setAlpha(0);

            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0)
                {
                    page.setTranslationX(horzMargin - vertMargin / 2);
                } else
                {
                    page.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                        / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else
            { // (1,+Infinity]
                // This page is way off-screen to the right.
                page.setAlpha(0);
            }
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.act_commonlist;
    }

    @Override
    protected void beforeCreate() {
        setTheme(R.style.AppThemeRed);
    }

    @Override
    protected void initView(View view) {
        mPageList = new ArrayList<>();
        mPageList.add(R.layout.tst_a);
        mPageList.add(R.layout.tst_b);
        mPageList.add(R.layout.tst_c);

        mViewpager.setAdapter(mPagerAdapter);
        //设置缓存页
        mViewpager.setOffscreenPageLimit(1);
        //设置切换动画
        mViewpager.setPageTransformer(true, mZoomOutPageTransformer);
        //设置滑动监听
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("Turman","scrolled");
                Log.d("Turman","position:"+position+",positionOffset:"+positionOffset+",positionOffsetPixels:"+positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                mTitle.setText(mTitleList.get(position));
                Log.d("Turman","selected");
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("Turman","scrollstatechanged");
            }
        });
        mViewpager.setCurrentItem(1);
    }

    @Override
    protected void handleFunc(Message msg) {

    }

    @Override
    protected boolean hasToolbar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.universal_button_back);
        setTitle("");
        mToolbar.setNavigationOnClickListener(v->onBackPressed());
        mTitle.setText(mTitleList.get(0));
    }

    @Override
    protected boolean backExitApp() {
        return false;
    }
}
