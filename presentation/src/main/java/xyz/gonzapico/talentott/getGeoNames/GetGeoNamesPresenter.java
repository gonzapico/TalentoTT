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
import xyz.gonzapico.talentott.getTemperature.City;
import xyz.gonzapico.talentott.getTemperature.Coordenates;

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
    private City cityToSearch = null;

    public GetGeonamesSuscriber(String city) {
      mCity = city;
    }

    public GetGeonamesSuscriber() {

    }

    @Override public void onComplete() {
      super.onComplete();
      Log.d(TAG, "onComplete");
      if (cityToSearch != null) {
        mGetGeoNamesView.showTemperature(cityToSearch);
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
        boolean foundCity = false;
        int indexOfArray = 0;
        while (!foundCity) {
          if (userDomainEntityList.get(indexOfArray)
              .getName()
              .equalsIgnoreCase(mCity.toLowerCase())) {
            foundCity = true;
            cityToSearch = new City();
            cityToSearch.setName(mCity);
            BboxModelDomain bboxModelDomain = userDomainEntityList.get(indexOfArray).getBbox();
            cityToSearch.setCoordenates(
                new Coordenates(bboxModelDomain.getNorth(), bboxModelDomain.getSouth(),
                    bboxModelDomain.getEast(), bboxModelDomain.getWest()));
            cityToSearch.setLng(userDomainEntityList.get(indexOfArray).getLng());
            cityToSearch.setLat(userDomainEntityList.get(indexOfArray).getLat());
          }
          indexOfArray++;
        }
      }
    }

    private void showErrorMessage(DefaultErrorBundle errorBundle) {
      String errorMessage = ErrorMessageFactory.create(mContext, errorBundle.getException());
      mGetGeoNamesView.showErrorMessage(errorMessage);
      Log.e(TAG, errorMessage);
    }
  }
}
