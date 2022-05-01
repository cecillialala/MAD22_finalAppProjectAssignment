package dk.au.group02_mad22_spring_appproject.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import dk.au.group02_mad22_spring_appproject.model.Meals;
import dk.au.group02_mad22_spring_appproject.models.Meal;
import dk.au.group02_mad22_spring_appproject.repository.Repository;

// TODO: Repository need to be made
public class SearchViewModel extends AndroidViewModel {

    private Repository repository;
    private List<Meals.Meal> mealsList;

    public SearchViewModel(@NonNull Application app) {
        super(app);
        this.repository = Repository.getInstance(app);
        this.mealsList = repository.getAllMeals();
    }

    //public void addMeal(Meals meal) {
    //    repository.addMeal(meal);
    //}

    //public void setMeal(String mealName) {
    //    repository.searchMeal(mealName, getApplication());
    //}

    public List<Meals.Meal> findMeal(String mealName) {
        return repository.findMeal(mealName);
    }

    public List<Meals.Meal> getMealsList() {
        return mealsList;
    }

    //public LiveData<List<Meals>> getCurrentMealsList() {
    //    return repository.getCurrentMealslist();
    //}
}
