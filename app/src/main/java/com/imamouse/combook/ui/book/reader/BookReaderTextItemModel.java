package com.imamouse.combook.ui.book.reader;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class BookReaderTextItemModel extends ItemViewModel<BookReaderViewModel> {

    public ObservableField<String> words = new ObservableField<>();

    public BookReaderTextItemModel(@NonNull BookReaderViewModel viewModel, String text) {
        super(viewModel);
        words.set("　　" + text);
    }

    public BookReaderTextItemModel(@NonNull BookReaderViewModel viewModel, String text,int textSize) {
        super(viewModel);
        words.set("　　" + text);
    }

}
