package com.imamouse.combook.ui.book;

import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dasu.blur.DBlur;
import com.imamouse.combook.R;
import com.imamouse.combook.databinding.ActivityBookInfoBinding;
import com.imamouse.combook.ui.search.SearchActivity;
import com.imamouse.combook.view.ArcImageView;
import com.imamouse.bookmodule.bean.Book;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class BookInfoActivity extends BaseActivity<ActivityBookInfoBinding, BookInfoViewModel> {
    //ActivityBookInfoBinding类是databinding框架自定生成的,对activity_book_info.xml

    public static String TOKEN_BOOKINFOACTIVITY_INITED = "token_bookinfoactivity_inited";

    ViewGroup.MarginLayoutParams ivTitleHeadBgParams;
    int marginTop;

    private void initBackground(String imageUrl) {
        //使用Glide框架加载图片
        final ArcImageView imageBookinfoBack = binding.includeBookHeader.imageviewBookinfoBack;
        final ImageView imageBookinfoBar = binding.bookinfoToolbarImage;
        Glide.with(imageBookinfoBack.getContext())
                .load(imageUrl)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        try {
                            // 将小说图片高斯模糊处理 并 加载到 背景及Toolbar
                            Bitmap bt = ((BitmapDrawable) resource).getBitmap();
                            ToastUtils.showShort("success");
                            DBlur.source(imageBookinfoBack.getContext(), bt)
                                    .intoTarget(imageBookinfoBack).modeNative().build().doBlurSync();
                            DBlur.source(imageBookinfoBar.getContext(), bt)
                                    .intoTarget(imageBookinfoBar).modeNative().build().doBlurSync();
                        } catch (Exception ex) {
                            ToastUtils.showShort(ex.getMessage());
                        }
                    }
                });
    }

    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.activity_book_info;
    }

    @Override
    public int initVariableId() {
        return BR.infoModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initParam();
    }

    @Override
    public void initParam() {
        this.setTheme(R.style.AppTheme_Background_Transparent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void initViewObservable() {
        //设置BookInfoLIst加载完成后attention淡出
        viewModel.initCharpterListOk.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Animation out = new AlphaAnimation(1.0f, 0.0f);
                out.setDuration(2000);
                out.setFillAfter(true);
                binding.bookinfoInitInfo.startAnimation(out);
            }
        });
    }

    @Override
    public void initData() {

        // toolbar 的高
        int toolbarHeight = binding.bookinfoToolbar.getLayoutParams().height;
        // 使背景图向上移动到图片的最低端，保留（toolbar）的高度
        ViewGroup.LayoutParams params = binding.bookinfoToolbarImage.getLayoutParams();
        ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) binding.bookinfoToolbarImage.getLayoutParams();
        marginTop = params.height - toolbarHeight;
        binding.bookinfoToolbarImage.setImageAlpha(0);
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);

        //设置Toolbar
        binding.bookinfoToolbar.setTitle("");//去除toolbar自带标题
        setSupportActionBar(binding.bookinfoToolbar);

        //获取 startActivity 时传入的 Bundle 对象
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            // 初始化电子书信息
            Book bundleBook = (Book) mBundle.getSerializable("BOOK");
            viewModel.initBookinfo(bundleBook);
            initBackground(bundleBook.getImageUrl());

            //加载背景图片
            Messenger.getDefault().register(this,
                    SearchActivity.TOKEN_SEARCHACTIVITY_SENDBLUR,
                    Bitmap.class,
                    new BindingConsumer<Bitmap>() {
                        @Override
                        public void call(Bitmap bitmap) {
                            if (bitmap != null) {
                                binding.ivBookInfoBackground.setImageBitmap(bitmap);
                            }
                        }
                    });
            Messenger.getDefault().sendNoMsg(TOKEN_BOOKINFOACTIVITY_INITED);
        }

        //nestScrollview滑动监听
        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int x, int y, int xOld, int yOld) {
                if (y < marginTop) {
                    // 根据 ScrollView 设置 Toolbar 透明度
                    binding.bookinfoToolbarImage.setImageAlpha((int) (((float) y / (float) marginTop) * 255));

                    //不知道为啥不起作用（更改状态栏图片位置）
                    //ivTitleHeadBgParams.setMargins(0, -y, 0, 0);
                } else {
                    binding.bookinfoToolbarImage.setImageAlpha(255);
                    //ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);
                }
            }
        });

        //binding.nestscrollviewChapterList.getLayoutParams().height = AndroidScreenUtil.getDpHeight() - toolbarHeight;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
