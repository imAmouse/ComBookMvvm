package com.imamouse.combook.ui.search.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasu.blur.BlurConfig;
import com.dasu.blur.DBlur;
import com.imamouse.combook.R;
import com.imamouse.combook.databinding.FragmentSearchListBinding;
import com.imamouse.combook.ui.search.SearchActivity;
import com.imamouse.combook.utils.SnapshotUtil;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class SearchListFragment extends BaseFragment<FragmentSearchListBinding, SearchListViewModel> {

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            viewModel.startSearch();
        }
    };

    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.fragment_search_list;
    }

    @Override
    public int initVariableId() {
        return BR.searchModel;
    }

    @Override
    public void initData() {
        // Set OnRefreshListener
        binding.swiperefreshSearchList.setOnRefreshListener(onRefreshListener);
    }

    @Override
    public SearchListViewModel initViewModel() {
        //View持有ViewModel的引用，如果没有特殊业务处理，这个方法可以不重写
        return ViewModelProviders.of(this).get(SearchListViewModel.class);
    }

    /**
     * 监听搜索click，切换Fragment
     */
    @Override
    public void initViewObservable() {
        viewModel.startSearch.observe(this, new Observer<SearchListViewModel>() {
            @Override
            public void onChanged(@Nullable SearchListViewModel searchListViewModel) {
                //调用 SwipeRefresh 开始搜索
                binding.swiperefreshSearchList.setRefreshing(true);
            }
        });

        viewModel.endSearch.observe(this, new Observer<SearchListViewModel>() {
            @Override
            public void onChanged(@Nullable SearchListViewModel searchViewModel) {
                binding.swiperefreshSearchList.setRefreshing(false);
            }
        });
    }


}
