package xyz.gonzapico.talentott.di.modules;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import xyz.gonzapico.interactor.BaseUseCase;
import xyz.gonzapico.interactor.GetCitySearched;
import xyz.gonzapico.interactor.GetGeonames;
import xyz.gonzapico.interactor.GetTemperature;
import xyz.gonzapico.talentott.di.PerActivity;

/**
 * /**
 * Dagger module that provides user related collaborators.
 */
@Module public class GeonameModule {

  public GeonameModule() {

  }

  @Provides @PerActivity @Named("geonames") BaseUseCase provideGetGeonamesUseCase(
      GetGeonames getGeonames) {
    return getGeonames;
  }

  @Provides @PerActivity @Named("temperature") BaseUseCase provideGetTemperatureUseCase(
      GetTemperature getTemperature) {
    return getTemperature;
  }

  @Provides @PerActivity @Named("searchsSaved") BaseUseCase provideGetCitySearchedUseCase(
      GetCitySearched getCitySearched) {
    return getCitySearched;
  }
}
