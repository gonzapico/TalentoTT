package xyz.gonzapico.interactor;

import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import xyz.gonzapico.executor.PostExecutionThread;
import xyz.gonzapico.executor.ThreadExecutor;
import xyz.gonzapico.model.GeonameModelDomain;
import xyz.gonzapico.repository.GeonameDomainRepository;

/**
 * Created by gfernandez on 25/02/17.
 */

public class GetGeonames extends BaseUseCase<List<GeonameModelDomain>, GetGeonames.Params> {

  private final GeonameDomainRepository mRepository;

  @Inject
  public GetGeonames(GeonameDomainRepository usersDomainRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = usersDomainRepository;
  }

  @Override Observable<List<GeonameModelDomain>> buildUseCaseObservable(Params params) {
    return mRepository.getGeonames(params.city, params.user);
  }

  public static final class Params {

    private final String city;
    private final String user;

    private Params(String city, String user) {
      this.city = city;
      this.user = user;
    }

    public static Params forCityUser(String cityName, String user) {
      return new Params(cityName, user);
    }
  }
}
