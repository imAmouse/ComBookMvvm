package com.imamouse.combook.ui.book.reader;

import android.os.Bundle;

import com.imamouse.combook.BR;
import com.imamouse.combook.R;
import com.imamouse.combook.databinding.ActivityBookReaderBinding;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseActivity;

public class BookReaderActivity extends BaseActivity<ActivityBookReaderBinding, BookReaderViewModel> {

    private static boolean is_open = false;

    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.activity_book_reader;
    }

    @Override
    public int initVariableId() {
        return BR.readerModel;
    }

    @Override
    public void initParam() {
        is_open = true;
    }

    @Override
    public void initData() {

        // 接收 Activity 启动时传入的小说信息
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> textList = bundle.getStringArrayList("text");
        String chapterName = bundle.getString("chapterName");
        viewModel.initText(textList, chapterName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        is_open = false;
    }

    public static boolean isOpen() {
        return is_open;
    }
}
