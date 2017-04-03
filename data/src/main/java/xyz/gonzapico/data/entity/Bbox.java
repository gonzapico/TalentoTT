
package xyz.gonzapico.data.entity;


public class Bbox {

    private Double east;
    private Double south;
    private Double north;
    private Double west;
    private Integer accuracyLevel;

    public Double getEast() {
        return east;
    }

    public void setEast(Double east) {
        this.east = east;
    }

    public Double getSouth() {
        return south;
    }

    public void setSouth(Double south) {
        this.south = south;
    }

    public Double getNorth() {
        return north;
    }

    public void setNorth(Double north) {
        this.north = north;
    }

    public Double getWest() {
        return west;
    }

    public void setWest(Double west) {
        this.west = west;
    }

    public Integer getAccuracyLevel() {
        return accuracyLevel;
    }

    public void setAccuracyLevel(Integer accuracyLevel) {
        this.accuracyLevel = accuracyLevel;
    }

}
