package com.imamouse.combook.ui.search.list;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.imamouse.combook.R;
import com.imamouse.combook.ui.search.SearchViewModel;
import com.zia.easybookmodule.bean.Book;
import com.zia.easybookmodule.engine.EasyBook;
import com.zia.easybookmodule.rx.Disposable;
import com.zia.easybookmodule.rx.Subscriber;

import java.util.List;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class SearchListViewModel extends BaseViewModel {

    public static String TOKEN_SEARCHLISTVIEWMODEL_CREATED = "token_searchlistviewmodel_created";

    //给RecyclerView添加items
    public final ObservableList<ItemViewModel> searchListItemModelObservableList
            = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public final ItemBinding<ItemViewModel> searchListItemModelItemBinding
            = ItemBinding.of(BR.searchItemModel, R.layout.item_search);

    //与SearchActivity通信，切换搜索Fragment
    public MutableLiveData<SearchListViewModel> endSearch = new MutableLiveData();
    //     ......        , 搜索开始
    public MutableLiveData<SearchListViewModel> startSearch = new MutableLiveData();

    public ObservableField<String> searchMessage = new ObservableField<>();
    public ObservableField<String> searchProgress = new ObservableField<>();
    public ObservableField<String> searchName = new ObservableField<>();

    public SearchListViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {

        // 监听 Search 请求
        Messenger.getDefault().register(this, SearchViewModel.TOKEN_SEARCHVIEWMODEL_STARTSEARCH, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String s) {
                searchName.set(s);
                startSearch.setValue(SearchListViewModel.this);
            }
        });

        // 在当 SearchListFragment 创建时主动请求
        // *** 必须位于 TOKEN_SEARCHVIEWMODEL_STARTSEARCH 监听之下 ***
        Messenger.getDefault().sendNoMsg(TOKEN_SEARCHLISTVIEWMODEL_CREATED);

    }

    //添加订阅，提供停止搜索功能
    private Disposable searchDisposable = null;

    public void searchBook(String searchText) {
        searchDisposable = EasyBook.search(searchText)
                .subscribe(new Subscriber<List<Book>>() {
                    @Override
                    public void onFinish(List<Book> books) {
                        //搜索结果，返回book集合，提示用户选择
                        //recyclerviewAdapter.load(books);
                        ToastUtils.showShort("搜索完成 ^_^");
                        searchListItemModelObservableList.clear();
                        for (Book book : books) {
                            searchListItemModelObservableList.add(new SearchListItemModel(SearchListViewModel.this, book));
                        }
                        endSearch.setValue(SearchListViewModel.this);
                    }

                    @Override
                    public void onError(Exception e) {
                        //搜索时遇到错误
                    }

                    @Override
                    public void onMessage(String s) {
                        if (s != null)
                            searchMessage.set(s);
                    }

                    @Override
                    public void onProgress(int i) {
                        //搜索进度，0 ~ 100
                        searchProgress.set(i + "%");
                    }
                });
    }

    @Override
    public void onDestroy() {
        try {
            if (searchDisposable != null)
                searchDisposable.dispose();
        } catch (Exception ex) {
            ToastUtils.showShort(ex.getMessage());
        }
    }
}
