package xyz.gonzapico.data.repository;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
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

  @Override public Observable<List<GeonameModelDomain>> getGeonames() {
    final GeonameDataStore userDataStore = this.geonameStoreFactory.createCloudDataStore();
    return userDataStore.geonames().map(this.geoMapper::transformToListOfGeonames);
  }
}
