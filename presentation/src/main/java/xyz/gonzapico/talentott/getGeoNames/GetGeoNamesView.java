package xyz.gonzapico.talentott.getGeoNames;

/**
 * Created by gfernandez on 3/04/17.
 */

public interface GetGeoNamesView {

  void showErrorMessage(String errorMessage);

  void showTemperature(Double east, Double north, Double west, Double south);
}
