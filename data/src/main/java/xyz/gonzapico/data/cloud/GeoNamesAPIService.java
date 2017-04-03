package xyz.gonzapico.data.cloud;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.gonzapico.data.entity.ResponseAPIGeonames;

/**
 * Created by gfernandez on 25/02/17.
 */

public interface GeoNamesAPIService {

  // http://api.geonames.org/searchJSON?q=Madrid&maxRows=20&startRow=0&lang=en&isNameRequired=true&style=FULL&username=ilgeonamessample
  @GET("/searchJSON") Observable<Response<ResponseAPIGeonames>> geonames(
      @Query("q") String city, @Query("maxRows") int maxRows, @Query("startRow") int startRow,
      @Query("lang") String language, @Query("isNameRequired") boolean isNameRequired,
      @Query("style") String style, @Query("username") String username);
}
