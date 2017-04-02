package xyz.gonzapico.data.repository.datasource;

import android.content.Context;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by gfernandez on 25/02/17.
 */

@Singleton public class GeonameStoreFactory {

  private final Context context;

  @Inject public GeonameStoreFactory(Context context) {
    if (context == null) {
      throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
  }

  /**
   * Create {@link GeonameDataStore} to retrieve data from the Cloud.
   */
  public GeonameDataStore createCloudDataStore() {

    return new GeonameStore(context);
  }
}
