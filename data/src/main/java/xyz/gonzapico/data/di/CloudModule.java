package xyz.gonzapico.data.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.gonzapico.data.cloud.GeoNamesAPIService;

import static android.content.Context.MODE_PRIVATE;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

/**
 * Created by gfernandez on 25/02/17.
 */

@Module public class CloudModule {

  String mBaseUrl;
  Context context;
  private final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
    @Override public Response intercept(Chain chain) throws IOException {
      Response originalResponse = chain.proceed(chain.request());
      if (isThereInternetConnection(CloudModule.this.context)) {
        int maxAge = 60; // read from cache for 1 minute
        return originalResponse.newBuilder()
            .header("Cache-Control", "public, max-age=" + maxAge)
            .build();
      } else {
        int maxStale = 60 * 60 * 24 * 14; // tolerate 2-weeks stale
        return originalResponse.newBuilder()
            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
            .build();
      }
    }
  };

  public CloudModule(String baseUrl, Context context) {
    this.mBaseUrl = baseUrl;
    this.context = context;
  }

  // Dagger will only look for methods annotated with @Provides
  @Provides @Singleton SharedPreferences providesSharedPreferences() {
    return context.getSharedPreferences("prueba", MODE_PRIVATE);
    //return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
  }

  @Provides @Singleton HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(BODY);
    return httpLoggingInterceptor;
  }

  @Provides @Singleton Gson provideGson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create();
  }

  @Provides @Singleton Retrofit provideRetrofit(Gson gson, OkHttpClient.Builder okHttpBuilder) {

    Retrofit retrofit =
        new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(mBaseUrl)
            .client(okHttpBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    retrofit.create(GeoNamesAPIService.class);

    return retrofit;
  }

  @Provides @Singleton Cache provideOkHttpCache() {
    int cacheSize = 10 * 1024 * 1024; // 10 MiB
    return new Cache(this.context.getCacheDir(), cacheSize);
  }

  @Provides @Singleton OkHttpClient.Builder provideOkHttpClient(Cache cache,
      HttpLoggingInterceptor loggingInterceptor) {
    OkHttpClient.Builder client = new OkHttpClient.Builder();
    client.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
    client.addInterceptor(loggingInterceptor);
    client.cache(cache);
    return client;
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  public boolean isThereInternetConnection(Context context) {
    boolean isConnected;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }
}
