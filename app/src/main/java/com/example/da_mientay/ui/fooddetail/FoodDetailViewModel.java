package com.example.da_mientay.ui.fooddetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.da_mientay.Common.Common;
import com.example.da_mientay.Model.Food;

import java.util.List;

public class FoodDetailViewModel extends ViewModel {

    private MutableLiveData<Food> mutableLiveDataFood;
    public FoodDetailViewModel()
    {

    }

    public MutableLiveData<Food> getMutableLiveDataFood() {
        if(mutableLiveDataFood ==null)

            mutableLiveDataFood = new MutableLiveData<>();
        mutableLiveDataFood.setValue(Common.selectedFood);
        return mutableLiveDataFood;
    }


}