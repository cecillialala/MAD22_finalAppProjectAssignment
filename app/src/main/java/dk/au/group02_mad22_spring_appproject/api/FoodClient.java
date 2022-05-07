package dk.au.group02_mad22_spring_appproject.api;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// https://www.section.io/engineering-education/making-api-requests-using-retrofit-android/#:~:text=Retrofit%20is%20a%20type%2Dsafe,Retrofit%20to%20make%20API%20requests.
public class FoodClient {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v2/9973533/";

    public static Retrofit getFoodClient() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(provideOkHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static Interceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }
// https://www.baeldung.com/guide-to-okhttp
//https://www.vogella.com/tutorials/JavaLibrary-OkHttp/article.html
    private static OkHttpClient provideOkHttp() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(provideLoggingInterceptor())
                .build();
    }
}
