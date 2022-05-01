package dk.au.group02_mad22_spring_appproject.services;



import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.group02_mad22_spring_appproject.Database.AppDatabase;
import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.model.Meals;

import dk.au.group02_mad22_spring_appproject.repository.Repository;

public class ForegroundService extends Service {
    public static final String CHANNEL_ID = "FoodServiceChannel";
    Repository repository;
private List<Meals.Meal> foodlist;
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        new LongOperation().execute();

        repository = Repository.getInstance(getApplication());
        //repository.getAllMeals().observe(this, new Observer<Meals>() )

/*        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();*/

    }

    //https://www.youtube.com/watch?v=tTbd1Mfi-Sk
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void createNotification() {

        // TODO  her står en problem (FoodService.java:67) (FoodService.java:95) (FoodService.java:87)

      /*  AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();*/


        List<Meals.Meal> tempList = repository.getAllMeals();
        ArrayList<Meals.Meal> suggestMeal = new ArrayList<>(tempList);

        Random rand = new Random();
        int n = rand.nextInt(suggestMeal.size());

        Meals.Meal randomMeal = suggestMeal.get(n);

        Notification notification = new NotificationCompat.Builder(ForegroundService.this, CHANNEL_ID)
                .setContentTitle("Deep Recipes")
                .setContentText(getString(R.string.suggestion)+randomMeal.getStrMeal()+"?")

                .build();
        startForeground(1, notification);
        //stopSelf();
    }

    //https://www.youtube.com/watch?v=tTbd1Mfi-Sk
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // TODO Make it on forground Service
    private class LongOperation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            while (true)
            {
                try {
                    Thread.sleep(10000);
                    createNotification();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
