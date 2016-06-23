package com.centanet.turman.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.centanet.turman.R;

/**
 * Created by diaoqf on 2016/6/23.
 */
public class DialogHelper {

    public static Dialog getLoadingDialog(Context context){
        Dialog loadingDialog;

        loadingDialog = new Dialog(context,R.style.Theme_dialog);
        loadingDialog.setContentView(R.layout.loading);
        Window window = loadingDialog.getWindow();
        loadingDialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() * 3 / 4;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        return loadingDialog;
    }


}
