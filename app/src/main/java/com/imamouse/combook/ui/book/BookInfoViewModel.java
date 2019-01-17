package com.imamouse.combook.ui.book;

import android.app.Application;
import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.view.View;

import com.imamouse.combook.BR;
import com.imamouse.combook.R;
import com.imamouse.combook.ui.book.chapter.BookInfoChapterItemModel;
import com.imamouse.bookmodule.bean.Book;
import com.imamouse.bookmodule.bean.Catalog;
import com.imamouse.bookmodule.engine.EasyBook;
import com.imamouse.bookmodule.rx.Disposable;
import com.imamouse.bookmodule.rx.Subscriber;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class BookInfoViewModel extends BaseViewModel {

    public Book book;

    public ObservableField<String> bookName = new ObservableField<>();
    public ObservableField<String> author = new ObservableField<>();
    public ObservableField<String> imageUrl = new ObservableField<>();
    public ObservableField<String> url = new ObservableField<>();
    public ObservableField<String> chapterSize = new ObservableField<>();
    public ObservableField<String> lastUpdateTime = new ObservableField<>();
    public ObservableField<String> lastChapterName = new ObservableField<>();
    public ObservableField<String> siteName = new ObservableField<>();

    public ObservableField<String> chapterHead = new ObservableField<>();

    public MediatorLiveData<BookInfoViewModel> initCharpterListOk = new MediatorLiveData<>();

    private Disposable listinitDisposable;

    //给RecyclerView添加items
    public final ObservableList<BookInfoChapterItemModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public final ItemBinding<BookInfoChapterItemModel> itemBinding = ItemBinding.of(BR.chapterItemModel, R.layout.item_book_info_chapter);

    public BookInfoViewModel(@NonNull Application application) {
        super(application);
    }

    public void initBookinfo(Book bookData) {
        if (bookData != null) {
            book = bookData;
            bookName.set(bookData.getBookName());
            author.set(bookData.getAuthor());
            imageUrl.set(bookData.getImageUrl());
            url.set(bookData.getUrl());
            chapterSize.set(bookData.getChapterSize());
            lastUpdateTime.set(bookData.getLastUpdateTime());
            lastChapterName.set(bookData.getLastChapterName());
            siteName.set(bookData.getSiteName());

            chapterHead.set("加载中");
        }

        //加载章节列表
        listinitDisposable = EasyBook.getCatalog(book)
                .subscribe(new Subscriber<List<Catalog>>() {
                    @Override
                    public void onFinish(List<Catalog> catalogs) {
                        ToastUtils.showShort("加载完成");
                        //加载结果，返回该书籍所有目录
                        for (Catalog chapter : catalogs) {
                            observableList.add(new BookInfoChapterItemModel(BookInfoViewModel.this, chapter));
                        }
                        initCharpterListOk.setValue(BookInfoViewModel.this);
                    }

                    @Override
                    public void onError(@NonNull Exception e) {

                    }

                    @Override
                    public void onMessage(@NonNull String s) {

                    }

                    @Override
                    public void onProgress(int i) {
                        chapterHead.set("正在加载章节（" + String.valueOf(i) + "）");
                    }
                });
    }

    /**
     * 退出搜索界面
     */
    public View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //结束加载章节列表
                if (listinitDisposable != null)
                    listinitDisposable.dispose();
            } catch (Exception ex) {
                ToastUtils.showShort(ex.getMessage());
            }
            onBackPressed();
        }
    };
}
