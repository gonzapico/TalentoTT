package xyz.gonzapico.talentott.getTemperature;

import java.util.List;

/**
 * Created by gfernandez on 3/04/17.
 */

public interface GetTemperatureView {
  void showTemperature(String temperature);

  void showObservation(String observation);

  void showErrorMessage(String errorMsg);

  void showCityName(String cityName);

  void showMap(City city);

  void showWeatherObservations(List<Station> stationList);
}
