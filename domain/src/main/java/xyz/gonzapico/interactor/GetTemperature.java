package xyz.gonzapico.interactor;

import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import xyz.gonzapico.executor.PostExecutionThread;
import xyz.gonzapico.executor.ThreadExecutor;
import xyz.gonzapico.model.BboxModelDomain;
import xyz.gonzapico.model.WeatherObservationModelDomain;
import xyz.gonzapico.repository.GeonameDomainRepository;

/**
 * Created by gfernandez on 25/02/17.
 */

public class GetTemperature
    extends BaseUseCase<List<WeatherObservationModelDomain>, GetTemperature.Params> {

  private final GeonameDomainRepository mRepository;

  @Inject public GetTemperature(GeonameDomainRepository usersDomainRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = usersDomainRepository;
  }

  @Override Observable<List<WeatherObservationModelDomain>> buildUseCaseObservable(Params params) {
    return mRepository.getWeatherObservations(params.coordenates, params.username);
  }

  public static final class Params {

    private final BboxModelDomain coordenates;
    private final String username;

    private Params(BboxModelDomain coordenates, String username) {

      this.username = username;
      this.coordenates = coordenates;
    }

    public static Params forCoordenatesUsername(BboxModelDomain coordenates, String username) {
      return new Params(coordenates, username);
    }
  }
}
