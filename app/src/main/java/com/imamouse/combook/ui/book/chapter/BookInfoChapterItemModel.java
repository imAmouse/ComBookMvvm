package com.imamouse.combook.ui.book.chapter;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.imamouse.combook.ui.book.BookInfoViewModel;
import com.imamouse.combook.ui.book.reader.BookReaderActivity;
import com.imamouse.bookmodule.bean.Catalog;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class BookInfoChapterItemModel extends ItemViewModel<BookInfoViewModel> {

    private ObservableField<Catalog> chapter = new ObservableField<>();
    public ObservableField<String> chapterName = new ObservableField<>();

    public BookInfoChapterItemModel(@NonNull BookInfoViewModel viewModel, Catalog chapter) {
        super(viewModel);
        this.chapter.set(chapter);
        chapterName.set(chapter.getChapterName());
    }

    public View.OnClickListener openCatalog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("book", viewModel.book);
            bundle.putSerializable("chapter", chapter.get());
            viewModel.startActivity(BookReaderActivity.class, bundle);
        }
    };
}
