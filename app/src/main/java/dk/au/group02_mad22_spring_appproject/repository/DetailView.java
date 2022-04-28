package dk.au.group02_mad22_spring_appproject.repository;

import dk.au.group02_mad22_spring_appproject.model.Meals;

public interface DetailView {
    void showLoading();
    void hideLoading();
    void setMeal(Meals.Meal meal);
    void onErrorLoading(String message);
}
