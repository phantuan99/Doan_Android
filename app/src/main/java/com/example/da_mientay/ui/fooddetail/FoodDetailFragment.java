package com.example.da_mientay.ui.fooddetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.da_mientay.Common.Common;
import com.example.da_mientay.Model.Food;
import com.example.da_mientay.R;
import com.example.da_mientay.ui.foodlist.FoodListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodDetailFragment extends Fragment {

    private FoodDetailViewModel foodDetailViewModel;

    private Unbinder unbinder;
    @BindView(R.id.img_food)
    ImageView img_food;
    @BindView(R.id.btn_cart)
    FloatingActionButton btnCart;
    @BindView(R.id.btn_rating)
    FloatingActionButton btn_rating;
    @BindView(R.id.food_name)
    TextView food_name;
    @BindView(R.id.food_description)
    TextView food_description;
    @BindView(R.id.food_price)
    TextView food_price;
//    @BindView(R.id.btn_number)
//    ElegantNumberButton btn_number;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        foodDetailViewModel =
                new ViewModelProvider(this).get(FoodDetailViewModel.class);
        View root  =inflater.inflate(R.layout.fragment_food_detail,container,false);
        unbinder = ButterKnife.bind(this,root);
        foodDetailViewModel.getMutableLiveDataFood().observe(getViewLifecycleOwner(),food -> {
            showInfo(food);
        });
        return root;
    }

    private void showInfo(Food food) {
        Glide.with(getContext()).load(food.getImage())
                .into(img_food);
        food_name.setText(new StringBuilder(food.getName()));
        food_description.setText(new StringBuilder(food.getDescription()));
        food_price.setText(new StringBuilder("VND ")
                .append(food.getPrice()).toString());

        ((AppCompatActivity)getActivity())
                .getSupportActionBar()
                .setTitle(Common.selectedFood.getName());

    }



}