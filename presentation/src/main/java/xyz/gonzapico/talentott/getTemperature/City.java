package xyz.gonzapico.talentott.getTemperature;

/**
 * Created by gfernandez on 4/04/17.
 */

public class City {

  private String name = "";
  private Coordenates coordenates;
  private String lng;
  private String lat;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Coordenates getCoordenates() {
    return coordenates;
  }

  public void setCoordenates(Coordenates coordenates) {
    this.coordenates = coordenates;
  }

  public String getLng() {
    return lng;
  }

  public void setLng(String lng) {
    this.lng = lng;
  }

  public String getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }
}
