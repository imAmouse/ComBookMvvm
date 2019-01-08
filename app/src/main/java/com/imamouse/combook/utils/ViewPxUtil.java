package com.imamouse.combook.utils;

import android.graphics.Rect;
import android.view.View;

public class ViewPxUtil {

    public static int getToolbarHeight(View view) {
        Rect frame = new Rect();
        view.getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }
}
