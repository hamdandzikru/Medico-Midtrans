package com.example.medico.utils;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;


import androidx.annotation.ColorInt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {

    public static void setSystemBarColorInt(Activity act, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    public static String getFormattedTimeEvent(Long time) {
        SimpleDateFormat newFormat = new SimpleDateFormat("h:mm a");
        return newFormat.format(new Date(time));
    }


}
