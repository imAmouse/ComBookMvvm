package com.imamouse.combook.ui.book.chapter;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.imamouse.combook.ui.book.BookInfoViewModel;
import com.imamouse.combook.ui.book.reader.BookReaderActivity;
import com.zia.easybookmodule.bean.Catalog;
import com.zia.easybookmodule.engine.EasyBook;
import com.zia.easybookmodule.rx.Subscriber;

import java.util.ArrayList;
import java.util.List;

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
            EasyBook.getContent(viewModel.book, chapter.get())
                    .subscribe(new Subscriber<List<String>>() {
                        @Override
                        public void onFinish(@NonNull List<String> strings) {
                            //返回该章节所有内容，按行保存在集合内，需要自行调整格式
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("text", (ArrayList<String>) strings);
                            bundle.putString("chapterName", chapterName.get());
                            viewModel.startActivity(BookReaderActivity.class, bundle);
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
    };
}
