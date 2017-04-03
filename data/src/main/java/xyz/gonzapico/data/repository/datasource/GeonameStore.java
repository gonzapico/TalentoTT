package xyz.gonzapico.data.repository.datasource;

import android.content.Context;
import io.reactivex.Observable;
import javax.inject.Inject;
import retrofit2.Response;
import retrofit2.Retrofit;
import xyz.gonzapico.data.Config;
import xyz.gonzapico.data.cloud.GeoNamesAPIService;
import xyz.gonzapico.data.di.CloudModule;
import xyz.gonzapico.data.di.DaggerCloudComponent;
import xyz.gonzapico.data.entity.ResponseAPIGeonames;

/**
 * Created by gfernandez on 25/02/17.
 */

public class GeonameStore implements GeonameDataStore {

  @Inject Retrofit retrofit;
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
  @Override public Observable<Response<ResponseAPIGeonames>> geonames(String city) {
    return restApi.geonames(city, Config.MAX_ROWS, Config.START_ROW, Config.LANGUAGE, Config.IS_NAME_REQUIRED, Config.STYLE, Config.FIRST_USER);
  }
}
