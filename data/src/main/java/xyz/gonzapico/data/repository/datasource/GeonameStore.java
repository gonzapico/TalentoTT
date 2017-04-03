package xyz.gonzapico.data.repository.datasource;

import android.content.Context;
import android.content.SharedPreferences;
import io.reactivex.Observable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import retrofit2.Response;
import retrofit2.Retrofit;
import xyz.gonzapico.data.Config;
import xyz.gonzapico.data.cloud.GeoNamesAPIService;
import xyz.gonzapico.data.di.CloudModule;
import xyz.gonzapico.data.di.DaggerCloudComponent;
import xyz.gonzapico.data.entity.Bbox;
import xyz.gonzapico.data.entity.ResponseAPIGeonames;
import xyz.gonzapico.data.entity.WeatherObservations;

/**
 * Created by gfernandez on 25/02/17.
 */

public class GeonameStore implements GeonameDataStore {

  @Inject Retrofit retrofit;
  @Inject SharedPreferences sharedPreferences;
  private GeoNamesAPIService restApi;
  private Context context;

  /**
   * Construct a {@link GeonameStore} based on connections to the api (Cloud).
   */
  @Inject public GeonameStore(Context context) {
    this.context = context;
    initDagger();
    initRetrofit();
  }

  private void initRetrofit() {
    restApi = retrofit.create(GeoNamesAPIService.class);
  }

  private void initDagger() {
    DaggerCloudComponent.builder()
        // list of modules that are part of this component need to be created here too
        .cloudModule(new CloudModule(Config.API_URL_GEONAMES, this.context)).build().inject(this);
  }

  @Override public Observable<Response<ResponseAPIGeonames>> geonames(String city, String user) {
    saveCitySearched(city);
    return restApi.geonames(city, Config.MAX_ROWS, Config.START_ROW, Config.LANGUAGE,
        Config.IS_NAME_REQUIRED, Config.STYLE, user);
  }

  @Override public Observable<Response<WeatherObservations>> weatherObservations(Bbox coordenates,
      String user) {
    return restApi.weather(coordenates.getNorth(), coordenates.getSouth(), coordenates.getEast(),
        coordenates.getWest(), user);
  }

  @Override public void saveCitySearched(String city) {
    Set<String> cities = sharedPreferences.getStringSet(Config.CITIES_CACHE, null);

    if (cities == null) {
      cities = new HashSet<String>();
    }
    cities.add(city);
    sharedPreferences.edit().putStringSet(Config.CITIES_CACHE, cities);
    sharedPreferences.edit().commit();
  }

  @Override public Observable<List<String>> citiesSearched() {
    Set<String> cities = sharedPreferences.getStringSet(Config.CITIES_CACHE, null);

    if (cities == null) {
      cities = new HashSet<String>();
    }
    String[] citiesArray = cities.toArray(new String[cities.size()]);

    return Observable.fromArray(Arrays.asList(citiesArray));
  }
}
