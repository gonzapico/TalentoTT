package xyz.gonzapico.talentott;

import android.app.Application;
import xyz.gonzapico.talentott.di.components.ApplicationComponent;
import xyz.gonzapico.talentott.di.components.DaggerApplicationComponent;
import xyz.gonzapico.talentott.di.modules.ApplicationModule;

/**
 * Created by gfernandez on 3/04/17.
 */

public class BaseTMApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    this.initializeInjector();
  }

  private void initializeInjector() {
    this.applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }
}
