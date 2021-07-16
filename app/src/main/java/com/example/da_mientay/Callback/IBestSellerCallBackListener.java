package com.example.da_mientay.Callback;

import com.example.da_mientay.Model.BestSeller;

import java.util.List;

public interface IBestSellerCallBackListener {

    void onBestSellerLoadSucess(List<BestSeller> bestSellerModels);
    void onBestSellerLoadFailed(String message);
}
