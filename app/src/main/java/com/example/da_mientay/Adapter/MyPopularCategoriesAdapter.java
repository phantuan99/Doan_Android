package com.example.da_mientay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.da_mientay.Callback.IRecyclerClickListener;
import com.example.da_mientay.EventBus.PopularCategoryClick;
import com.example.da_mientay.Model.PopularCategory;
import com.example.da_mientay.R;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyPopularCategoriesAdapter extends RecyclerView.Adapter<MyPopularCategoriesAdapter.MyViewHolder> {

     Context context;
     List<PopularCategory> popularCategoryModelList;

    public MyPopularCategoriesAdapter(Context context, List<PopularCategory> popularCategoryModelList) {
        this.context = context;
        this.popularCategoryModelList = popularCategoryModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_popular_catagories,parent,false));

    }
    //Truyền hình ảnh và tên vào
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(popularCategoryModelList.get(position).getImage())
                    .into(holder.category_IMG);
        holder.txtCagoryName.setText(popularCategoryModelList.get(position).getName());

        //Lisnter item popular click and post sticky
        holder.setListener(new IRecyclerClickListener() {
            @Override
            public void onItemClickListener(View view, int pos) {
                EventBus.getDefault().postSticky(new PopularCategoryClick(popularCategoryModelList.get(pos)));
            }
        });
    }

    //Size của Category
    @Override
    public int getItemCount() {
        return popularCategoryModelList.size();
    }

    public class MyViewHolder  extends  RecyclerView.ViewHolder implements View.OnClickListener {

        Unbinder unbinder;
        @BindView(R.id.txtCagoryName)
        TextView txtCagoryName;
        @BindView(R.id.category_IMG)
        CircleImageView category_IMG;

        //set for popular click
        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(v,getAdapterPosition());
        }
    }
}
