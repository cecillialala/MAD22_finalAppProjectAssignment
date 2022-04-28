package dk.au.group02_mad22_spring_appproject.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
//https://developer.android.com/topic/libraries/architecture/room
//https://developer.android.com/training/data-storage/room

import dk.au.group02_mad22_spring_appproject.model.Meals;

@Dao
public interface MealsDAO {
    @Query("SELECT * FROM mealsTable")
    public List<Meals.Meal> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllMeals(Meals.Meal m);

    @Update
    public void updateMeals(List<Meals.Meal> meals);

    @Delete
    public void delete(Meals.Meal m);

    @Query("SELECT * FROM mealsTable where favourite LIKE :status")
    List<Meals.Meal> getFavouriteMeals(Integer status);

}