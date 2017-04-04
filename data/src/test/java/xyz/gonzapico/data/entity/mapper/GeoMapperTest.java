package xyz.gonzapico.data.entity.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;
import xyz.gonzapico.data.entity.Geoname;
import xyz.gonzapico.data.entity.ResponseAPIGeonames;
import xyz.gonzapico.model.GeonameModelDomain;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by gfernandez on 3/04/17.
 */
public class GeoMapperTest {
  private static final int FAKE_GENRE_ID = 111;
  private static final String FAKE_NAME = "Adventure";

  private GeoMapper geoMapper;

  @Before public void setUp() throws Exception {
    geoMapper = new GeoMapper();
  }

  @Test public void transformToListOfGeonames() throws Exception {
    ResponseAPIGeonames mockResponse = mock(ResponseAPIGeonames.class);

    Geoname geonameOne = mock(Geoname.class);
    Geoname geonameTwo = mock(Geoname.class);
    List<Geoname> geonameList = new ArrayList<Geoname>(2);
    geonameList.add(geonameOne);
    geonameList.add(geonameTwo);
    mockResponse.setGeonames(geonameList);

    Collection<GeonameModelDomain> geonameCollection =
        geoMapper.transformToListOfGeonames(Response.success(mockResponse));
    assertThat(geonameCollection.toArray()[0], is(instanceOf(GeonameModelDomain.class)));
    assertThat(geonameCollection.toArray()[1], is(instanceOf(GeonameModelDomain.class)));
    assertThat(geonameCollection.size(), is(2));
  }
}