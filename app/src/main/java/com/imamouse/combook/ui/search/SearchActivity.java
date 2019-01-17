package com.imamouse.combook.ui.search;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.dasu.blur.BlurConfig;
import com.dasu.blur.DBlur;
import com.dasu.blur.OnBlurListener;
import com.imamouse.combook.BR;
import com.imamouse.combook.R;
import com.imamouse.combook.databinding.ActivitySearchBinding;
import com.imamouse.combook.ui.book.BookInfoActivity;
import com.imamouse.combook.ui.search.list.SearchListFragment;
import com.imamouse.combook.ui.search.list.SearchListItemModel;
import com.imamouse.combook.ui.search.list.SearchListViewModel;
import com.imamouse.combook.ui.search.start.SearchStartFragment;
import com.imamouse.combook.utils.SnapshotUtil;
import com.imamouse.bookmodule.bean.Book;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> {

    public static String TOKEN_SEARCHACTIVITY_SENDBLUR = "token_searchactivity_sendblur";

    // 初始化 Fragment 表
    private List<Fragment> mFragments;

    private void initFragment() {
        //设置Fragment数组
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new SearchStartFragment());
        mFragments.add(new SearchListFragment());

        //默认显示第一个
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_search, mFragments.get(0))
                .commit();
    }

    /**
     * 更换Fragment，切换页面
     *
     * @param index 目标页面在 mFragment[] 的位置
     */
    private void setIndexSelected(int index) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_search, mFragments.get(index))
                .commit();
    }

    /**
     * 关闭软键盘
     */
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && this.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.activity_search;
    }

    @Override
    public int initVariableId() {
        return BR.searchModel;
    }

    @Override
    public void initParam() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void initData() {
        initFragment();

        // 搜索框获取焦点
        binding.edittextSearch.requestFocus();

        // 焦点在 EditText 时通过软键盘(硬键盘尚未尝试) 回车进行搜索
        binding.edittextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == android.view.KeyEvent.KEYCODE_ENTER
                        && event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                    viewModel.startSearchBefore();
                }
                return false;
            }
        });

        //
        binding.frameSearch.setDrawingCacheEnabled(true);
    }

    /**
     * 监听搜索 Search click
     * ->切换到 SearchListFragment
     * ->为cvSearchProgress(CardView)设置毛玻璃效果
     * ->关闭 输入法软键盘
     * <p>
     * 监听 SearchListItem 切换 BookInfoActivity
     * -> 全界面(SearchActivity.this)高斯模糊处理
     * -> 初始化 BookInfoActivity 并传入 高斯模糊Bitmap
     */
    @Override
    public void initViewObservable() {
        viewModel.startSearchBefore.observe(this, new Observer<SearchViewModel>() {
            @Override
            public void onChanged(@Nullable SearchViewModel searchViewModel) {
                setIndexSelected(1);
                hideKeyboard();
                binding.cvSearchProgress.setVisibility(View.VISIBLE);

                //为加载界面设置高斯模糊
                binding.frameSearch.buildDrawingCache();
                binding.frameSearch.post(new Runnable() {
                    @Override
                    public void run() {
                        // 截取ProgressBar图片
//                    Bitmap bitmap = SnapshotUtil
//                            .snapShotWithoutBar(binding.framelayoutSearchProgress, false);

                        //
                        Bitmap bitmap = Bitmap.createBitmap(binding.frameSearch.getDrawingCache());

                        int[] location = {0, 0};
                        binding.cvSearchProgress.getLocationInWindow(location);
                        bitmap = Bitmap.createBitmap(bitmap,
                                location[0],
                                location[1] - binding.toolbarSearch.getMeasuredHeight(),
                                binding.cvSearchProgress.getMeasuredWidth(),
                                binding.cvSearchProgress.getMeasuredHeight());
                        DBlur.source(binding.cvSearchProgress.getContext(), bitmap)
                                .intoTarget(binding.ivSearchProgress)
                                .mode(BlurConfig.MODE_NATIVE)
                                .build()
                                .doBlurSync();
                    }
                });
            }
        });

        Messenger.getDefault().register(this, SearchListViewModel.TOKEN_SEARCHLISTVIEWMODEL_SEARCHEND, new BindingAction() {
            @Override
            public void call() {
                binding.cvSearchProgress.setVisibility(View.INVISIBLE);
            }
        });

        Messenger.getDefault().register(this, SearchListItemModel.TOKEN_SEARCHLISTITEM_CLICKED, Book.class, new BindingConsumer<Book>() {
            @Override
            public void call(final Book book) {
                Bitmap snapshotBitmap = SnapshotUtil.snapShotWithoutBar(SearchActivity.this, false);
                DBlur.source(SearchActivity.this, snapshotBitmap)
                        .modeNative()
                        .build()
                        .doBlur(new OnBlurListener() {
                            @Override
                            public void onBlurSuccess(final Bitmap bitmap) {
                                // 当模糊动画完成时执行
                                if (book != null && bitmap != null) {
                                    Bundle mBundle = new Bundle();
                                    mBundle.putSerializable("BOOK", book);
                                    Messenger.getDefault().register(this,
                                            BookInfoActivity.TOKEN_BOOKINFOACTIVITY_INITED,
                                            new BindingAction() {
                                                @Override
                                                public void call() {
                                                    Messenger.getDefault().send(bitmap, TOKEN_SEARCHACTIVITY_SENDBLUR);
                                                }
                                            });
                                    viewModel.startActivity(BookInfoActivity.class, mBundle);
                                }
                            }

                            @Override
                            public void onBlurFailed() {

                            }
                        });
            }
        });
    }

}
