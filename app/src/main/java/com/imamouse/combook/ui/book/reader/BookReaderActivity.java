package com.imamouse.combook.ui.book.reader;

import android.os.Bundle;

import com.imamouse.combook.BR;
import com.imamouse.combook.R;
import com.imamouse.combook.databinding.ActivityBookReaderBinding;
import com.imamouse.bookmodule.bean.Book;
import com.imamouse.bookmodule.bean.Catalog;

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
        Book book= (Book) bundle.getSerializable("book");
        Catalog catalog= (Catalog) bundle.getSerializable("chapter");

        viewModel.book=book;

        viewModel.initCatalog(catalog);
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
