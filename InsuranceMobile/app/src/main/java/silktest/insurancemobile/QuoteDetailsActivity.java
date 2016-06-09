/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class QuoteDetailsActivity extends BackNavigatingActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quote_details);

    setupToolbar();

    appendStringExtra(R.id.zipCode, "zip");
    appendIntExtra(R.id.age, "age");
    appendStringExtra(R.id.gender, "gender");
    appendStringExtra(R.id.drivingRecord, "drivingRecord");
    appendStringExtra(R.id.type, "type");
    appendStringExtra(R.id.make, "make");
    appendIntExtra(R.id.year, "year");
    appendStringExtra(R.id.model, "model");

    double qoute = getIntent().getDoubleExtra("quote", 0.0);
    final TextView result = ((TextView) findViewById(R.id.result));

    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
    String formatted = formatter.format(qoute);
    result.setText(String.format(getResources().getString(R.string.quote_result), formatted));
  }

  private void appendStringExtra(@IdRes int id, String extra) {
    append(id, getIntent().getStringExtra(extra));
  }

  private void appendIntExtra(@IdRes int id, String extra) {
    append(id, String.valueOf(getIntent().getIntExtra(extra, 0)));
  }

  private void append(@IdRes int id, String value) {
    TextView view = (TextView) findViewById(id);
    view.setText(view.getText() + " " + value);
  }
}
