package com.imamouse.combook.ui.main;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.imamouse.combook.R;
import com.imamouse.combook.databinding.ActivityMainBinding;
import com.imamouse.combook.ui.book.reader.BookReaderActivity;
import com.imamouse.combook.ui.home.HomeFragment;
import com.imamouse.combook.ui.lookwhat.LookwhatFragment;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityModel> {


    private int mIndex = -1; //记录当前Fragment index

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setIndexSelected(0);
                    return true;
                case R.id.navigation_dashboard:
                    if(!BookReaderActivity.isOpen()){
                        ToastUtils.showShort("您还没有看过小说鸭！");
                        return false;
                    }
                    startActivity(BookReaderActivity.class);
                    return true;
                case R.id.navigation_notifications:
                    setIndexSelected(1);
                    return true;
            }
            return false;
        }
    };

    /**
     * 初始化Fragment数组
     */
    private List<Fragment> mFragments;

    private void initFragment() {
        //设置Fragment数组
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new HomeFragment());
        mFragments.add(new LookwhatFragment());

        setIndexSelected(0);
    }

    /**
     * 更换Fragment，切换页面
     *
     * @param index
     */
    private void setIndexSelected(int index) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (mIndex != -1) {
            fragmentTransaction.hide(mFragments.get(mIndex));
        }
        if (!mFragments.get(index).isAdded()) {
            fragmentTransaction
                    .add(R.id.frame_layout, mFragments.get(index))
                    .commitAllowingStateLoss();
        } else
            fragmentTransaction
                    .show(mFragments.get(index))
                    .commitAllowingStateLoss();
        mIndex = index;
    }

    @Override
    public void initData() {

        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        // 加载页
        initFragment();
    }

    @Override
    public void initViewObservable() {
        // 切换夜间模式
        viewModel.nightModeSwitch.observe(this, new Observer<MainActivityModel>() {
            @Override
            public void onChanged(@Nullable MainActivityModel mainActivityModel) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate(); // 这个是刷新，不然不起作用
            }
        });
    }

    //ActivityLoginBinding类是databinding框架自定生成的,对activity_login.xml
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.mainModel;
    }

//    @Override
//    public MainActivityModel initViewModel() {
//        //View持有ViewModel的引用，如果没有特殊业务处理，这个方法可以不重写
//        return ViewModelProviders.of(this).get(MainActivityModel.class);
//    }
}
