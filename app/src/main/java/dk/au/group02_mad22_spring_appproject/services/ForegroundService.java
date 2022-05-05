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
import androidx.lifecycle.LifecycleService;
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

public class ForegroundService extends LifecycleService {
    public static final String CHANNEL_ID = "FoodServiceChannel";
    private static final String TAG = "Forground";
    boolean started = false;
    int count;
    private static final int NOTIFICATION_ID =42 ;
    Repository repository;

    ExecutorService execService;
private List<Meals.Meal> foodlist;

    public ForegroundService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        new LongOperation().execute();

        repository = Repository.getInstance(getApplication());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationchannel = new NotificationChannel(CHANNEL_ID, "Foreground Service", NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationchannel);
        }
        //build the notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Denne foregroundservice kører i baggrunden .")
                .setContentText("Den vil kigge efter ny Food.")
                .setSmallIcon(R.drawable.ic_baseline_restaurant_24)
                .setTicker("Food opdateres.")
                .build();

        Log.d(TAG, "On Start command test");
        startForeground(NOTIFICATION_ID, notification);


        //initate the background work - only starts if it is not already started


        if (!started) {
            started = true;
            doUpdateDrinkListWork();
        }



        return START_STICKY;

    }

    private void doUpdateDrinkListWork() {

        if (execService == null) {
            execService = Executors.newSingleThreadExecutor();
        }
        execService.submit(new Runnable() {
            @Override
            public void run() {
                count++;    //increment counter
                Log.d(TAG, "Count: " + count);
                try {
                    Thread.sleep(60000);
                    createNotification();

                    Log.d(TAG, "run: Food er opdateret");

                } catch (InterruptedException e) {
                    Log.e(TAG, "run: ERROR with doing the foreground service", e);
                }


                //the recursive bit - if started still true, call self again
                if (started) {
                    doUpdateDrinkListWork();
                }
            }
        });

    }

    //https://www.youtube.com/watch?v=tTbd1Mfi-Sk
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationChannel serviceChannel2 = new NotificationChannel(
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

        List<Meals.Meal> tempList = repository.getAllMeals();
        if(!tempList.isEmpty()){
            Log.d(TAG, "createNotification: "+ tempList);
            ArrayList<Meals.Meal> suggestMeal = new ArrayList<>(tempList);
            Log.d(TAG, "createNotification2222: "+ suggestMeal);
            Random rand = new Random();
            int n = rand.nextInt(suggestMeal.size());

            Meals.Meal randomMeal = suggestMeal.get(n);
            Log.d(TAG, "createNotification33: "+ randomMeal);
            Notification notification = new NotificationCompat.Builder(ForegroundService.this, CHANNEL_ID)
                    .setContentTitle("Get Fat")
                    .setContentText(getString(R.string.suggestion)+randomMeal.getStrMeal()+"?")
                    .setSmallIcon(R.drawable.ic_baseline_restaurant_24)
                    .build();
            Log.d(TAG, "createNotification33: "+ randomMeal.getStrMeal());
            startForeground(NOTIFICATION_ID, notification);
            //stopSelf();
        }else{
            Log.d(TAG, "Add Recipe to your favourite list: ");
              }

    }

    //https://www.youtube.com/watch?v=tTbd1Mfi-Sk
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }


    private class LongOperation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            while (true)
            {
                try {
                    Thread.sleep(60000);
                    createNotification();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
