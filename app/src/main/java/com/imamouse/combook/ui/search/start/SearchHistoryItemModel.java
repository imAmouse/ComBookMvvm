package com.imamouse.combook.ui.search.start;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class SearchHistoryItemModel extends ItemViewModel<SearchStartViewModel> {

    public ObservableField<String> searchText = new ObservableField<>();

    public SearchHistoryItemModel(@NonNull SearchStartViewModel viewModel, String searchText) {
        super(viewModel);
        this.searchText.set(searchText);
    }
}
