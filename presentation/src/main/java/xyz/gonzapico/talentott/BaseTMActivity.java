package xyz.gonzapico.talentott;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.gonzapico.talentott.di.HasComponent;
import xyz.gonzapico.talentott.di.components.ApplicationComponent;
import xyz.gonzapico.talentott.di.components.DaggerGeonameComponent;
import xyz.gonzapico.talentott.di.components.GeonameComponent;
import xyz.gonzapico.talentott.di.modules.ActivityModule;

/**
 * Created by gfernandez on 2/04/17.
 */

public abstract class BaseTMActivity extends AppCompatActivity
    implements HasComponent<GeonameComponent> {

  @BindView(R.id.toolbar) Toolbar toolbar;
  private GeonameComponent mGeoNameComponent;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResource());
    ButterKnife.bind(this);

    if (toolbar != null) {
      setSupportActionBar(toolbar);
    }
    this.initializeInjector();
  }

  protected abstract int getLayoutResource();

  /***
   * Method to init the DI and has all the classes availables
   */
  private void initializeInjector() {
    this.mGeoNameComponent = DaggerGeonameComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }

  /***
   * Get out a {@link Fragment} from the back stack.
   */
  protected void popFragment() {
    if (getFragmentManager().getBackStackEntryCount() > 1) this.getFragmentManager().popBackStack();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((BaseTMApplication) getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  @Override public GeonameComponent getComponent() {
    return mGeoNameComponent;
  }
}
