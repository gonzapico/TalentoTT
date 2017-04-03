package xyz.gonzapico.talentott.getGeoNames;

import android.util.Log;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import xyz.gonzapico.exception.DefaultErrorBundle;
import xyz.gonzapico.interactor.BaseUseCase;
import xyz.gonzapico.interactor.DefaultObserver;
import xyz.gonzapico.interactor.GetGeonames.Params;
import xyz.gonzapico.model.GeonameModelDomain;
import xyz.gonzapico.talentott.exception.ErrorMessageFactory;

/**
 * Created by gfernandez on 3/04/17.
 */

public class GetGeoNamesPresenter {

  private final static String TAG = "GetGeoNamesPresenter";

  private BaseUseCase getGeoNamesUseCase;

  private GetGeoNamesView mGetGeoNamesView;

  @Inject public GetGeoNamesPresenter(@Named("geonames") BaseUseCase useCaseGetGeonames) {
    this.getGeoNamesUseCase = useCaseGetGeonames;
  }

  public void onAttachView(GetGeoNamesView getGeoNamesView) {
    this.mGetGeoNamesView = getGeoNamesView;
  }

  public void onViewDetached() {
    this.mGetGeoNamesView = null;
    this.getGeoNamesUseCase.dispose();
  }

  public void getGeonames(String city) {
    this.getGeoNamesUseCase.execute(new GetGeonamesSuscriber(), Params.forCity(city));
  }

  private final class GetGeonamesSuscriber extends DefaultObserver<List<GeonameModelDomain>> {
    public GetGeonamesSuscriber() {

    }

    @Override public void onComplete() {
      super.onComplete();
      Log.d(TAG, "onComplete");
    }

    @Override public void onError(Throwable e) {
      DefaultErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
      showErrorMessage(errorBundle);
      Log.e(TAG, errorBundle.getErrorMessage() + " " + errorBundle.getException().toString());
    }

    @Override public void onNext(List<GeonameModelDomain> userDomainEntityList) {
      Log.d(TAG, userDomainEntityList.size() + "");
    }

    private void showErrorMessage(DefaultErrorBundle errorBundle) {
      String errorMessage = ErrorMessageFactory.create(null, errorBundle.getException());
      Log.e(TAG, errorMessage);
    }
  }
}
