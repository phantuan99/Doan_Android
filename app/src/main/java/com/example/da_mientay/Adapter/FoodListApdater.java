package com.example.da_mientay.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.da_mientay.Model.Food;
import com.example.da_mientay.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodListApdater extends RecyclerView.Adapter<FoodListApdater.MyViewHolder>{

    private Context context;
    private List<Food> foodModelList;

    public FoodListApdater(Context context, List<Food> foodModelList) {
        this.context = context;
        this.foodModelList = foodModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
            private Unbinder unbinder;
            @BindView(R.id.txt_food_name)
            TextView txt_food_name;
            @BindView(R.id.txt_food_price)
            TextView txt_food_price;
            @BindView(R.id.img_food_image)
            ImageView img_food_image;
            @BindView(R.id.img_favorite)
            ImageView img_favorite;
            @BindView(R.id.img_quick_cart)
            ImageView img_quick_cart;
        public MyViewHolder( View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.layout_food_item,parent,false));
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(foodModelList.get(position).getImage())
        .into(holder.img_food_image);
        holder.txt_food_price.setText(new StringBuilder("VND")
                .append(foodModelList.get(position).getPrice()));
        holder.txt_food_name.setText(new StringBuilder("")
                .append(foodModelList.get(position).getName()));
    }
    @Override
    public int getItemCount() {
        return foodModelList.size();
    }


}
