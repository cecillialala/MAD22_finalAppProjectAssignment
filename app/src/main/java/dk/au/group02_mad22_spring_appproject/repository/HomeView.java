package dk.au.group02_mad22_spring_appproject.repository;

import java.util.List;

import dk.au.group02_mad22_spring_appproject.model.Categories;
import dk.au.group02_mad22_spring_appproject.model.Meals;

public interface HomeView {
    void showLoading();
    void hideLoading();
    void setMeal(List<Meals.Meal> meal);
    void setCategory(List<Categories.Category> category);
    void onErrorLoading(String message);

}
