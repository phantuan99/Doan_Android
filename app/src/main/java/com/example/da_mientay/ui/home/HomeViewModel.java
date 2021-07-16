package com.example.da_mientay.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.da_mientay.Callback.IBestSellerCallBackListener;
import com.example.da_mientay.Callback.IPopularCallBackListener;
import com.example.da_mientay.Common.Common;
import com.example.da_mientay.Model.BestSeller;
import com.example.da_mientay.Model.PopularCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements IPopularCallBackListener, IBestSellerCallBackListener {

    private MutableLiveData<List<PopularCategory>> popularList;

    private MutableLiveData<List<BestSeller>> bestSellerList;

    private IPopularCallBackListener popularCallBackListener;
    private IBestSellerCallBackListener bestSellerCallBackListener;

    private  MutableLiveData<String> messageError;


    //getter
    public MutableLiveData<List<PopularCategory>> getPopularList() {
        if(popularList == null)
        {
            popularList = new MutableLiveData<>();
            messageError = new MutableLiveData<>();
            loadPopularList();
        }
        return popularList;
    }

    public MutableLiveData<List<BestSeller>> getBestSellerList() {
        if(bestSellerList == null)
        {
            bestSellerList = new MutableLiveData<>();
            messageError = new MutableLiveData<>();
            loadBestSellerList();
        }
        return bestSellerList;
    }

    private void loadBestSellerList() {
        List<BestSeller> temp = new ArrayList<>();
        DatabaseReference bestSellerRef = FirebaseDatabase.getInstance().getReference(Common.BESTSELLER_REF);
        bestSellerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot itemSnapShot: snapshot.getChildren())
                {
                    BestSeller model = itemSnapShot.getValue(BestSeller.class);
                    temp.add(model);
                }
                bestSellerCallBackListener.onBestSellerLoadSucess(temp);
            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                bestSellerCallBackListener.onBestSellerLoadFailed(error.getMessage());
            }
        });
    }

    private void loadPopularList() {
        List<PopularCategory> temp = new ArrayList<>();
        DatabaseReference popularRef = FirebaseDatabase.getInstance().getReference(Common.POPULAR_CATEGORY_REF);
        popularRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for(DataSnapshot itemSnapShot: snapshot.getChildren())
                {
                    PopularCategory model = itemSnapShot.getValue(PopularCategory.class);
                    temp.add(model);
                }
                 popularCallBackListener.onPopularLoadSuccess(temp);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                popularCallBackListener.onPopularLoadFailed(error.getMessage());
            }
        });
    }

    public IPopularCallBackListener getPopularCallBackListener() {
        return popularCallBackListener;
    }

    @Override
    public void onPopularLoadSuccess(List<PopularCategory> popularCategoryModel) {
        popularList.setValue(popularCategoryModel);
    }

    @Override
    public void onPopularLoadFailed(String message) {
    messageError.setValue(message);
    }

    private MutableLiveData<String> mText;

    public HomeViewModel() {
       popularCallBackListener = this;
       bestSellerCallBackListener = this;
    }

    public LiveData<String> getText() {
        return mText;
    }


    @Override
    public void onBestSellerLoadSucess(List<BestSeller> bestSellerModels) {
        bestSellerList.setValue(bestSellerModels);
    }

    @Override
    public void onBestSellerLoadFailed(String message) {
        messageError.setValue(message);
    }
}