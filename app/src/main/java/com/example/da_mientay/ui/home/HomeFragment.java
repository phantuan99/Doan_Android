package com.example.da_mientay.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.example.da_mientay.Adapter.MyBestSellerAdapter;
import com.example.da_mientay.Adapter.MyPopularCategoriesAdapter;
import com.example.da_mientay.R;
import com.example.da_mientay.databinding.FragmentHomeBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    Unbinder unbinder;
    @BindView(R.id.rcv_popular)
    RecyclerView rcv_popular;

    @BindView(R.id.viewPager)
    LoopingViewPager viewPager;

    LayoutAnimationController layoutAnimationController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

       View root = inflater.inflate(R.layout.fragment_home,container,false);
        unbinder = ButterKnife.bind(this,root);
        init();
        homeViewModel.getPopularList().observe(getViewLifecycleOwner(),popularCategories -> {
            // Khởi tạo adapter
            MyPopularCategoriesAdapter adapter = new MyPopularCategoriesAdapter(getContext(),popularCategories);
            rcv_popular.setAdapter(adapter);
            // Set animation vào recyleview
            rcv_popular.setLayoutAnimation(layoutAnimationController);
        });
        homeViewModel.getBestSellerList().observe(getViewLifecycleOwner(),bestSellers -> {

            MyBestSellerAdapter adapter =new MyBestSellerAdapter(getContext(),bestSellers,true);
            viewPager.setAdapter(adapter);
        });
        return  root;

    }
    private void init()
    {
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
        rcv_popular.setHasFixedSize(true);
        rcv_popular.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.resumeAutoScroll();
    }

    @Override
    public void onPause() {
        viewPager.pauseAutoScroll();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}