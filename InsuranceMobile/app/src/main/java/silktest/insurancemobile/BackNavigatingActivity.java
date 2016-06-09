/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public abstract class BackNavigatingActivity extends AppCompatActivity {
  protected void setupToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    if (toolbar != null) {
      setSupportActionBar(toolbar);

      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onBackPressed();
        }
      });

      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  protected void showErrorMessage(String message) {
    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this).setMessage(message);
    dlgAlert.setTitle("InsuranceMobile");
    dlgAlert.setPositiveButton("OK", null);
    dlgAlert.create().show();
  }
}
