package xyz.gonzapico.talentott.getGeoNames;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import xyz.gonzapico.data.Config;
import xyz.gonzapico.data.exception.GeonameNotFoundException;
import xyz.gonzapico.exception.DefaultErrorBundle;
import xyz.gonzapico.interactor.BaseUseCase;
import xyz.gonzapico.interactor.DefaultObserver;
import xyz.gonzapico.interactor.GetGeonames.Params;
import xyz.gonzapico.model.BboxModelDomain;
import xyz.gonzapico.model.GeonameModelDomain;
import xyz.gonzapico.talentott.exception.CityEmptyException;
import xyz.gonzapico.talentott.exception.ErrorMessageFactory;

/**
 * Created by gfernandez on 3/04/17.
 */

public class GetGeoNamesPresenter {

  private final static String TAG = "GetGeoNamesPresenter";

  private BaseUseCase getGeoNamesUseCase;

  private GetGeoNamesView mGetGeoNamesView;
  private Context mContext;

  @Inject public GetGeoNamesPresenter(@Named("geonames") BaseUseCase useCaseGetGeonames) {
    this.getGeoNamesUseCase = useCaseGetGeonames;
  }

  public void onAttachView(GetGeoNamesView getGeoNamesView, Context context) {
    this.mGetGeoNamesView = getGeoNamesView;
    this.mContext = context;
  }

  public void onViewDetached() {
    this.mGetGeoNamesView = null;
    this.getGeoNamesUseCase.dispose();
  }

  public void getGeonames(String city) {
    if (TextUtils.isEmpty(city)) {
      String errorMessage = ErrorMessageFactory.create(mContext,
          new DefaultErrorBundle(new CityEmptyException()).getException());
      mGetGeoNamesView.showErrorMessage(errorMessage);
      return;
    }
    this.getGeoNamesUseCase.execute(new GetGeonamesSuscriber(city),
        Params.forCityUser(city, Config.FIRST_USER));
  }

  public void retryGetGeonames(String city) {
    this.getGeoNamesUseCase.execute(new GetGeonamesSuscriber(city),
        Params.forCityUser(city, Config.SECOND_USER));
  }

  private final class GetGeonamesSuscriber extends DefaultObserver<List<GeonameModelDomain>> {

    private String mCity = "";
    private boolean isFirstAttempt = true;
    private BboxModelDomain coordenatesToSearch = null;

    public GetGeonamesSuscriber(String city) {
      mCity = city;
    }

    public GetGeonamesSuscriber() {

    }

    @Override public void onComplete() {
      super.onComplete();
      Log.d(TAG, "onComplete");
      if (coordenatesToSearch != null) {
        mGetGeoNamesView.showTemperature(coordenatesToSearch.getNorth(),
            coordenatesToSearch.getSouth(), coordenatesToSearch.getEast(),
            coordenatesToSearch.getWest());
      }
    }

    @Override public void onError(Throwable e) {
      DefaultErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
      Log.e(TAG, errorBundle.getErrorMessage() + " " + errorBundle.getException().toString());
      if (isFirstAttempt && errorBundle.getException() instanceof GeonameNotFoundException) {
        isFirstAttempt = false;
        retryGetGeonames(mCity);
      } else {
        showErrorMessage(errorBundle);
      }
    }

    @Override public void onNext(List<GeonameModelDomain> userDomainEntityList) {
      Log.d(TAG, userDomainEntityList.size() + "");
      if (userDomainEntityList.isEmpty()) {
        DefaultErrorBundle errorBundle = new DefaultErrorBundle(new GeonameNotFoundException());
        showErrorMessage(errorBundle);
      } else {
        coordenatesToSearch = userDomainEntityList.get(0).getBbox();
      }
    }

    private void showErrorMessage(DefaultErrorBundle errorBundle) {
      String errorMessage = ErrorMessageFactory.create(mContext, errorBundle.getException());
      mGetGeoNamesView.showErrorMessage(errorMessage);
      Log.e(TAG, errorMessage);
    }
  }
}
