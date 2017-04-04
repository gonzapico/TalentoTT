package xyz.gonzapico.data.di;

import dagger.Component;
import javax.inject.Singleton;
import xyz.gonzapico.data.repository.datasource.GeonameStore;

/**
 * Created by gfernandez on 25/02/17.
 */

@Singleton @Component(modules = CloudModule.class) public interface CloudComponent {
  void inject(GeonameStore cloudUserStore);
}
