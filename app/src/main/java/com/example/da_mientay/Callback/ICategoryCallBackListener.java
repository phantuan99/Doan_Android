package com.example.da_mientay.Callback;

import com.example.da_mientay.Model.BestSeller;
import com.example.da_mientay.Model.Category;

import java.util.List;

public interface ICategoryCallBackListener {
    void onCategoryLoadSucess(List<Category> categoryModels);
    void onCategoryLoadFailed(String message);
}
