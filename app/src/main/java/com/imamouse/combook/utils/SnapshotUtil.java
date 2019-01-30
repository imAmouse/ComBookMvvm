package com.imamouse.combook.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/*  *******注意！*******
    使用完成后，
    在acitivity的super.finish();或者在onDestroy中回收资源：
    @Override
    public void finish() {
            super.finish();
            defaultFinishNotActivityHelper();
            SnapshotUtil.recycle();
    }******************/

public class SnapshotUtil {

    private static Bitmap tab_bg = null;

    public static Bitmap getScreenshot(View v) {
        if (tab_bg != null) {
            recycle();
        }
        try {
            tab_bg = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                    Bitmap.Config.RGB_565);
            Canvas c = new Canvas(tab_bg);
            v.draw(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tab_bg;
    }

    public static Bitmap snapShotWithoutBar(Activity activity, boolean ifOutTop) {
        if (tab_bg != null) {
            recycle();
        }
        View view = activity.getWindow().getDecorView();
        try {
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            tab_bg = view.getDrawingCache();
            int statusBarHeight = 0;
            if (ifOutTop) {
                Rect frame = new Rect();
                activity.getWindow().getDecorView()
                        .getWindowVisibleDisplayFrame(frame);
                statusBarHeight = frame.top;
            }

            Point size = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(size);
            tab_bg = Bitmap.createBitmap(tab_bg, 0, statusBarHeight, size.x,
                    size.y - statusBarHeight);
            view.destroyDrawingCache();
        } catch (Exception e) {
            e.printStackTrace();
            getScreenshot(view);
        }
        return tab_bg;
    }

    public static Bitmap snapShotWithoutBar(View view, boolean ifOutTop) {
        if (tab_bg != null) {
            recycle();
        }
        try {
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            tab_bg = view.getDrawingCache();
            int statusBarHeight = 0;
            if (ifOutTop) {
                statusBarHeight = ViewPxUtil.getToolbarHeight(view);
            }

            tab_bg = Bitmap.createBitmap(tab_bg, 0, statusBarHeight, view.getMeasuredWidth(),
            view.getMeasuredHeight() - statusBarHeight);
            view.destroyDrawingCache();
        } catch (Exception e) {
            e.printStackTrace();
            getScreenshot(view);
        }
        return tab_bg;
    }

    public static void recycle() {

        try {
            if (tab_bg != null) {
                tab_bg.recycle();
                System.gc();
                tab_bg = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
