package com.example.da_mientay.Callback;

import com.example.da_mientay.Model.PopularCategory;

import java.util.List;

public interface IPopularCallBackListener {
    void onPopularLoadSuccess(List<PopularCategory> popularCategoryModel);
    void onPopularLoadFailed(String message);

}
