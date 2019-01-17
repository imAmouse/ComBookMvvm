package com.imamouse.combook.ui.book.reader;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.imamouse.combook.BR;
import com.imamouse.combook.R;
import com.imamouse.bookmodule.bean.Book;
import com.imamouse.bookmodule.bean.Catalog;
import com.imamouse.bookmodule.engine.EasyBook;
import com.imamouse.bookmodule.rx.Subscriber;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class BookReaderViewModel extends BaseViewModel {

    public BookReaderViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableList<BookReaderTextItemModel> observableList = new ObservableArrayList<>();
    public ItemBinding<BookReaderTextItemModel> itemBinding = ItemBinding.of(BR.bookreaderItemModel, R.layout.item_book_reader_text);

    Book book;

    public void initCatalog(final Catalog catalog) {
        EasyBook.getContent(book, catalog)
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onFinish(@NonNull List<String> strings) {
                        //返回该章节所有内容，按行保存在集合内，需要自行调整格式
                        initText((ArrayList<String>) strings, catalog.getChapterName());
                    }

                    @Override
                    public void onError(@NonNull Exception e) {

                    }

                    @Override
                    public void onMessage(@NonNull String s) {

                    }

                    @Override
                    public void onProgress(int i) {

                    }
                });
    }

    public void initText(ArrayList<String> texts, String chapterName) {
        observableList.clear();
        observableList.add(new BookReaderTextItemModel(this, chapterName));
        for (String text : texts) {
            if (!text.equals(""))
                observableList.add(new BookReaderTextItemModel(this, text));
        }
    }
}
