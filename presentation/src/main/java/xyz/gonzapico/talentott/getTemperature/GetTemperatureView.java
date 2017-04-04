package xyz.gonzapico.talentott.getTemperature;

/**
 * Created by gfernandez on 3/04/17.
 */

public interface GetTemperatureView {
  void showTemperature(String temperature);

  void showObservation(String observation);

  void showErrorMessage(String errorMsg);

  void showCityName(String cityName);
}
