package xyz.gonzapico.data.repository;

import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Response;
import xyz.gonzapico.data.Config;
import xyz.gonzapico.data.entity.Geoname;
import xyz.gonzapico.data.entity.ResponseAPIGeonames;
import xyz.gonzapico.data.entity.mapper.GeoMapper;
import xyz.gonzapico.data.repository.datasource.GeonameDataStore;
import xyz.gonzapico.data.repository.datasource.GeonameStoreFactory;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by gfernandez on 3/04/17.
 */
public class GeonameRepositoryTest {

  @Rule public ExpectedException expectedException = ExpectedException.none();
  private GeonameRepository geonameRepository;
  @Mock private GeonameStoreFactory mockGeonameDataStoreFactory;
  @Mock private GeoMapper mockGeonameDataMapper;
  @Mock private GeonameDataStore mockGeonameDataStore;
  @Mock private Geoname mockGeonameEntity;

  private String FAKE_CITY = "Madrid";

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
    geonameRepository = new GeonameRepository(mockGeonameDataStoreFactory, mockGeonameDataMapper);

    given(mockGeonameDataStoreFactory.createCloudDataStore()).willReturn(mockGeonameDataStore);
  }

  @Test public void getGeonames() throws Exception {
    Response<ResponseAPIGeonames> entityAPIResponse = Response.success(new ResponseAPIGeonames());
    given(mockGeonameDataStore.geonames(FAKE_CITY, Config.FIRST_USER)).willReturn(
        Observable.just(entityAPIResponse));
    geonameRepository.getGeonames(FAKE_CITY, Config.FIRST_USER);

    verify(mockGeonameDataStoreFactory).createCloudDataStore();
    verify(mockGeonameDataStore).geonames(FAKE_CITY, Config.FIRST_USER);
  }
}