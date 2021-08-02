package com.example.da_mientay;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da_mientay.Common.Common;
import com.example.da_mientay.EventBus.BestSellerClick;
import com.example.da_mientay.EventBus.CategoryClick;
import com.example.da_mientay.EventBus.FoodItemClick;
import com.example.da_mientay.EventBus.PopularCategoryClick;
import com.example.da_mientay.Model.Category;
import com.example.da_mientay.Model.Food;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.da_mientay.databinding.ActivityHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private NavController navController;
    private DrawerLayout drawer;

    TextView txt_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_menu, R.id.nav_slideshow,
                R.id.nav_foodlist,R.id.nav_fooddetail)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //Set ten
        View headerView = navigationView.getHeaderView(0);
        txt_Name = (TextView)headerView.findViewById(R.id.txtName);
        txt_Name.setText(Common.currentUser.getName());

        //Custom view nav
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
         navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    //EventBus
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public  void onCategorySelected(CategoryClick event)
    {
        if(event.isSuccess())
        {
            navController.navigate(R.id.nav_foodlist);
          //  Toast.makeText(this,"Click "+event.getCategoryModel(),Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public  void onFoodItemClick(FoodItemClick event)
    {
        if(event.isSuccess())
        {
            navController.navigate(R.id.nav_fooddetail);

        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public  void onPopularItemCLick(PopularCategoryClick event)
    {
        if(event.getPopularCategory()!=null)
        {
            FirebaseDatabase.getInstance()
                    .getReference("Category")
                    .child(event.getPopularCategory().getMenu_id())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                Common.categorySelected = snapshot.getValue(Category.class);

                                //Load food
                                FirebaseDatabase.getInstance()
                                        .getReference("Category")
                                        .child(event.getPopularCategory().getMenu_id())
                                        .child("foods")
                                        .orderByChild("id")
                                        .equalTo(event.getPopularCategory().getFood_id())
                                        .limitToLast(1)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.exists())
                                                {
                                                    for(DataSnapshot itemSnapShot:snapshot.getChildren())
                                                    {
                                                        Common.selectedFood = itemSnapShot.getValue(Food.class);
                                                    }
                                                    navController.navigate(R.id.nav_fooddetail);
                                                }
                                                else
                                                {
                                                    Toast.makeText(Home.this, "Item không tồn tại",Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Home.this, ""+error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public  void onBestSellerClick(BestSellerClick event)
    {
        if(event.getBestSeller()!=null)
        {
            FirebaseDatabase.getInstance()
                    .getReference("Category")
                    .child(event.getBestSeller().getMenu_id())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                Common.categorySelected = snapshot.getValue(Category.class);

                                //Load food
                                FirebaseDatabase.getInstance()
                                        .getReference("Category")
                                        .child(event.getBestSeller().getMenu_id())
                                        .child("foods")
                                        .orderByChild("id")
                                        .equalTo(event.getBestSeller().getFood_id())
                                        .limitToLast(1)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.exists())
                                                {
                                                    for(DataSnapshot itemSnapShot:snapshot.getChildren())
                                                    {
                                                        Common.selectedFood = itemSnapShot.getValue(Food.class);
                                                    }
                                                    navController.navigate(R.id.nav_fooddetail);
                                                }
                                                else
                                                {
                                                    Toast.makeText(Home.this, "Item không tồn tại",Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Home.this, ""+error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        item.setChecked(true);
        drawer.closeDrawers();
        switch (item.getItemId())
        {
            case R.id.nav_home:
                navController.navigate(R.id.nav_home);
                break;
                case R.id. nav_menu:
                navController.navigate(R.id.nav_menu);
                break;
        }
        return true;
    }

}