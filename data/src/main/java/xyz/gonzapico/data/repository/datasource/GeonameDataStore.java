package xyz.gonzapico.data.repository.datasource;

import io.reactivex.Observable;
import retrofit2.Response;
import xyz.gonzapico.data.entity.Bbox;
import xyz.gonzapico.data.entity.ResponseAPIGeonames;
import xyz.gonzapico.data.entity.WeatherObservations;

/**
 * Created by gfernandez on 25/02/17.
 */

public interface GeonameDataStore {

  /***
   * Get an {@link Observable} which will emit a {@link ResponseAPIGeonames}.
   * @return {@link ResponseAPIGeonames}
   */
  Observable<Response<ResponseAPIGeonames>> geonames(String city, String user);

  /***
   * Get an {@link Observable} which will emit a {@link WeatherObservations}.
   * @param coordenates to let the request know coordenates (north, south, west and east)
   * @param user first or second username
   * @return {@link WeatherObservations}
   */
  Observable<Response<WeatherObservations>> weatherObservations(Bbox coordenates, String user);
}
