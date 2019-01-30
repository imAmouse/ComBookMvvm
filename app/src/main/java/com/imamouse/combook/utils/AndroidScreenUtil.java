package com.imamouse.combook.utils;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class AndroidScreenUtil {

    private static int pxWidth;
    private static int pxHeight;
    private static float density;
    private static int densityDpi;
    private static int dpWidth;
    private static int dpHeight;

    public AndroidScreenUtil(Application application) {
        WindowManager wm = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        pxWidth = dm.widthPixels;         // 屏幕宽度（像素）
        pxHeight = dm.heightPixels;       // 屏幕高度（像素）
        density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        dpWidth = (int) (pxWidth / density);  // 屏幕宽度(dp)
        dpHeight = (int) (pxHeight / density);// 屏幕高度(dp)
    }

    public static int getPxWidth() {
        return pxWidth;
    }

    public static int getPxHeight() {
        return pxHeight;
    }

    public static float getDensity() {
        return density;
    }

    public static int getDensityDpi() {
        return densityDpi;
    }

    public static int getDpWidth() {
        return dpWidth;
    }

    public static int getDpHeight() {
        return dpHeight;
    }
}
