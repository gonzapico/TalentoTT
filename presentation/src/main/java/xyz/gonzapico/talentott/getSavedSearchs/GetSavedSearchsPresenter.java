package xyz.gonzapico.talentott.getSavedSearchs;

import android.content.Context;
import android.util.Log;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import xyz.gonzapico.exception.DefaultErrorBundle;
import xyz.gonzapico.interactor.BaseUseCase;
import xyz.gonzapico.interactor.DefaultObserver;
import xyz.gonzapico.talentott.exception.ErrorMessageFactory;

/**
 * Created by gfernandez on 3/04/17.
 */

public class GetSavedSearchsPresenter {

  private final static String TAG = "GetSavedSearchsPresenter";

  private BaseUseCase getSavedSearchsUseCase;

  private GetSavedSearchsView mGetGeoNamesView;
  private Context mContext;

  @Inject public GetSavedSearchsPresenter(@Named("searchsSaved") BaseUseCase useCaseGetGeonames) {
    this.getSavedSearchsUseCase = useCaseGetGeonames;
  }

  public void onAttachView(GetSavedSearchsView getGeoNamesView, Context context) {
    this.mGetGeoNamesView = getGeoNamesView;
    this.mContext = context;
  }

  public void onViewDetached() {
    this.mGetGeoNamesView = null;
    this.getSavedSearchsUseCase.dispose();
  }

  public void getSavedSearchs() {
    getSavedSearchsUseCase.execute(new GetSavedSearchsSuscriber(), null);
  }

  private final class GetSavedSearchsSuscriber extends DefaultObserver<List<String>> {

    public GetSavedSearchsSuscriber() {

    }

    @Override public void onComplete() {
      super.onComplete();
      Log.d(TAG, "onComplete");
    }

    @Override public void onError(Throwable e) {
      DefaultErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
      Log.e(TAG, errorBundle.getErrorMessage() + " " + errorBundle.getException().toString());
    }

    @Override public void onNext(List<String> cacheCityList) {
      Log.d(TAG, cacheCityList.size() + "");
      mGetGeoNamesView.showSuggestions(cacheCityList);
    }

    private void showErrorMessage(DefaultErrorBundle errorBundle) {
      String errorMessage = ErrorMessageFactory.create(mContext, errorBundle.getException());
      Log.e(TAG, errorMessage);
    }
  }
}
