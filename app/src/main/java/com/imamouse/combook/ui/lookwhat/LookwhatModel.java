package com.imamouse.combook.ui.lookwhat;

import android.app.Application;
import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LookwhatModel extends BaseViewModel {

    public LookwhatModel(@NonNull Application application) {
        super(application);
    }

    //搜索按钮binding
    public BindingCommand searchCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("尚未添加功能");
        }
    });

}
