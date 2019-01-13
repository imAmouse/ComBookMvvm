package com.imamouse.combook.ui.search;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.imamouse.combook.ui.search.list.SearchListViewModel;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class SearchViewModel extends BaseViewModel {

    // 通过 Messenger 发送 Token 与 SearchListViewModel 通信，表示搜索按钮已点击,搜索开始
    public static final String TOKEN_SEARCHVIEWMODEL_STARTSEARCH = "token_searchviewmodel_startsearch";
    //搜索内容binding
    public ObservableField<String> bookName = new ObservableField<>("");
    //与SearchActivity通信，切换搜索Fragment
    public MutableLiveData<SearchViewModel> startSearchBefore = new MutableLiveData();

    public ObservableField<String> searchMessage = new ObservableField<>();
    public ObservableField<String> searchProgress = new ObservableField<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 退出搜索界面
     */
    public View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //统一处理返回事件
            AppManager.getAppManager().currentActivity().finish();
        }
    };

    /**
     * 开始搜索
     */
    public View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startSearchBefore();
        }
    };

    public void startSearchBefore() {
        if (!bookName.get().equals("")) {
            // 告诉 SearchActivity 切换 Fragment，准备搜索
            startSearchBefore.setValue(SearchViewModel.this);
        } else {
            ToastUtils.showShort("请输入搜索内容");
        }
    }

    // 当 SearchActivity 中的 Fragment 切换成功时 : 开始搜索
    public void startSearch(){
        Messenger.getDefault().send(bookName.get(), TOKEN_SEARCHVIEWMODEL_STARTSEARCH);
    }

    @Override
    public void onCreate() {
        Messenger.getDefault().register(this, SearchListViewModel.TOKEN_SEARCHLISTVIEWMODEL_CREATED, new BindingAction() {
            @Override
            public void call() {
                startSearch();
            }
        });

        Messenger.getDefault().register(this, SearchListViewModel.TOKEN_SEARCHLISTVIEWMODEL_SEARCHMESSAGE, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String s) {
                searchMessage.set(s);
            }
        });

        Messenger.getDefault().register(this, SearchListViewModel.TOKEN_SEARCHLISTVIEWMODEL_SEARCHPROGRESS, Integer.class, new BindingConsumer<Integer>() {
            @Override
            public void call(Integer integer) {
                searchProgress.set(integer + " %");
            }
        });
    }
}
