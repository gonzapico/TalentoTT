package xyz.gonzapico.talentott.getTemperature;

/**
 * Created by gfernandez on 3/04/17.
 */

public class Coordenates {

  private Double east;
  private Double west;
  private Double north;
  private Double south;

  public Coordenates(Double north, Double south, Double east, Double west){
    this.north = north;
    this.south = south;
    this.west = west;
    this.east = east;
  }

  public Double getEast() {
    return east;
  }

  public void setEast(Double east) {
    this.east = east;
  }

  public Double getWest() {
    return west;
  }

  public void setWest(Double west) {
    this.west = west;
  }

  public Double getNorth() {
    return north;
  }

  public void setNorth(Double north) {
    this.north = north;
  }

  public Double getSouth() {
    return south;
  }

  public void setSouth(Double south) {
    this.south = south;
  }
}
