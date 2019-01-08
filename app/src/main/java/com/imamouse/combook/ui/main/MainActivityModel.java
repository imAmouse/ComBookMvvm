package com.imamouse.combook.ui.main;

import android.app.ActivityOptions;
import android.app.Application;
import android.arch.lifecycle.MediatorLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.imamouse.combook.R;
import com.imamouse.combook.ui.search.SearchActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class MainActivityModel extends BaseViewModel {

    public MainActivityModel(@NonNull Application application) {
        super(application);
    }

    public MediatorLiveData<MainActivityModel> nightModeSwitch = new MediatorLiveData<>();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //搜索Topbar
    public View.OnClickListener topbarOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle extras = ActivityOptions.makeCustomAnimation(
                    getApplication(), R.anim.fade_in, R.anim.fade_out).toBundle();
            startActivity(SearchActivity.class, extras);
        }
    };

    // 切换夜间模式
    public View.OnClickListener nightModeOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nightModeSwitch.setValue(MainActivityModel.this);
        }
    };
}
