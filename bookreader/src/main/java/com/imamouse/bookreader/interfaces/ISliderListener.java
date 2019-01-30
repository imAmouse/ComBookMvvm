package com.imamouse.bookreader.interfaces;

import com.imamouse.bookreader.bean.TxtChar;

/**
 * created by ： bifan-wei
 */

public interface ISliderListener {
    void onShowSlider(TxtChar txtChar);
    void onShowSlider(String CurrentSelectedText);
    void onReleaseSlider();
}
