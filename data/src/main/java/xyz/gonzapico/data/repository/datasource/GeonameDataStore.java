package xyz.gonzapico.data.repository.datasource;

import retrofit2.Response;
import rx.Observable;
import xyz.gonzapico.data.entity.ResponseAPIGeonames;

/**
 * Created by gfernandez on 25/02/17.
 */

public interface GeonameDataStore {

  /***
   * Get an {@link Observable} which will emit a {@link ResponseAPIGeonames}.
   * @return {@link ResponseAPIGeonames}
   */
  Observable<Response<ResponseAPIGeonames>> geonames();
}
