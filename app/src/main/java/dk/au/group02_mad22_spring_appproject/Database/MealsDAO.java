package dk.au.group02_mad22_spring_appproject.Database;

import androidx.lifecycle.LiveData;
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
    List<Meals.Meal> getAllMeals();

    @Query("SELECT * FROM mealsTable")
    LiveData<List<Meals.Meal>> getAllMealsLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMeals(Meals.Meal m);

    @Update
    void updateMeals(List<Meals.Meal> meals);

    @Delete
    void delete(Meals.Meal m);

    @Query("SELECT * FROM mealsTable where favourite LIKE :status")
    List<Meals.Meal> getFavouriteMeals(Integer status);

}