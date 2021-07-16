package com.example.da_mientay.ui.foodlist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.da_mientay.Adapter.FoodListApdater;
import com.example.da_mientay.Adapter.MyBestSellerAdapter;
import com.example.da_mientay.Adapter.MyPopularCategoriesAdapter;
import com.example.da_mientay.Model.Food;
import com.example.da_mientay.R;
import com.example.da_mientay.ui.home.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodListFragment extends Fragment {

    private FoodListViewModel foodListViewModel;

    Unbinder unbinder;
    @BindView(R.id.rcv_food_list)
    RecyclerView rcv_food_list;

    LayoutAnimationController layoutAnimationController;

    FoodListApdater adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        foodListViewModel =
                new ViewModelProvider(this).get(FoodListViewModel.class);

        View root = inflater.inflate(R.layout.fragment_food_list,container,false);
        unbinder = ButterKnife.bind(this,root);

        initView();

        foodListViewModel.getMutableLiveDataFoodList().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                adapter = new FoodListApdater(getContext(),foods);
                rcv_food_list.setAdapter(adapter);
                rcv_food_list.setLayoutAnimation(layoutAnimationController);
            }
        });


        return root;
    }

    private void initView() {
        rcv_food_list.setHasFixedSize(true);
        rcv_food_list.setLayoutManager(new LinearLayoutManager(getContext()));

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
    }
}
