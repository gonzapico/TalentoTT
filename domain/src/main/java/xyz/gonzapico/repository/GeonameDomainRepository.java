package xyz.gonzapico.repository;

import java.util.List;
import rx.Observable;
import xyz.gonzapico.model.GeonameModelDomain;

/**
 * Created by gfernandez on 25/02/17.
 */

public interface GeonameDomainRepository {

  Observable<List<GeonameModelDomain>> getGeonames();
}
