package com.imamouse.combook.ui.book.reader;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.imamouse.combook.BR;
import com.imamouse.combook.R;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class BookReaderViewModel extends BaseViewModel {

    public BookReaderViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableList<BookReaderTextItemModel> observableList = new ObservableArrayList<>();
    public ItemBinding<BookReaderTextItemModel> itemBinding = ItemBinding.of(BR.bookreaderItemModel, R.layout.item_book_reader_text);

    public void initText(ArrayList<String> texts,String chapterName) {
        observableList.clear();
        observableList.add(new BookReaderTextItemModel(this,chapterName));
        for (String text : texts) {
            if (!text.equals(""))
            observableList.add(new BookReaderTextItemModel(this, text));
        }
    }
}
