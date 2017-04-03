package xyz.gonzapico.data.entity.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Response;
import xyz.gonzapico.data.entity.Bbox;
import xyz.gonzapico.data.entity.Geoname;
import xyz.gonzapico.data.entity.ResponseAPIGeonames;
import xyz.gonzapico.model.BboxModelDomain;
import xyz.gonzapico.model.GeonameModelDomain;

/**
 * Created by gfernandez on 25/02/17.
 */

@Singleton public class GeoMapper {
  @Inject public GeoMapper() {

  }

  public List<GeonameModelDomain> transformToListOfGeonames(
      Response<ResponseAPIGeonames> listResponse) {
    List<GeonameModelDomain> listOfGeonames = null;
    if (listResponse.isSuccessful()) {
      listOfGeonames = new ArrayList<>();
      listOfGeonames.addAll(transformToGeonameModelDomainList(listResponse.body().getGeonames()));
    }
    return listOfGeonames;
  }

  private Collection<GeonameModelDomain> transformToGeonameModelDomainList(List<Geoname> geonames) {
    List<GeonameModelDomain> result = new ArrayList<>();

    for (Geoname geoname : geonames) {
      GeonameModelDomain geonameModelDomain = new GeonameModelDomain();
      geonameModelDomain.setName(geoname.getName());
      try {
        geonameModelDomain.setBbox(transformBbox(geoname.getBbox()));
      } catch (Exception e) {
      }
      result.add(geonameModelDomain);
    }
    return result;
  }

  private BboxModelDomain transformBbox(Bbox bbox) {
    BboxModelDomain bboxResult = new BboxModelDomain();

    bboxResult.setEast(bbox.getEast());
    bboxResult.setNorth(bbox.getNorth());
    bboxResult.setSouth(bbox.getSouth());
    bboxResult.setWest(bbox.getWest());

    return bboxResult;
  }
}