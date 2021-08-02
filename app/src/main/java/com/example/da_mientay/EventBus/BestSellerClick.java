package com.example.da_mientay.EventBus;

import com.example.da_mientay.Model.BestSeller;

public class BestSellerClick
{
    private BestSeller bestSeller;

    public BestSellerClick(BestSeller bestSeller) {
        this.bestSeller = bestSeller;
    }

    public BestSeller getBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(BestSeller bestSeller) {
        this.bestSeller = bestSeller;
    }
}
