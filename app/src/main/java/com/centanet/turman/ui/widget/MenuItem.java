package com.centanet.turman.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.centanet.turman.R;

/**
 * Created by diaoqf on 2016/6/24.
 */
public class MenuItem extends TextView {
    public MenuItem(Context context) {
        super(context);
        init(context, null);
    }

    public MenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //get left image
        Drawable mDrawable = getCompoundDrawables()[0];

        if (attrs != null && mDrawable != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MenuItem);
            int img_width = a.getInt(R.styleable.MenuItem_lft_img_width,mDrawable.getIntrinsicWidth());
            int img_height = a.getInt(R.styleable.MenuItem_lft_img_height,mDrawable.getIntrinsicHeight());
            a.recycle();

            mDrawable.setBounds(0,0,img_width,img_height);
        }
    }
}





















