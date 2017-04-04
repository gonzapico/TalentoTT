package xyz.gonzapico.talentott.getTemperature.mapper;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import xyz.gonzapico.model.BboxModelDomain;
import xyz.gonzapico.model.WeatherObservationModelDomain;
import xyz.gonzapico.talentott.di.PerActivity;
import xyz.gonzapico.talentott.getTemperature.Coordenates;
import xyz.gonzapico.talentott.getTemperature.Station;

/**
 * Created by gfernandez on 1/03/17.
 */

@PerActivity public class DomainWeatherMapper {

  @Inject public DomainWeatherMapper() {

  }

  public BboxModelDomain transformToBbox(Coordenates coordenates) {
    BboxModelDomain bboxModelDomainResult = new BboxModelDomain();

    bboxModelDomainResult.setNorth(coordenates.getNorth());
    bboxModelDomainResult.setEast(coordenates.getEast());
    bboxModelDomainResult.setWest(coordenates.getWest());
    bboxModelDomainResult.setSouth(coordenates.getSouth());

    return bboxModelDomainResult;
  }

  public List<Station> transformToStationList(
      List<WeatherObservationModelDomain> listOfObservatories) {
    List<Station> stationListResult = new ArrayList<>();

    for (WeatherObservationModelDomain observatory : listOfObservatories) {
      Station station = new Station();
      station.setName(observatory.getStationName());
      station.setCurrentTemperature(observatory.getTemperature());
      try {
        station.setLatLng(new LatLng(observatory.getLat(), observatory.getLng()));
      } catch (Exception e) {

      }

      stationListResult.add(station);
    }

    return stationListResult;
  }
}
