package com.example.da_mientay.ui.menu;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_mientay.Adapter.CategoriesAdapter;
import com.example.da_mientay.R;
import com.example.da_mientay.databinding.FragmentMenuBinding;
import com.example.da_mientay.Common.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class MenuFragment extends Fragment {
    Unbinder unbinder;
    private MenuViewModel menuViewModel;
    @BindView(R.id.rcv_menu)
    RecyclerView recyclerView;
    AlertDialog dialog;
    LayoutAnimationController layoutAnimationController;
    CategoriesAdapter categoriesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                new ViewModelProvider(this).get(MenuViewModel.class);


        View root = inflater.inflate(R.layout.fragment_menu,container,false);
        unbinder = ButterKnife.bind(this,root);
        initView();
        menuViewModel.getMessageError().observe(getViewLifecycleOwner(),s -> {
           Toast.makeText(getContext(),""+s,Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        menuViewModel.getCategoryListMultable().observe(getViewLifecycleOwner(),categoryList -> {
            dialog.dismiss();
            categoriesAdapter = new CategoriesAdapter(getContext(),categoryList);
            recyclerView.setAdapter(categoriesAdapter);
            recyclerView.setLayoutAnimation(layoutAnimationController);
        });

        return root;
    }

    private void initView() {
        //Loading dialog
        dialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setCancelable(false)
                .setMessage(R.string.text_menu_dialog)
                .build();
        dialog.show();
        //Animation tu trai qua
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(categoriesAdapter !=null)
                {
                    switch (categoriesAdapter.getItemViewType(position))
                    {
                        case Common.DEFAULT_COLUMN_COUNT:return 1;
                        case Common.FULL_WIDTH_COLUMN:return 2;
                        default:return -1;
                    }
                }
                return -1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(8));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}