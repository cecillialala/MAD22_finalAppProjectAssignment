package dk.au.group02_mad22_spring_appproject.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import dk.au.group02_mad22_spring_appproject.model.Meals;
import dk.au.group02_mad22_spring_appproject.repository.Repository;
//lektion 2 Demo viewmodel L2_6_clicker_app_viewmodel
public class MainViewModel extends AndroidViewModel {
    private Repository repository;
    private List<Meals.Meal> FoodObject;
    public MainViewModel(@NonNull Application application) {
        super(application);

        repository=Repository.getInstance(application);
        FoodObject= repository.getAllMeals();
    }

    public List<Meals.Meal> getFoodObject() {
        return repository.getAllMeals();
    }

    public void addMeals(Meals.Meal MealsName){
        repository.insertAllMeals(MealsName);
    }

    public void delete(Meals.Meal m){
        repository.delete(m);
    }
}
