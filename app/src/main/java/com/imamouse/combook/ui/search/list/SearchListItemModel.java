package com.imamouse.combook.ui.search.list;

import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.imamouse.combook.ui.book.BookInfoActivity;
import com.imamouse.combook.ui.search.SearchActivity;
import com.zia.easybookmodule.bean.Book;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;

public class SearchListItemModel extends ItemViewModel<SearchListViewModel> {

    //生成类
    public Book bookEntity;

    public ObservableField<String> bookName;
    public ObservableField<String> author;
    public ObservableField<String> imageUrl;
    public ObservableField<String> url;
    public ObservableField<String> chapterSize;
    public ObservableField<String> lastUpdateTime;
    public ObservableField<String> lastChapterName;
    public ObservableField<String> siteName;
    private Bitmap bookImage;

    public static String TOKEN_SEARCHLISTITEM_CLICKED = "token_searchlistitem_clicked";

    public SearchListItemModel(@NonNull SearchListViewModel viewModel, Book bookEntity) {
        super(viewModel);
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

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (bookEntity != null) {
                // 通知 SearchActivity 即将切换到 BookInfoActivity
                Messenger.getDefault().send(bookEntity,TOKEN_SEARCHLISTITEM_CLICKED);
            }
        }
    });
}
