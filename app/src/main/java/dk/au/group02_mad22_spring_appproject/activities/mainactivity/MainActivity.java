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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dk.au.group02_mad22_spring_appproject.GoogleMaps.MapFragment;
import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.ViewModel.DetailsViewModel;
import dk.au.group02_mad22_spring_appproject.ViewModel.MainViewModel;
import dk.au.group02_mad22_spring_appproject.activities.category.CategoryActivity;
import dk.au.group02_mad22_spring_appproject.activities.detailsactivity.DetailActivity;
import dk.au.group02_mad22_spring_appproject.activities.loginactivity.LoginActivity;
import dk.au.group02_mad22_spring_appproject.adapters.RecyclerViewHomeAdapter;
import dk.au.group02_mad22_spring_appproject.adapters.ViewPagerHeaderAdapter;
import dk.au.group02_mad22_spring_appproject.api.Utils;
import dk.au.group02_mad22_spring_appproject.model.Categories;
import dk.au.group02_mad22_spring_appproject.model.Meals;
import dk.au.group02_mad22_spring_appproject.repository.HomeView;
import dk.au.group02_mad22_spring_appproject.repository.Repository;

public class MainActivity extends AppCompatActivity implements HomeView, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_DETAIL = "detail";
    private MainViewModel vm;
    @BindView(R.id.viewPagerHeader) ViewPager viewPagerMeal;
    @BindView(R.id.recyclerCategory) RecyclerView recyclerViewCategory;

    Repository.HomePresenter presenter;

    /* https://jakewharton.github.io/butterknife/ */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // TODO have to enable Service
        //Starting the service
        //Intent serviceIntent = new Intent(this.getApplicationContext(), FoodService.class);
        //startService(serviceIntent);
        vm = new ViewModelProvider(this).get(MainViewModel.class);
        presenter = new Repository.HomePresenter(this);
        presenter.getMeals();
        presenter.getCategories();

        /* https://stackoverflow.com/questions/31691859/setsupportactionbar-cannot-be-applied */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void showLoading() {
        findViewById(R.id.shimmerMeal).setVisibility(View.VISIBLE);
        findViewById(R.id.shimmerCategory).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        findViewById(R.id.shimmerMeal).setVisibility(View.GONE);
        findViewById(R.id.shimmerCategory).setVisibility(View.GONE);
    }

    @Override
    public void setMeal(List<Meals.Meal> meal) {
        ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(meal, this);
        viewPagerMeal.setAdapter(headerAdapter);
        viewPagerMeal.setPadding(20, 0, 150, 0);
        headerAdapter.notifyDataSetChanged();

        /* Idea taken from previous assignment */
        headerAdapter.setOnItemClickListener((view, position) -> {
            TextView mealName = view.findViewById(R.id.mealName);
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
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
                break;
            case R.id.nav_favourite:
                startActivity(new Intent(this, FavouriteFragment.class));
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
        }
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}