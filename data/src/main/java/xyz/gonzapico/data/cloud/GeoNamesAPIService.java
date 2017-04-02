package xyz.gonzapico.data.cloud;

import retrofit2.Response;
import retrofit2.http.GET;
import xyz.gonzapico.data.entity.ResponseAPIGeonames;

/**
 * Created by gfernandez on 25/02/17.
 */

public interface GeoNamesAPIService {

  // http://api.geonames.org/searchJSON?q=Madrid&maxRows=20&startRow=0&lang=en&isNameRequired=true&style=FULL&username=ilgeonamessample
  @GET("/api/v1/Conversations/searchJSON") rx.Observable<Response<ResponseAPIGeonames>> geonames();
}
