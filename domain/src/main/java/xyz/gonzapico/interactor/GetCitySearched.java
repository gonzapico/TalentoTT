package xyz.gonzapico.interactor;

import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import xyz.gonzapico.executor.PostExecutionThread;
import xyz.gonzapico.executor.ThreadExecutor;
import xyz.gonzapico.repository.GeonameDomainRepository;

/**
 * Created by gfernandez on 3/04/17.
 */

public class GetCitySearched extends BaseUseCase<List<String>, Void> {

  private final GeonameDomainRepository mRepository;

  @Inject public GetCitySearched(GeonameDomainRepository usersDomainRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.mRepository = usersDomainRepository;
  }

  @Override Observable<List<String>> buildUseCaseObservable(Void params) {
    return mRepository.getCitySearched();
  }
}
