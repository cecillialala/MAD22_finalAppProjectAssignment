package dk.au.group02_mad22_spring_appproject.api;

import dk.au.group02_mad22_spring_appproject.model.Categories;
import dk.au.group02_mad22_spring_appproject.model.Meals;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
// https://guides.codepath.com/android/consuming-apis-with-retrofit
public interface FoodApi {
    @GET("latest.php")
    Call<Meals> getMeal();

    @GET("categories.php")
    Call<Categories> getCategories();

    @GET("filter.php")
    Call<Meals> getMealByCategory(@Query("c") String category);

    @GET("search.php")
    Call<Meals> getMealByName(@Query("s") String mealName);

}
