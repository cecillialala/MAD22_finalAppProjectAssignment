package dk.au.group02_mad22_spring_appproject.repository;

import java.util.List;

import dk.au.group02_mad22_spring_appproject.model.Meals;

public interface SearchView {
    void setMeals(List<Meals.Meal> meals);
    void onErrorLoading(String message);
}
