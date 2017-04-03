package xyz.gonzapico.data.cloud;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.gonzapico.data.entity.ResponseAPIGeonames;
import xyz.gonzapico.data.entity.WeatherObservations;

/**
 * Created by gfernandez on 25/02/17.
 */

public interface GeoNamesAPIService {

  // http://api.geonames.org/searchJSON?q=Madrid&maxRows=20&startRow=0&lang=en&isNameRequired=true&style=FULL&username=ilgeonamessample
  @GET("/searchJSON") Observable<Response<ResponseAPIGeonames>> geonames(
      @Query("q") String city, @Query("maxRows") int maxRows, @Query("startRow") int startRow,
      @Query("lang") String language, @Query("isNameRequired") boolean isNameRequired,
      @Query("style") String style, @Query("username") String username);

  // http://api.geonames.org/weatherJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&username=demo
  @GET("/weatherJSON") Observable<Response<WeatherObservations>> weather(
      @Query("north") Double north, @Query("south") Double south, @Query("east") Double east,
      @Query("west") Double west, @Query("username") String username);

}
