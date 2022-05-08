package dk.au.group02_mad22_spring_appproject.activities.mainactivity;

import static dk.au.group02_mad22_spring_appproject.activities.mainactivity.MainActivity.EXTRA_DETAIL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
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

public class FavouriteFragment extends FragmentActivity implements FavouriteAdapter.OnItemClickListener{
    public static final String TAG = "FavoriteFragment";
    FavouriteAdapter adapter;
    private List<Meals.Meal> listOfMeals;

    Button backButton;
    AppDatabase db;
// TODO Lav den om til LiveData

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favourite);

        settingUI();
    }

    private void settingUI(){
        Log.d(TAG, "Setting up UI..... We are in");
        this.backButton = findViewById(R.id.btn_back);
        this.backButton.setOnClickListener(view -> onBackPressed()); //Could be done in main but we did it like this to show off our onBackPressed skills

        final RecyclerView mealsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
        List<Meals.Meal> list= vm.getFoodObject();
        listOfMeals = list;
        adapter = new FavouriteAdapter(list);
        mealsRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }



    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "Clicking buttons");
        TextView mealName = findViewById(R.id.fave_meal_name);
        Intent detailIntent = new Intent(this, DetailActivity.class);
        Meals.Meal meal = listOfMeals.get(position);

        detailIntent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
        detailIntent.putExtra("meal", meal);
        detailIntent.putExtra("position", position);

        startActivity(detailIntent);

    }
}

