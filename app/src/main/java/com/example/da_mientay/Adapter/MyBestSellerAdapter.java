package com.example.da_mientay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;

import com.bumptech.glide.Glide;
import com.example.da_mientay.Model.BestSeller;
import com.example.da_mientay.R;


import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyBestSellerAdapter extends LoopingPagerAdapter<BestSeller> {

    @BindView(R.id.img_bestseller)
    ImageView img_bestseller;
    @BindView(R.id.txtBestSeller)
    TextView txtBestSeller;

    Unbinder unbinder;

    public MyBestSellerAdapter(Context context, List< BestSeller> itemList, boolean isInfinite) {
        super(context,itemList, isInfinite);
    }

    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
     //   ImageView imageView = (ImageView)convertView.findViewById(R.id.img_bestseller);
        unbinder = ButterKnife.bind(this,convertView);
        Glide.with(convertView).load(itemList.get(listPosition).getImage())
                .into(img_bestseller);
        txtBestSeller.setText(itemList.get(listPosition).getName());
    }


    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.layout_bestseller_item,container,false);

    }
}
