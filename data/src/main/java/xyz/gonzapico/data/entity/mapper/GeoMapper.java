package xyz.gonzapico.data.entity.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Response;
import xyz.gonzapico.data.entity.Bbox;
import xyz.gonzapico.data.entity.Geoname;
import xyz.gonzapico.data.entity.ResponseAPIGeonames;
import xyz.gonzapico.data.entity.WeatherObservation;
import xyz.gonzapico.data.entity.WeatherObservations;
import xyz.gonzapico.model.BboxModelDomain;
import xyz.gonzapico.model.GeonameModelDomain;
import xyz.gonzapico.model.WeatherObservationModelDomain;

/**
 * Created by gfernandez on 25/02/17.
 */

@Singleton public class GeoMapper {
  @Inject public GeoMapper() {

  }

  public List<GeonameModelDomain> transformToListOfGeonames(
      Response<ResponseAPIGeonames> listResponse) {
    List<GeonameModelDomain> listOfGeonames = new ArrayList<>();
    if (listResponse.isSuccessful()) {
      listOfGeonames.addAll(transformToGeonameModelDomainList(listResponse.body().getGeonames()));
    }
    return listOfGeonames;
  }

  private Collection<GeonameModelDomain> transformToGeonameModelDomainList(List<Geoname> geonames) {
    List<GeonameModelDomain> result = new ArrayList<>();

    for (Geoname geoname : geonames) {
      GeonameModelDomain geonameModelDomain = new GeonameModelDomain();
      geonameModelDomain.setName(geoname.getName());
      try {
        geonameModelDomain.setBbox(transformBbox(geoname.getBbox()));
      } catch (Exception e) {
      }
      result.add(geonameModelDomain);
    }
    return result;
  }

  private BboxModelDomain transformBbox(Bbox bbox) {
    BboxModelDomain bboxResult = new BboxModelDomain();

    bboxResult.setEast(bbox.getEast());
    bboxResult.setNorth(bbox.getNorth());
    bboxResult.setSouth(bbox.getSouth());
    bboxResult.setWest(bbox.getWest());

    return bboxResult;
  }

  public Bbox transformToCoordenates(BboxModelDomain coordenates) {
    Bbox coordenatesResult = new Bbox();

    coordenatesResult.setEast(coordenates.getEast());
    coordenatesResult.setWest(coordenates.getWest());
    coordenatesResult.setSouth(coordenates.getSouth());
    coordenatesResult.setNorth(coordenates.getNorth());

    return coordenatesResult;
  }

  public List<WeatherObservationModelDomain> transformToListOfWeatherObservations(
      Response<WeatherObservations> weatherObservationsResponse) {
    List<WeatherObservationModelDomain> listOfWeatherObservations = new ArrayList<>();
    if (weatherObservationsResponse.isSuccessful()) {
      listOfWeatherObservations.addAll(transformWeatherObservationList(
          weatherObservationsResponse.body().getWeatherObservations()));
    }
    return listOfWeatherObservations;
  }

  private List<WeatherObservationModelDomain> transformWeatherObservationList(
      List<WeatherObservation> weatherObservations) {
    List<WeatherObservationModelDomain> resultList = new ArrayList<>();

    for (WeatherObservation weatherObservation : weatherObservations) {
      WeatherObservationModelDomain observationWeather = new WeatherObservationModelDomain();
      observationWeather.setTemperature(weatherObservation.getTemperature());
      observationWeather.setObservation(weatherObservation.getObservation());

      resultList.add(observationWeather);
    }

    return resultList;
  }
}
