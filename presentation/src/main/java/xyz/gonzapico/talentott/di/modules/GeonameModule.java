package xyz.gonzapico.talentott.di.modules;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import xyz.gonzapico.interactor.BaseUseCase;
import xyz.gonzapico.interactor.GetGeonames;
import xyz.gonzapico.talentott.di.PerActivity;

/**
 * /**
 * Dagger module that provides user related collaborators.
 */
@Module public class GeonameModule {

  public GeonameModule() {

  }

  @Provides @PerActivity @Named("geonames") BaseUseCase provideGetUsersUseCase(
      GetGeonames getGeonames) {
    return getGeonames;
  }
}
