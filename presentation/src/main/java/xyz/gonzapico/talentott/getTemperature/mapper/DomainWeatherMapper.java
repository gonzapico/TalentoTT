package xyz.gonzapico.talentott.getTemperature.mapper;

import javax.inject.Inject;
import xyz.gonzapico.model.BboxModelDomain;
import xyz.gonzapico.talentott.di.PerActivity;
import xyz.gonzapico.talentott.getTemperature.Coordenates;

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
}
