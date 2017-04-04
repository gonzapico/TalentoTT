package xyz.gonzapico.talentott;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;
import javax.inject.Inject;
import xyz.gonzapico.talentott.di.components.GeonameComponent;
import xyz.gonzapico.talentott.getGeoNames.GetGeoNamesPresenter;
import xyz.gonzapico.talentott.getGeoNames.GetGeoNamesView;
import xyz.gonzapico.talentott.getSavedSearchs.GetSavedSearchsPresenter;
import xyz.gonzapico.talentott.getSavedSearchs.GetSavedSearchsView;
import xyz.gonzapico.talentott.getTemperature.City;
import xyz.gonzapico.talentott.getTemperature.GetTemperaturePresenter;
import xyz.gonzapico.talentott.getTemperature.GetTemperatureView;

/**
 * A placeholder fragment containing a simple view.
 */
public class GetGeonamesFragment extends BaseTMFragment
    implements GetGeoNamesView, GetTemperatureView, GetSavedSearchsView {
  @Inject GetSavedSearchsPresenter savedSearchsPresenter;
  @Inject GetGeoNamesPresenter geonamesPresenter;
  @Inject GetTemperaturePresenter temperaturePresenter;
  @BindView(R.id.etLocation) EditText etLocation;
  @BindView(R.id.pbTemperature) ProgressBar pbTemperature;
  @BindView(R.id.tvCityName) TextView tvCityName;
  @BindView(R.id.tvTemperature) TextView tvTemperature;
  @BindView(R.id.tvObservations) TextView tvObservations;
  @BindView(R.id.infoZone) LinearLayout llInfoZone;
  private com.google.android.gms.maps.MapFragment mapView;
  private GoogleMap mGoogleMap;
  private View mView;

  public GetGeonamesFragment() {
    setRetainInstance(true);
  }

  public static GetGeonamesFragment newInstance() {
    GetGeonamesFragment fragment = new GetGeonamesFragment();
    return fragment;
  }

  @Override public void showSuggestions(List<String> suggestionList) {
    for (String city : suggestionList) {
      Log.d("city", city);
    }
  }

  @OnClick(R.id.btnSearchLocation) void searchLocation() {
    geonamesPresenter.getGeonames(etLocation.getText().toString());
    llInfoZone.setVisibility(View.GONE);
    hideKeyboard();
  }

  @OnFocusChange(R.id.etLocation) void getCache() {
    savedSearchsPresenter.getSavedSearchs();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mView = inflater.inflate(R.layout.fragment_home, container, false);
    return mView;
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
    llInfoZone.setVisibility(View.GONE);
  }

  @Override public void showTemperature(City city) {
    temperaturePresenter.getTemperature(city);
    llInfoZone.setVisibility(View.VISIBLE);
  }

  @Override public void showTemperature(String temperature) {
    pbTemperature.setProgress(Integer.parseInt(temperature));
    tvTemperature.setText(temperature);
  }

  @Override public void showObservation(String observation) {
    tvObservations.setText(observation);
  }

  @Override public void showCityName(String cityName) {
    tvCityName.setText(cityName.toUpperCase());
  }

  private void hideKeyboard() {
    // Check if no view has focus:
    View view = etLocation.getRootView();
    if (view != null) {
      InputMethodManager imm =
          (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  @Override public void showMap(final City city) {
    mapView = MapFragment.newInstance();
    ((BaseTMActivity) getActivity()).addFragment(R.id.mapView, mapView);
    mapView.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(GoogleMap googleMap) {
        LatLng latLng =
            new LatLng(Double.parseDouble(city.getLat()), Double.parseDouble(city.getLng()));
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        mGoogleMap.addMarker(new MarkerOptions().icon(
            BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round))
            .anchor(0.0f, 1.0f)
            .position(latLng));
        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
        mGoogleMap.moveCamera(cameraUpdate);
      }
    });
  }
}
