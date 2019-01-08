package com.imamouse.combook.ui.search.start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.imamouse.combook.R;
import com.imamouse.combook.databinding.FragmentSearchStartBinding;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;

public class SearchStartFragment extends BaseFragment<FragmentSearchStartBinding,SearchStartViewModel> {

    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.fragment_search_start;
    }

    @Override
    public int initVariableId() {
        return BR.searchModel;
    }

}
