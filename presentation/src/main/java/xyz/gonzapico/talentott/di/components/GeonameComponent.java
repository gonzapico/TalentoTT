package xyz.gonzapico.talentott.di.components;

import dagger.Component;
import xyz.gonzapico.talentott.GetGeonamesFragment;
import xyz.gonzapico.talentott.di.PerActivity;
import xyz.gonzapico.talentott.di.modules.ActivityModule;
import xyz.gonzapico.talentott.di.modules.GeonameModule;

/**
 * A scope {@link PerActivity}
 * component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, GeonameModule.class
}) public interface GeonameComponent extends ActivityComponent {
  void inject(GetGeonamesFragment getGeonamesFragment);
}