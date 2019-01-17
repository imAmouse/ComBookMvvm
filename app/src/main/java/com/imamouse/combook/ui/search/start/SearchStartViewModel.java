package com.imamouse.combook.ui.search.start;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.imamouse.combook.R;
import com.imamouse.combook.view.FlowLayoutManager;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.imamouse.bookmodule.bean.Book;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class SearchStartViewModel extends BaseViewModel {

    //给RecyclerView添加items
    public final ObservableList<ItemViewModel> searchRecommendItemModelObservableList = new ObservableArrayList<>();
    public final ObservableList<ItemViewModel> searchHistoryItemModelObservableArrayList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public final ItemBinding<ItemViewModel> searchRecommendItemModelItemBinding
            = ItemBinding.of(BR.recommendItemModel, R.layout.item_search_recommend);
    public final ItemBinding<ItemViewModel> searchHistoryItemModelItemBinding
            = ItemBinding.of(BR.historyItemModel, R.layout.item_search_history);

    public FlowLayoutManager flowLayoutManager = new FlowLayoutManager(false);

    private void initRecycler() {
        for (int i = 0; i < 5; i++) {
            searchRecommendItemModelObservableList
                    .add(new SearchRecommendItemModel(
                            this,
                            new Book(
                                    "书名未知",
                                    "作者未知",
                                    "https://csdnimg.cn/pubfooter/images/csdn_cs_qr.png",
                                    "",
                                    "",
                                    "",
                                    "")));
        }
    }

    /**
     * 生成二维码
     * ImageView binding:bitmapPic
     *
     * @param textContent
     * @return
     */
    public Bitmap getQR_Code(String textContent) {
        return CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getApplication().getResources(), R.mipmap.ic_launcher));
    }

    public SearchStartViewModel(@NonNull Application application) {
        super(application);
    }

    public void onStart() {
        initRecycler();
    }
}
