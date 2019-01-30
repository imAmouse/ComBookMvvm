package com.imamouse.bookreader.interfaces;

import com.imamouse.bookreader.bean.TxtChar;

/**
 * Created by bifan-wei
 * on 2017/12/5.
 */

public interface ITextSelectListener {
    void onTextChanging(TxtChar firstSelectedChar,TxtChar lastSelectedChar);
    void onTextChanging(String selectText);
    void onTextSelected(String selectText);
}
