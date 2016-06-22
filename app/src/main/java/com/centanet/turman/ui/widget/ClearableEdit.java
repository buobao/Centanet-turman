package com.centanet.turman.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.centanet.turman.R;

/**
 * 可擦除EditText
 * Created by diaoqf on 2016/6/22.
 */
public class ClearableEdit extends EditText {


    /**
     * 删除图标
     */
    private Drawable mDrawable;
    /**
     * 是否有焦点
     */
    private boolean mHasFoucs;

    public ClearableEdit(Context context) {
        super(context);
        init();
    }

    public ClearableEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        //这里获得的是一个数组，分别是左上右下的图标资源
        //这里获取右边的图标资源(如果有的话)
        mDrawable = getCompoundDrawables()[2];

        if (mDrawable == null) {
            mDrawable = getResources().getDrawable(R.mipmap.text_clear);
        }

        mDrawable.setBounds(0,0,mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        // 默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(new OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ClearableEdit.this.mHasFoucs = hasFocus;
                if (mHasFoucs) {
                    setClearIconVisible(getText().length() > 0);
                } else {
                    setClearIconVisible(false);
                }
            }
        });
        //设置输入框中内容发生变化的监听
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ClearableEdit.this.mHasFoucs){
                    setClearIconVisible(getText().length() > 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 显示/隐藏清除图标
     * @param flag
     */
    private void setClearIconVisible(boolean flag) {
        Drawable right = flag ? mDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                //这里计算是否触摸右侧图标
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    ClearableEdit.this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }


}
