package xyz.gonzapico.talentott;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import javax.inject.Inject;
import xyz.gonzapico.talentott.di.components.GeonameComponent;
import xyz.gonzapico.talentott.getGeoNames.GetGeoNamesPresenter;
import xyz.gonzapico.talentott.getGeoNames.GetGeoNamesView;

/**
 * A placeholder fragment containing a simple view.
 */
public class GetGeonamesFragment extends BaseTMFragment implements GetGeoNamesView {

  @Inject GetGeoNamesPresenter geonamesPresenter;
  @BindView(R.id.etLocation) EditText etLocation;

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

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.getComponent(GeonameComponent.class).inject(this);
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override public void onDestroy() {
    super.onDestroy();

    geonamesPresenter.onViewDetached();
  }
}
