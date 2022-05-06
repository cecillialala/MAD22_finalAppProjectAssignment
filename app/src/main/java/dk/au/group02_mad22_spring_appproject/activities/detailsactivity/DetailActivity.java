package dk.au.group02_mad22_spring_appproject.activities.detailsactivity;

import static dk.au.group02_mad22_spring_appproject.activities.mainactivity.MainActivity.EXTRA_DETAIL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dk.au.group02_mad22_spring_appproject.GoogleMaps.MapFragment;
import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.ViewModel.DetailsViewModel;
import dk.au.group02_mad22_spring_appproject.activities.mainactivity.FavouriteFragment;
import dk.au.group02_mad22_spring_appproject.api.Utils;
import dk.au.group02_mad22_spring_appproject.model.Meals;
import dk.au.group02_mad22_spring_appproject.repository.DetailView;
import dk.au.group02_mad22_spring_appproject.repository.Repository;

/*
*  For this part we have used //https://square.github.io/picasso/
*  The reason is that it downloads images in another thread while creating a placeholder
*  and also it handles recycling which is great
*/

public class DetailActivity extends AppCompatActivity implements DetailView {
    private static final String TAG = "DetailActivity";
    private DetailsViewModel vm;
    private TextView tvCategory, tvCountry, tvInstructions, tvIngredients, tvMeasures, tvYoutube, tvMap;
    private ImageView ivThumb, ivStar;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    public Meals.Meal tempMeal = new Meals.Meal();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate created");
        setContentView(R.layout.activity_detailed);


        findIDs(); //Find view by ID
        setupActionBar(); //ActionBar
        intentRepoVm();//Intent setting, viewmodel, repository and visual of adding to db

    }

    private void findIDs(){
        Log.d(TAG, "finding ids");
        toolbar = findViewById(R.id.toolbar);
        ivThumb = findViewById(R.id.mealThumb);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        tvCategory = findViewById(R.id.category);
        tvCountry = findViewById(R.id.country);
        tvInstructions = findViewById(R.id.instructions);
        tvIngredients = findViewById(R.id.ingredient);
        tvMeasures = findViewById(R.id.measure);
        tvYoutube = findViewById(R.id.youtube);
        tvMap = findViewById(R.id.source);
        ivStar = findViewById(R.id.star);

    }

    private void intentRepoVm(){
        Log.d(TAG, "Getting intent");
        Intent intent = getIntent();
        String mealName = intent.getStringExtra(EXTRA_DETAIL);


        Log.d(TAG, "Setting up viewModel and Repo");
        Repository.DetailPresenter presenter = new Repository.DetailPresenter(this);
        presenter.getMealById(mealName);
        vm = new ViewModelProvider(this).get(DetailsViewModel.class);
        List<Meals.Meal> list= vm.getFoodObject();
        tvCategory.setText(tempMeal.getStrCategory());

        Log.d(TAG, "Adding visual feedback for if meal is in favorites");
        if(list.size() != 0) {
            for(Meals.Meal m : list) {
                if(m.getStrMeal().equals(mealName)) {
                    ivStar.setImageResource(R.drawable.ic_filled_star);
                    return;
                }
            }
            ivStar.setImageResource(R.drawable.ic_outline_star);
        }
    }

    private void setupActionBar() {
        Log.d(TAG, "Actionbar is on");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.AppTheme_AppBarOverlay);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.dark_background)); //Collapsed background
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhiteDark)); //Little title
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhiteDark)); //Big title of meal
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "Menu is here");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_map:
                startActivity(new Intent(this, MapFragment.class));
                Log.d(TAG, "Going to map");
                break;
            case R.id.nav_favourite:
                startActivity(new Intent(this, FavouriteFragment.class));
                Log.d(TAG, "Going to favorites");
                break;
            case android.R.id.home :
                onBackPressed();
                Log.d(TAG, "Going back");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setMeal(Meals.Meal meal) {
        tempMeal = meal;

        //Setting and getting
        Picasso.get().load(meal.getStrMealThumb()).into(ivThumb);
        collapsingToolbarLayout.setTitle(meal.getStrMeal());
        tvCategory.setText(meal.getStrCategory());
        tvCountry.setText(meal.getStrArea());
        tvInstructions.setText(meal.getStrInstructions());

        //Ingredients
        if (!meal.getStrIngredient1().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient1());
        }
        if (!meal.getStrIngredient2().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient2());
        }
        if (!meal.getStrIngredient3().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient3());
        }
        if (!meal.getStrIngredient4().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient4());
        }
        if (!meal.getStrIngredient5().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient5());
        }
        if (!meal.getStrIngredient6().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient6());
        }
        if (!meal.getStrIngredient7().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient7());
        }
        if (!meal.getStrIngredient8().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient8());
        }
        if (!meal.getStrIngredient9().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient9());
        }
        if (!meal.getStrIngredient10().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient10());
        }
        if (!meal.getStrIngredient11().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient11());
        }
        if (!meal.getStrIngredient12().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient12());
        }
        if (!meal.getStrIngredient13().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient13());
        }
        if (!meal.getStrIngredient14().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient14());
        }
        if (!meal.getStrIngredient15().isEmpty()) {
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient15());
        }
        if (meal.getStrIngredient16() != null && !meal.getStrIngredient16().trim().isEmpty()){
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient16());
        }
        if (meal.getStrIngredient17() != null && !meal.getStrIngredient17().trim().isEmpty()){
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient17());
        }
        if (meal.getStrIngredient18() != null && !meal.getStrIngredient18().trim().isEmpty()){
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient18());
        }
        if (meal.getStrIngredient19() != null && !meal.getStrIngredient19().trim().isEmpty()){
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient19());
        }
        if (meal.getStrIngredient20() != null && !meal.getStrIngredient20().trim().isEmpty()){
            tvIngredients.append("\n \u2022 " + meal.getStrIngredient20());
        }

        //Measures
        if (!meal.getStrMeasure1().isEmpty() && !Character.isWhitespace(meal.getStrMeasure1().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure1());
        }
        if (!meal.getStrMeasure2().isEmpty() && !Character.isWhitespace(meal.getStrMeasure2().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure2());
        }
        if (!meal.getStrMeasure3().isEmpty() && !Character.isWhitespace(meal.getStrMeasure3().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure3());
        }
        if (!meal.getStrMeasure4().isEmpty() && !Character.isWhitespace(meal.getStrMeasure4().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure4());
        }
        if (!meal.getStrMeasure5().isEmpty() && !Character.isWhitespace(meal.getStrMeasure5().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure5());
        }
        if (!meal.getStrMeasure6().isEmpty() && !Character.isWhitespace(meal.getStrMeasure6().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure6());
        }
        if (!meal.getStrMeasure7().isEmpty() && !Character.isWhitespace(meal.getStrMeasure7().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure7());
        }
        if (!meal.getStrMeasure8().isEmpty() && !Character.isWhitespace(meal.getStrMeasure8().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure8());
        }
        if (!meal.getStrMeasure9().isEmpty() && !Character.isWhitespace(meal.getStrMeasure9().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure9());
        }
        if (!meal.getStrMeasure10().isEmpty() && !Character.isWhitespace(meal.getStrMeasure10().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure10());
        }
        if (!meal.getStrMeasure11().isEmpty() && !Character.isWhitespace(meal.getStrMeasure11().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure11());
        }
        if (!meal.getStrMeasure12().isEmpty() && !Character.isWhitespace(meal.getStrMeasure12().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure12());
        }
        if (!meal.getStrMeasure13().isEmpty() && !Character.isWhitespace(meal.getStrMeasure13().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure13());
        }
        if (!meal.getStrMeasure14().isEmpty() && !Character.isWhitespace(meal.getStrMeasure14().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure14());
        }
        if (!meal.getStrMeasure15().isEmpty() && !Character.isWhitespace(meal.getStrMeasure15().charAt(0))) {
            tvMeasures.append("\n : " + meal.getStrMeasure15());
        }
        if (meal.getStrMeasure16() != null && !meal.getStrMeasure16().trim().isEmpty()) {
            tvMeasures.append("\n : " + meal.getStrMeasure16());
        }
        if (meal.getStrMeasure17() != null && !meal.getStrMeasure17().trim().isEmpty()) {
            tvMeasures.append("\n : " + meal.getStrMeasure17());
        }
        if (meal.getStrMeasure18() != null && !meal.getStrMeasure18().trim().isEmpty()) {
            tvMeasures.append("\n : " + meal.getStrMeasure18());
        }
        if (meal.getStrMeasure19() != null && !meal.getStrMeasure19().trim().isEmpty()) {
            tvMeasures.append("\n : " + meal.getStrMeasure19());
        }
        if (meal.getStrMeasure20() != null && !meal.getStrMeasure20().trim().isEmpty()) {
            tvMeasures.append("\n : " + meal.getStrMeasure20());
        }

        tvYoutube.setOnClickListener(v -> {
            Intent intentYoutube = new Intent(Intent.ACTION_VIEW);
            intentYoutube.setData(Uri.parse(meal.getStrYoutube()));
            Log.d(TAG, "Going to youtube");
            startActivity(intentYoutube);
        });

        tvMap.setOnClickListener(v -> {
            startActivity(new Intent(this, MapFragment.class));
            Log.d(TAG, "Going to map from here too!");
        });

        //Adding to fave
        ivStar.setOnClickListener(v -> {
            List<Meals.Meal> list= vm.getFoodObject();
            if(list.size() != 0) {
                for(Meals.Meal m : list) {
                    if(m.getIdMeal().equals(tempMeal.getIdMeal())) {
                        vm.delete(m);
                        ivStar.setImageResource(R.drawable.ic_outline_star);
                        Log.d(TAG, "A meal has been abandoned by the fat gods");
                        return;
                    }
                }
            }
            vm.addMeals(tempMeal);
            ivStar.setImageResource(R.drawable.ic_filled_star);
            Log.d(TAG, "A mean has been favored by the fat gods");
        });
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(this, "Error: This is not loading!", message);
    }


}

