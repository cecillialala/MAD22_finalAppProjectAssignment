package dk.au.group02_mad22_spring_appproject.repository;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.function.Consumer;

import dk.au.group02_mad22_spring_appproject.Database.AppDatabase;
import dk.au.group02_mad22_spring_appproject.api.Utils;
import dk.au.group02_mad22_spring_appproject.model.Categories;
import dk.au.group02_mad22_spring_appproject.model.Meals;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository extends AppCompatActivity {
//RESSOURCE: "Room Demo"

    private static final String TAG = "Repoo";
    private final AppDatabase db;
    private final Context context;
    private static Repository repository;
    private List<Meals.Meal> FoodlistLive ;



    public Repository(Application app){
        this.context=app;
        db=AppDatabase.getAppDatabase(context.getApplicationContext());
        FoodlistLive= db.mealsDao().getAllMeals();

    }
    public static Repository getInstance(final Application application) {
        if (repository == null) {
            return repository = new Repository(application);
        }

        return repository;
    }

    public List<Meals.Meal> findMeal(String name){
        return db.mealsDao().findMeal(name);
    }


    public List<Meals.Meal> getAllMeals(){
        return db.mealsDao().getAllMeals();
    }

    public void insertAllMeals(Meals.Meal m){
        db.mealsDao().insertAllMeals(m);
    }
    public void delete(Meals.Meal m){
        db.mealsDao().delete(m);
    }


    public static class DetailPresenter {
        private DetailView view;

        public DetailPresenter(DetailView view) {
            this.view = view;
        }

        public void getMealById(String mealName) {



            Utils.getApi().getMealByName(mealName)
                    .enqueue(new Callback<Meals>() {
                        @Override
                        public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {

                            if (response.isSuccessful() && response.body() != null) {
                                view.setMeal(response.body().getMeals().get(0));
                            } else {
                                view.onErrorLoading(response.message());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Meals> call, @NonNull Throwable t) {

                            view.onErrorLoading(t.getLocalizedMessage());
                        }
                    });
        }
    }

    public static class SearchPresenter {
        private SearchView view;

        public SearchPresenter(SearchView view) {
            this.view = view;
        }

        public void getMealByName(String mealName) {

            view.showLoading();
            Call<Meals> mealsCall = Utils.getApi().getMealByName(mealName);
            mealsCall.enqueue(new Callback<Meals>() {
                @Override
                public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {
                    view.hideLoading();
                    if (response.isSuccessful() && response.body() != null) {
                        view.setMeals(response.body().getMeals());
                    } else {
                        view.onErrorLoading(response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Meals> call, @NonNull Throwable t) {
                    view.hideLoading();
                    view.onErrorLoading(t.getLocalizedMessage());
                }
            });

        }
    }

    public static class CategoryPresenter {
        private CategoryView view;

        public CategoryPresenter(CategoryView view) {
            this.view = view;
        }

        public void getMealByCategory(String category) {

            view.showLoading();
            Call<Meals> mealsCall = Utils.getApi().getMealByCategory(category);
            mealsCall.enqueue(new Callback<Meals>() {
                @Override
                public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {
                    view.hideLoading();
                    if (response.isSuccessful() && response.body() != null) {
                        view.setMeals(response.body().getMeals());
                    } else {
                        view.onErrorLoading(response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Meals> call, @NonNull Throwable t) {
                    view.hideLoading();
                    view.onErrorLoading(t.getLocalizedMessage());
                }
            });

        }
    }

    public static class HomePresenter {

        private HomeView view;

        public HomePresenter(HomeView view) {
            this.view = view;
        }

        /* https://square.github.io/retrofit/ */

        public void getMeals() {
            view.showLoading();
            Call<Meals> mealsCall = Utils.getApi().getMeal();

            mealsCall.enqueue(new Callback<Meals>() {
                @Override
                public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {
                    view.hideLoading();

                    if (response.isSuccessful() && response.body() != null) {
                        view.setMeal(response.body().getMeals());
                    }
                    else {
                        view.onErrorLoading(response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Meals> call, @NonNull Throwable t) {
                    view.hideLoading();
                    view.onErrorLoading(t.getLocalizedMessage());
                }
            });
        }


        public void getCategories() {
            view.showLoading();

            Call<Categories> categoriesCall = Utils.getApi().getCategories();
            categoriesCall.enqueue(new Callback<Categories>() {
                @Override
                public void onResponse(@NonNull Call<Categories> call, @NonNull Response<Categories> response) {
                    view.hideLoading();

                    if (response.isSuccessful() && response.body() != null) {
                        view.setCategory(response.body().getCategories());
                    }
                    else {
                        view.onErrorLoading(response.message());

                    }
                }

                @Override
                public void onFailure(@NonNull Call<Categories> call, @NonNull Throwable t) {
                    view.hideLoading();
                    view.onErrorLoading(t.getLocalizedMessage());
                }
            });
        }

    }

    private FirebaseLoginWithEmailAuth emailAuth;

    //region Authentication

    public boolean isLoggedIn() {
        return emailAuth.isLoggedIn();
    }


    public void createUserWithEmailAndPassword(String email, String password, Consumer<FirebaseUser> successCallback) {
        emailAuth.createUserWithEmailAndPassword(email, password, successCallback);
    }

    public void signInWithEmailAndPassword(String email, String password, Consumer<FirebaseUser> successCallback, Consumer<Exception> errorCallback) {
        emailAuth.signInWithEmailAndPassword(email, password, successCallback, errorCallback);
    }

    public void signOut() {
        emailAuth.signOut();
    }

    public void forgotPassword(String email) {
        emailAuth.forgotPassword(email);
    }

}
