package xyz.gonzapico.repository;

import io.reactivex.Observable;
import java.util.List;
import xyz.gonzapico.model.BboxModelDomain;
import xyz.gonzapico.model.GeonameModelDomain;
import xyz.gonzapico.model.WeatherObservationModelDomain;

/**
 * Created by gfernandez on 25/02/17.
 */

public interface GeonameDomainRepository {

  Observable<List<GeonameModelDomain>> getGeonames(String city, String user);

  Observable<List<WeatherObservationModelDomain>> getWeatherObservations(
      BboxModelDomain coordenates, String user);
}
