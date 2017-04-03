package xyz.gonzapico.data.repository;

import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import xyz.gonzapico.data.entity.mapper.GeoMapper;
import xyz.gonzapico.data.repository.datasource.GeonameDataStore;
import xyz.gonzapico.data.repository.datasource.GeonameStoreFactory;
import xyz.gonzapico.model.GeonameModelDomain;
import xyz.gonzapico.repository.GeonameDomainRepository;

/**
 * Created by gfernandez on 25/02/17.
 */

@Singleton public class GeonameRepository implements GeonameDomainRepository {

  private final GeonameStoreFactory geonameStoreFactory;
  private final GeoMapper geoMapper;

  @Inject public GeonameRepository(GeonameStoreFactory geonameStoreFactory, GeoMapper geoMapper) {
    this.geonameStoreFactory = geonameStoreFactory;
    this.geoMapper = geoMapper;
  }

  @Override public Observable<List<GeonameModelDomain>> getGeonames(String city) {
    final GeonameDataStore geonameDataStore = this.geonameStoreFactory.createCloudDataStore();
    return geonameDataStore.geonames(city).map(this.geoMapper::transformToListOfGeonames);
  }
}
