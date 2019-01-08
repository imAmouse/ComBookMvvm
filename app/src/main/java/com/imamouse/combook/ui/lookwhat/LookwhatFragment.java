package com.imamouse.combook.ui.lookwhat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.imamouse.combook.R;
import com.imamouse.combook.databinding.FragmentHomeBinding;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;

public class LookwhatFragment extends BaseFragment<FragmentHomeBinding,LookwhatModel> {

    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.fragment_lookwhat;
    }

    @Override
    public int initVariableId() {
        return BR.lookwhatModel;
    }
}
