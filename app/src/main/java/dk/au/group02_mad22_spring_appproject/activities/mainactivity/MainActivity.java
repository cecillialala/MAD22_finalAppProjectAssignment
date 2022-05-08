package dk.au.group02_mad22_spring_appproject.activities.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dk.au.group02_mad22_spring_appproject.GoogleMaps.MapFragment;
import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.ViewModel.MainViewModel;
import dk.au.group02_mad22_spring_appproject.activities.category.CategoryActivity;
import dk.au.group02_mad22_spring_appproject.activities.detailsactivity.DetailsActivity;
import dk.au.group02_mad22_spring_appproject.activities.loginactivity.LoginActivity;
import dk.au.group02_mad22_spring_appproject.activities.searchactivity.SearchActivity;
import dk.au.group02_mad22_spring_appproject.adapters.RecyclerViewHomeAdapter;
import dk.au.group02_mad22_spring_appproject.adapters.HeaderAdapter;
import dk.au.group02_mad22_spring_appproject.api.Utils;
import dk.au.group02_mad22_spring_appproject.model.Categories;
import dk.au.group02_mad22_spring_appproject.model.Meals;
import dk.au.group02_mad22_spring_appproject.repository.HomeView;
import dk.au.group02_mad22_spring_appproject.repository.Repository;
import dk.au.group02_mad22_spring_appproject.services.ForegroundService;

public class MainActivity extends AppCompatActivity implements HomeView, NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = "MainActivity";
    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_DETAIL = "detail";

    private DrawerLayout drawer;
    private MainViewModel vm;

    @BindView(R.id.viewPagerHeader) ViewPager viewPagerMeal;
    @BindView(R.id.recyclerCategory) RecyclerView recyclerViewCategory;

    Repository.HomePresenter presenter;
    Button btn_search;
    /* https://jakewharton.github.io/butterknife/ */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Creating on Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       setupUI();
        setupForegroundService();
    }

    private void setupForegroundService(){
        Intent serviceIntent = new Intent(this.getApplicationContext(), ForegroundService.class);
        startService(serviceIntent);
    }

    private void setupUI(){
        Log.d(TAG, "Finding IDs and all sorts of stuff");
        btn_search=findViewById(R.id.btn_search);

        vm = new ViewModelProvider(this).get(MainViewModel.class);
        presenter = new Repository.HomePresenter(this);
        presenter.getMeals();
        presenter.getCategories();

        Log.d(TAG, "Toolbar coming up");
        /* https://stackoverflow.com/questions/31691859/setsupportactionbar-cannot-be-applied */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "Drawer coming up");
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d(TAG, "Actionbar coming up");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ButterKnife.bind(this);

        Log.d(TAG, "Button coming up");
        btn_search.setOnClickListener(v -> GotoSearch());
    }

    //Shimmer meal made by haerulmuttaqin at https://github.com/haerulmuttaqin/FoodsApp-starting-code/blob/master/app/src/main/res/layout/item_view_pager_header_shimmer.xml
    @Override
    public void showLoading() {
        Log.d(TAG, "Placeholder hi");
        findViewById(R.id.shimmerMeal).setVisibility(View.VISIBLE);
        findViewById(R.id.shimmerCategory).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        Log.d(TAG, "Placeholder bye");
        findViewById(R.id.shimmerMeal).setVisibility(View.GONE);
        findViewById(R.id.shimmerCategory).setVisibility(View.GONE);
    }

    @Override
    public void setMeal(List<Meals.Meal> meal) {
        Log.d(TAG, "Setting meal");
        HeaderAdapter headerAdapter = new HeaderAdapter(meal, this);
        viewPagerMeal.setAdapter(headerAdapter);
        viewPagerMeal.setPadding(20, 0, 150, 0);
        headerAdapter.notifyDataSetChanged();

        headerAdapter.setOnItemClickListener((view, position) -> {
            TextView mealName = view.findViewById(R.id.mealName);
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
            startActivity(intent);
        });
    }

    @Override
    public void setCategory(List<Categories.Category> category) {
        RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(category, this);
        recyclerViewCategory.setAdapter(homeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2,
                GridLayoutManager.VERTICAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setNestedScrollingEnabled(true);
        homeAdapter.notifyDataSetChanged();

        homeAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(this, CategoryActivity.class);
            intent.putExtra(EXTRA_CATEGORY, (Serializable) category);
            intent.putExtra(EXTRA_POSITION, position);
            startActivity(intent);
        });
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(this, "Title", message);
    }

    //https://codinginflow.com/tutorials/android/navigation-drawer/part-3-fragments
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_map:
                startActivity(new Intent(this, MapFragment.class));
                Log.d(TAG, "Going to map");
                break;
            case R.id.nav_favourite:
                startActivity(new Intent(this, FavouritesFragment.class));
                Log.d(TAG, "Going to favorites");
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*https://www.youtube.com/watch?v=zYVEMCiDcmY*/
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
            Log.d(TAG, "Yeeting out");
        }
    }
    public void GotoSearch(){
        Intent searchList = new Intent(this, SearchActivity.class);
        Log.d(TAG, "Going to search");
        startActivity(searchList);

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        Log.d(TAG, "Going to logout screen");
        finish();
    }
}