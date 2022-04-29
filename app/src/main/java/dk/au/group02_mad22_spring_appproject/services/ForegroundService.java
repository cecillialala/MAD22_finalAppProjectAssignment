package dk.au.group02_mad22_spring_appproject.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.group02_mad22_spring_appproject.models.Meal;
import dk.au.group02_mad22_spring_appproject.repository.Repository;

public class ForegroundService extends Service {

    public Repository repository;
    ExecutorService executorService;
    List<Meal> mealList = new ArrayList<>();
    public static final String TAG = "foregroundservice";
    public static final String Service_Channel = "ServiceChannel";
    public static final int NotificationID = 6;
    boolean start = false;

    public ForegroundService(){

    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Log.d(TAG, "onStartCommand: førIf");
        if (Build.VERSION.SDK_INT>= android.os.Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(Service_Channel, "Drink Service", NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            Log.d(TAG, "onStartCommand: commandIN");
        }

        //TODO fra repos
        updateBackground();
        return START_STICKY;

    }

    private void updateBackground() {
        Log.d(TAG, "updateInBackground: updateInBackgroundIN");
        if (executorService == null) {
            Log.d(TAG, "updateInBackground: inde i IF");
            executorService = Executors.newSingleThreadExecutor();
        }

        executorService.submit(() -> {
            Log.d(TAG, "run: we are inside run");
            //TODO
            List<Meal> mealList = repository.getallMeal().getValue();

            if (mealList != null) {
                Random random = new Random();
                //TODO
                Meal randomMeal = getallMeal.get(random.nextInt(getallMeal.size()));
                Notification notification = getNotification(randomMeal);
                startForeground(NotificationID, notification);
            }

            try {
                Log.d(TAG, "run: inside DrinkService" );
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Log.e(TAG, "run: Error when updating drink: ", e);
            }

            updateBackground();
        });
    }

    private Notification getNotification(Meal randomMeal) {
        return new NotificationCompat.Builder(getApplicationContext(), Service_Channel)
                .setContentTitle("Gruppe2 meal - Recommended Meal")
                //TODO
                .setContentText("Name: " + randomMeal.getMealName() + " - Category: " + randomMeal.getCategory())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        start = false;
        super.onDestroy();
    }


}
