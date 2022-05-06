package dk.au.group02_mad22_spring_appproject.activities.mainactivity;

import static dk.au.group02_mad22_spring_appproject.activities.mainactivity.MainActivity.EXTRA_DETAIL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.au.group02_mad22_spring_appproject.Database.AppDatabase;
import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.ViewModel.DetailsViewModel;
import dk.au.group02_mad22_spring_appproject.ViewModel.MainViewModel;
import dk.au.group02_mad22_spring_appproject.activities.detailsactivity.DetailActivity;
import dk.au.group02_mad22_spring_appproject.adapters.FavouriteAdapter;
import dk.au.group02_mad22_spring_appproject.model.Meals;

public class FavouriteFragment extends FragmentActivity {
    FavouriteAdapter adapter;
    private LiveData<List<Meals.Meal>> listOfMeals;
    private List<Meals.Meal> mealsList;
    private MainViewModel vm;

    Button backButton;
    AppDatabase db;
// TODO Lav den om til LiveData

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favourite);

        this.backButton = findViewById(R.id.btn_back);
        this.backButton.setOnClickListener(view -> onBackPressed());

        final RecyclerView mealsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO Lav den om til HomeViewModel

        vm = new ViewModelProvider(this).get(MainViewModel.class);
        LiveData<List<Meals.Meal>> list= vm.getFoodObject();
        listOfMeals = list;
        adapter = new FavouriteAdapter(mealsList);
        vm.getFoodObject().observe(this, new Observer<List<Meals.Meal>>() {
            @Override
            public void onChanged(List<Meals.Meal> meals) {
                mealsRecyclerView.setAdapter(adapter);
                adapter.updateMealsList(meals);
            }
        });
        adapter.setOnItemClickListener((view, position) -> {
            TextView mealName = view.findViewById(R.id.fave_meal_name);
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
            startActivity(intent);
        });
    }

    /* Her får vi en bug */
    /*@Override
    public void onItemClick(int position) {
        TextView mealName = findViewById(R.id.fave_meal_name);
        Intent detailIntent = new Intent(this, DetailActivity.class);
        Meals.Meal meal = mealsList.get(position);

        detailIntent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
        detailIntent.putExtra("meal", meal);
        detailIntent.putExtra("position", position);

        startActivity(detailIntent);

    }*/
}

