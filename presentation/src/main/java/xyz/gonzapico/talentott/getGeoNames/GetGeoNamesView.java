package xyz.gonzapico.talentott.getGeoNames;

import xyz.gonzapico.talentott.getTemperature.City;

/**
 * Created by gfernandez on 3/04/17.
 */

public interface GetGeoNamesView {

  void showErrorMessage(String errorMessage);

  void showTemperature(City city);
}
