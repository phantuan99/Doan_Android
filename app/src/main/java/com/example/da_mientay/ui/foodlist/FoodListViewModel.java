package com.example.da_mientay.ui.foodlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.da_mientay.Common.Common;
import com.example.da_mientay.Model.Food;


import java.util.List;

public class FoodListViewModel extends ViewModel  {


    private MutableLiveData<List<Food>> mutableLiveDataFoodList;
    public FoodListViewModel()
    {

    }

    public MutableLiveData<List<Food>> getMutableLiveDataFoodList() {
        if(mutableLiveDataFoodList ==null)

            mutableLiveDataFoodList = new MutableLiveData<>();
            mutableLiveDataFoodList.setValue(Common.categorySelected.getFoods());

            return mutableLiveDataFoodList;
    }

}