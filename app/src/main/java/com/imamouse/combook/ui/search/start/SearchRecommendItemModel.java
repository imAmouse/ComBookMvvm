package com.imamouse.combook.ui.search.start;

import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.imamouse.bookmodule.bean.Book;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;

public class SearchRecommendItemModel extends ItemViewModel {
    public Book bookEntity;

    public ObservableField<String> bookName;
    public ObservableField<String> author;
    public ObservableField<String> imageUrl;
    public ObservableField<String> url;
    public ObservableField<String> chapterSize;
    public ObservableField<String> lastUpdateTime;
    public ObservableField<String> lastChapterName;
    public ObservableField<String> siteName;
    public ObservableField<Bitmap> bookBm = new ObservableField<>();

    public SearchRecommendItemModel(@NonNull BaseViewModel viewModel, Book bookEntity) {
        super(viewModel);
        initBook(bookEntity);
        if (bookEntity.getUrl() != null)
            Glide.with(viewModel.getApplication())
                    .asBitmap()
                    .load(bookEntity.getUrl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            bookBm.set(resource);
                        }
                    });
    }

    public SearchRecommendItemModel(@NonNull BaseViewModel viewModel, Book bookEntity, Bitmap bookPic) {
        super(viewModel);
        initBook(bookEntity);
        if (bookPic != null)
            bookBm.set(bookPic);
    }

    private void initBook(Book bookEntity) {
        this.bookEntity = bookEntity;
        bookName = new ObservableField<>(bookEntity.getBookName());
        author = new ObservableField<>(bookEntity.getAuthor());
        imageUrl = new ObservableField<>(bookEntity.getImageUrl());
        url = new ObservableField<>(bookEntity.getUrl());
        chapterSize = new ObservableField<>(bookEntity.getChapterSize());
        lastUpdateTime = new ObservableField<>(bookEntity.getLastUpdateTime());
        lastChapterName = new ObservableField<>(bookEntity.getLastChapterName());
        siteName = new ObservableField<>(bookEntity.getSiteName());
    }
}
