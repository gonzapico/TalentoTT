package xyz.gonzapico.talentott.getTemperature;

import android.content.Context;
import android.util.Log;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import xyz.gonzapico.data.Config;
import xyz.gonzapico.data.exception.GeonameNotFoundException;
import xyz.gonzapico.exception.DefaultErrorBundle;
import xyz.gonzapico.interactor.BaseUseCase;
import xyz.gonzapico.interactor.DefaultObserver;
import xyz.gonzapico.interactor.GetTemperature;
import xyz.gonzapico.model.WeatherObservationModelDomain;
import xyz.gonzapico.talentott.exception.ErrorMessageFactory;
import xyz.gonzapico.talentott.getTemperature.mapper.DomainWeatherMapper;

/**
 * Created by gfernandez on 3/04/17.
 */

public class GetTemperaturePresenter {

  private final static String TAG = "GetTemperaturePresenter";

  private BaseUseCase getTemperatureUseCase;
  private DomainWeatherMapper mapper;

  private GetTemperatureView getTemperatureView;
  private Context mContext;
  private boolean isFirstAttempt = true;

  @Inject public GetTemperaturePresenter(@Named("temperature") BaseUseCase useCaseGetGeonames,
      DomainWeatherMapper mapper) {
    this.getTemperatureUseCase = useCaseGetGeonames;
    this.mapper = mapper;
  }

  public void onAttachView(GetTemperatureView getGeoNamesView, Context context) {
    this.getTemperatureView = getGeoNamesView;
    this.mContext = context;
  }

  public void onViewDetached() {
    this.getTemperatureView = null;
    this.getTemperatureUseCase.dispose();
  }

  public void getTemperature(City city) {
    String username = isFirstAttempt ? Config.FIRST_USER : Config.SECOND_USER;
    this.getTemperatureUseCase.execute(new GetTemperatureSuscriber(city),
        GetTemperature.Params.forCoordenatesUsername(mapper.transformToBbox(city.getCoordenates()),
            username));
  }

  private final class GetTemperatureSuscriber
      extends DefaultObserver<List<WeatherObservationModelDomain>> {

    private City mCity = null;

    public GetTemperatureSuscriber(City city) {
      mCity = city;
    }

    public GetTemperatureSuscriber() {

    }

    @Override public void onComplete() {
      super.onComplete();
      Log.d(TAG, "onComplete");
    }

    @Override public void onError(Throwable e) {
      DefaultErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
      Log.e(TAG, errorBundle.getErrorMessage() + " " + errorBundle.getException().toString());
      if (isFirstAttempt) {
        isFirstAttempt = !isFirstAttempt;
        getTemperature(mCity);
      }
    }

    @Override
    public void onNext(List<WeatherObservationModelDomain> weatherObservationModelDomainList) {
      Log.d(TAG, weatherObservationModelDomainList.size() + "");
      if (weatherObservationModelDomainList.isEmpty()) {
        DefaultErrorBundle errorBundle = new DefaultErrorBundle(new GeonameNotFoundException());
        showErrorMessage(errorBundle);
      } else {
        getTemperatureView.showObservation(
            weatherObservationModelDomainList.get(0).getObservation());
        getTemperatureView.showTemperature(
            weatherObservationModelDomainList.get(0).getTemperature());
        getTemperatureView.showCityName(mCity.getName());
        getTemperatureView.showMap(mCity);
      }
    }

    private void showErrorMessage(DefaultErrorBundle errorBundle) {
      String errorMessage = ErrorMessageFactory.create(mContext, errorBundle.getException());
      Log.e(TAG, errorMessage);
      getTemperatureView.showErrorMessage(errorMessage);
    }
  }
}
