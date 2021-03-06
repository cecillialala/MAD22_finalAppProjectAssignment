package dk.au.group02_mad22_spring_appproject.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dk.au.group02_mad22_spring_appproject.model.Meals;

//https://developer.android.com/topic/libraries/architecture/room
//RESSOURCE: "Room Demo"
@Database(entities = {Meals.Meal.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract MealsDAO mealsDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "meal-database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }


}