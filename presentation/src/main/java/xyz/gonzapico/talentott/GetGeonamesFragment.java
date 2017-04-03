package xyz.gonzapico.talentott;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import java.util.List;
import javax.inject.Inject;
import xyz.gonzapico.talentott.di.components.GeonameComponent;
import xyz.gonzapico.talentott.getGeoNames.GetGeoNamesPresenter;
import xyz.gonzapico.talentott.getGeoNames.GetGeoNamesView;
import xyz.gonzapico.talentott.getSavedSearchs.GetSavedSearchsPresenter;
import xyz.gonzapico.talentott.getSavedSearchs.GetSavedSearchsView;
import xyz.gonzapico.talentott.getTemperature.Coordenates;
import xyz.gonzapico.talentott.getTemperature.GetTemperaturePresenter;
import xyz.gonzapico.talentott.getTemperature.GetTemperatureView;

/**
 * A placeholder fragment containing a simple view.
 */
public class GetGeonamesFragment extends BaseTMFragment
    implements GetGeoNamesView, GetTemperatureView, GetSavedSearchsView {
  @Override public void showSuggestions(List<String> suggestionList) {
    for (String city : suggestionList){
      Log.d("city", city);
    }
  }

  @Inject GetSavedSearchsPresenter savedSearchsPresenter;
  @Inject GetGeoNamesPresenter geonamesPresenter;
  @Inject GetTemperaturePresenter temperaturePresenter;
  @BindView(R.id.etLocation) EditText etLocation;
  @BindView(R.id.pbTemperature) ProgressBar pbTemperature;

  public GetGeonamesFragment() {
    setRetainInstance(true);
  }

  public static GetGeonamesFragment newInstance() {
    GetGeonamesFragment fragment = new GetGeonamesFragment();
    return fragment;
  }

  @OnClick(R.id.btnSearchLocation) void searchLocation() {
    geonamesPresenter.getGeonames(etLocation.getText().toString());
  }

  @OnFocusChange(R.id.etLocation) void getCache(){
    savedSearchsPresenter.getSavedSearchs();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.getComponent(GeonameComponent.class).inject(this);
  }

  @Override public void onResume() {
    super.onResume();
    geonamesPresenter.onAttachView(this, getActivity());
    temperaturePresenter.onAttachView(this, getActivity());
    savedSearchsPresenter.onAttachView(this, getActivity());
  }

  @Override public void onDestroy() {
    super.onDestroy();

    geonamesPresenter.onViewDetached();
  }

  @Override public void showErrorMessage(String errorMessage) {
    showToastMessage(errorMessage);
  }

  @Override public void showTemperature(Double east, Double north, Double west, Double south) {
    Coordenates coordenates = new Coordenates(north, south, east, west);
    temperaturePresenter.getTemperature(coordenates);
  }

  @Override public void showTemperature(String temperature) {
    pbTemperature.setProgress(Integer.parseInt(temperature));
  }

  @Override public void showObservation(String observation) {

  }
}
