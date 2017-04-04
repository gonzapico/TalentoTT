package xyz.gonzapico.talentott.getTemperature;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gfernandez on 4/04/17.
 */

public class Station {

  private LatLng latLng;
  private String name = "";
  private String currentTemperature = "";

  public LatLng getLatLng() {
    return latLng;
  }

  public void setLatLng(LatLng latLng) {
    this.latLng = latLng;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCurrentTemperature() {
    return currentTemperature;
  }

  public void setCurrentTemperature(String currentTemperature) {
    this.currentTemperature = currentTemperature;
  }
}
