package xyz.gonzapico.talentott;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;

public class HomeActivity extends BaseTMActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      addFragment(R.id.fragmentContainer, GetGeonamesFragment.newInstance());
    }
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_home;
  }

  @Override public void onBackPressed() {
    finish();
  }
}
