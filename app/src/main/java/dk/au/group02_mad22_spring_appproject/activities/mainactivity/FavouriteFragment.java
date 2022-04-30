package dk.au.group02_mad22_spring_appproject.activities.mainactivity;

import static dk.au.group02_mad22_spring_appproject.activities.mainactivity.MainActivity.EXTRA_DETAIL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.au.group02_mad22_spring_appproject.Database.AppDatabase;
import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.ViewModel.DetailsViewModel;
import dk.au.group02_mad22_spring_appproject.activities.detailsactivity.DetailActivity;
import dk.au.group02_mad22_spring_appproject.adapters.FavouriteAdapter;
import dk.au.group02_mad22_spring_appproject.model.Meals;

public class FavouriteFragment extends FragmentActivity implements FavouriteAdapter.OnItemClickListener{
    FavouriteAdapter adapter;
    private List<Meals.Meal> listOfMeals;
    AppDatabase db;
// TODO Lav den om til LiveData

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favourite);

        final RecyclerView mealsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO Lav den om til HomeViewModel


        DetailsViewModel vm = new ViewModelProvider(this).get(DetailsViewModel.class);
        List<Meals.Meal> list= vm.getFoodObject();
        listOfMeals = list;
        adapter = new FavouriteAdapter(list);
        mealsRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }


    /* Her får vi en bug */
    @Override
    public void onItemClick(int position) {
        TextView mealName = findViewById(R.id.fave_meal_name);
        Intent detailIntent = new Intent(this, DetailActivity.class);
        Meals.Meal meal = listOfMeals.get(position);

        detailIntent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
        detailIntent.putExtra("meal", meal);
        detailIntent.putExtra("position", position);

        startActivity(detailIntent);

    }
}

