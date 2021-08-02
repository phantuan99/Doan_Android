package com.example.da_mientay.ui.fooddetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.da_mientay.Common.Common;
import com.example.da_mientay.Model.Addon;
import com.example.da_mientay.Model.Food;
import com.example.da_mientay.Model.Size;
import com.example.da_mientay.R;
import com.example.da_mientay.Signin;
import com.example.da_mientay.ui.foodlist.FoodListViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FoodDetailFragment extends Fragment implements TextWatcher {

    private FoodDetailViewModel foodDetailViewModel;
    private BottomSheetDialog bottomSheetDialog;

    private Unbinder unbinder;
    @BindView(R.id.img_food)
    ImageView img_food;
    @BindView(R.id.btn_cart)
    FloatingActionButton btnCart;
    @BindView(R.id.food_name)
    TextView food_name;
    @BindView(R.id.food_description)
    TextView food_description;
    @BindView(R.id.food_price)
    TextView food_price;
    @BindView(R.id.rdo_group_size)
    RadioGroup rdo_group_size;
    @BindView(R.id.txt_number)
    TextView txt_number;
    @BindView(R.id.btn_minus)
    Button btn_minus;
    @BindView(R.id.btn_plus)
    Button btn_plus;
    @BindView(R.id.img_add_addOn)
    ImageView img_add_addOn;
    @BindView(R.id.chipG_selected_addon)
    ChipGroup chipG_selected_addon;

    int showPrice;

    //N
    int total =1;


    //View inflate
    ChipGroup chipGroup_addon;
    EditText edt_search;

    @OnClick(R.id.img_add_addOn)
    void  onAddonClick()
    {

            //Hiển thị tất cả option addon
            displayAddonList();
            bottomSheetDialog.show();

    }

    private void displayAddonList() {
        if((Common.selectedFood.getAddon().size())>0);
        {
            //Xóa các check
            chipGroup_addon.clearCheck();
            chipGroup_addon.removeAllViews();

            edt_search.addTextChangedListener(this);

            //Thêm tất cả view
            for(Addon addon : Common.selectedFood.getAddon())
            {

                Chip chip = (Chip) getLayoutInflater().inflate(R.layout.layout_addon_item,null);
                chip.setText(new StringBuilder(addon.getName()).append("(+VND")
                        .append(addon.getPrice()).append(")"));
                chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked)
                    {
                        if(Common.selectedFood.getUserSelectedAddon() ==null)
                            Common.selectedFood.setUserSelectedAddon(new ArrayList<>());
                        Common.selectedFood.getUserSelectedAddon().add(addon);

                    }
                });
                chipGroup_addon.addView(chip);

            }
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        foodDetailViewModel =
                new ViewModelProvider(this).get(FoodDetailViewModel.class);
        View root  =inflater.inflate(R.layout.fragment_food_detail,container,false);
        unbinder = ButterKnife.bind(this,root);
        init();
        plusandminus();
        foodDetailViewModel.getMutableLiveDataFood().observe(getViewLifecycleOwner(),food -> {
            showInfo(food);
        });
        return root;
    }

    private void plusandminus() {
        int temp;
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(total >1) {
                    total--;
                    txt_number.setText(String.valueOf(total));
                    updatePrice();
                }
                else
                    Toast.makeText(getActivity(),"1 là nhỏ nhất",Toast.LENGTH_SHORT).show();
            }

        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(total<10) {
                   total++;
                   txt_number.setText(String.valueOf(total));
                   updatePrice();
               }else
               {
                   Toast.makeText(getActivity(),"10 là lớn nhất",Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    private void init() {
        bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.DialogStyle);
        View layout_addon_display = getLayoutInflater().inflate(R.layout.layout_addon_display,null);
        chipGroup_addon = (ChipGroup) layout_addon_display.findViewById(R.id.chipG_addon);
        edt_search =(EditText) layout_addon_display.findViewById(R.id.edt_search);
        bottomSheetDialog.setContentView(layout_addon_display);

        bottomSheetDialog.setOnDismissListener(dialog -> {
            displaySelectedAddon();
            updatePrice();
        });
    }

        private void displaySelectedAddon() {
        if(Common.selectedFood.getUserSelected() != null && Common.selectedFood.getUserSelectedAddon().size() >0)
        {
            // xóa view đã chọn
            chipG_selected_addon.removeAllViews();
            // Thêm tất cả addon đã có vào list
            for(Addon addon : Common.selectedFood.getUserSelectedAddon())
            {
                Chip chip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip_delete_icon,null);
                chip.setText(new StringBuilder(addon.getName()).append("VND")
                .append(addon.getPrice()).append(")"));
                chip.setClickable(false);
                chip.setOnCloseIconClickListener(v -> {
                    // Xóa khi user xóa
                    chipG_selected_addon.removeView(v);
                    Common.selectedFood.getUserSelectedAddon().remove(addon);
                    updatePrice();
                });
                chipG_selected_addon.addView(chip);
            }
        }else
            chipG_selected_addon.removeAllViews();
    }

    /**
     * Phương thức gán text và hình vào View
     * @param food
     */
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

        //Size
        for(Size size : Common.selectedFood.getSize())
        {
            RadioButton rdoButton = new RadioButton(getContext());
            rdoButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked)
                    Common.selectedFood.setUserSelected(size);
                //Cập nhật giás
                updatePrice();

            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f);
            rdoButton.setLayoutParams(params);
            rdoButton.setText(size.getName());
            rdoButton.setTag(size.getPrice());

            rdo_group_size.addView(rdoButton);
        }
        if(rdo_group_size.getChildCount()>0)
        {
            RadioButton rdoButton = (RadioButton)rdo_group_size.getChildAt(0);
            // Mac dinh la chon cai dau tien
            rdoButton.setChecked(true);
        }
        updatePrice();
    }

    private void updatePrice() {
        int totalPrice  = Common.selectedFood.getPrice();
        int sl = Integer.parseInt(txt_number.getText().toString());
        int foodPrice = Common.selectedFood.getUserSelected().getPrice();



        //Addon
        if(Common.selectedFood.getUserSelectedAddon()!=null && Common.selectedFood.getUserSelectedAddon().size()>0)
            for(Addon addon : Common.selectedFood.getUserSelectedAddon())
                totalPrice +=Integer.valueOf(addon.getPrice());

        //Size
        totalPrice += foodPrice;

        showPrice = totalPrice * sl;

        food_price.setText(new StringBuilder("").append(showPrice).toString());
    }

    //Từ watcher text search
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        chipGroup_addon.clearCheck();
        chipGroup_addon.removeAllViews();

        for(Addon addon : Common.selectedFood.getAddon())
        {
                if(addon.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                    Chip chip = (Chip) getLayoutInflater().inflate(R.layout.layout_addon_item, null);
                    chip.setText(new StringBuilder(addon.getName()).append("(+VND")
                            .append(addon.getPrice()).append(")"));
                    chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            if (Common.selectedFood.getUserSelectedAddon() == null)
                                Common.selectedFood.setUserSelectedAddon(new ArrayList<>());
                            Common.selectedFood.getUserSelectedAddon().add(addon);

                        }
                    });
                    chipGroup_addon.addView(chip);
                }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}