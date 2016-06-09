/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import silktest.insurancemobile.model.AutomobileType;
import silktest.insurancemobile.model.DrivingRecord;
import silktest.insurancemobile.model.Financing;
import silktest.insurancemobile.model.Gender;
import silktest.insurancemobile.model.User;

public class AutoQuoteActivity extends BackNavigatingActivity {
  private static final Map<String, List<String>> CARS = new HashMap<>();

  static {
    CARS.put("Buick", Arrays.asList("-", "Century", "Electra", "Enclave", "Le Sabre", "Roadmaster", "Skylark"));
    CARS.put("Chrysler", Arrays.asList("-", "300 M", "Aspen", "Crossfire", "Grand Voyager", "Le Baron", "PT Cruiser", "Sebring"));
    CARS.put("Cadillac", Arrays.asList("-", "BLS", "Deville", "Eldorado", "Escalade", "Seville", "SRX", "CTS"));
    CARS.put("Toyota", Arrays.asList("-", "4-Runner", "Avalon", "Camry", "Hilux", "Celica"));
    CARS.put("Pontiac", Arrays.asList("-", "Firebird", "Trans Sport", "Solstice Cabriolet"));
    CARS.put("Lexus", Arrays.asList("-", "GS430", "RX400", "LS600h", "ES 350"));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_auto_quote);

    setupToolbar();

    String email = getIntent().getStringExtra("email");

    final EditText viewZipCode = ((EditText) findViewById(R.id.autoquote_zip));
    final EditText viewEmail = ((EditText) findViewById(R.id.autoquote_email));
    final EditText viewAge = ((EditText) findViewById(R.id.autoquote_age));
    final EditText viewYear = ((EditText) findViewById(R.id.autoquote_car_year));

    final RadioGroup viewGender = ((RadioGroup) findViewById(R.id.radio_gender));
    final RadioGroup viewType = ((RadioGroup) findViewById(R.id.radio_automobiletype));

    final Spinner viewMake = ((Spinner) findViewById(R.id.car_make));
    final Spinner viewModel = ((Spinner) findViewById(R.id.car_model));
    final Spinner viewFinancing = ((Spinner) findViewById(R.id.financial_info));

    final RatingBar viewDrivingRecord = ((RatingBar) findViewById(R.id.rating_driving_record));

    if (!TextUtils.isEmpty(email)) {
      User user = User.getUserByEmail(email);

      viewEmail.setText(user.getEmail());
      String birthday = user.getBirthday();

      try {
        Date date = new SimpleDateFormat("dd.MM.yyyy").parse(birthday);

        int age = getAge(date);

        viewAge.setText(String.valueOf(age));
      } catch (ParseException ex) {
        // ignored
      }
    }

    final Spinner make = ((Spinner) findViewById(R.id.car_make));

    ArrayAdapter<String> makeSpinnerAdapter = new ArrayAdapter<String>(AutoQuoteActivity.this, android.R.layout.simple_spinner_dropdown_item);
    makeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    make.setAdapter(makeSpinnerAdapter);
    makeSpinnerAdapter.addAll(CARS.keySet());
    makeSpinnerAdapter.notifyDataSetChanged();

    final Spinner model = ((Spinner) findViewById(R.id.car_model));

    make.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ArrayAdapter<String> modelSpinnerAdapter = new ArrayAdapter<String>(AutoQuoteActivity.this, android.R.layout.simple_spinner_dropdown_item);
        modelSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        model.setAdapter(modelSpinnerAdapter);
        modelSpinnerAdapter.addAll(CARS.get(make.getSelectedItem()));
        modelSpinnerAdapter.notifyDataSetChanged();

        model.setSelection(0);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });


    final View submitButton = findViewById(R.id.submit);

    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        boolean hasError = false;

        hasError |= EditTextUtils.mustNotBeEmpty(viewZipCode);
        hasError |= EditTextUtils.mustNotBeEmpty(viewEmail);
        hasError |= EditTextUtils.mustNotBeEmpty(viewAge);
        hasError |= EditTextUtils.mustNotBeEmpty(viewYear);

        if ("-".equals(model.getSelectedItem())) {
          TextView errorText = (TextView) model.getSelectedView();
          errorText.setError("empty");
          errorText.setTextColor(Color.RED);
          errorText.setText("Model must be provided");

          hasError |= true;
        }

        if (hasError) {
          return;
        }

        int age = Integer.parseInt(viewAge.getText().toString());
        if (age < 1 || age > 99) {
          viewAge.setError("Invalid age");
          hasError = true;
        }

        int year = Integer.parseInt(viewYear.getText().toString());
        if (year < 1900 || year > 2100) {
          viewYear.setError("Invalid year");
          hasError = true;
        }

        if (!hasError) {
          Intent intent = new Intent(AutoQuoteActivity.this, QuoteDetailsActivity.class);

          DrivingRecord drivingRecord = calculateDrivingRecord(viewDrivingRecord.getRating());
          Gender gender = Gender.valueOf(((RadioButton) findViewById(viewGender.getCheckedRadioButtonId())).getText().toString().toUpperCase());
          AutomobileType type = AutomobileType.valueOf(((RadioButton) findViewById(viewType.getCheckedRadioButtonId())).getText().toString().toUpperCase());
          Financing financing = Financing.valueOf(viewFinancing.getSelectedItem().toString().toUpperCase());

          intent.putExtra("zip", viewZipCode.getText().toString());
          intent.putExtra("age", age);
          intent.putExtra("gender", ((RadioButton) findViewById(viewGender.getCheckedRadioButtonId())).getText());
          intent.putExtra("drivingRecord", drivingRecord.toString());
          intent.putExtra("type", ((RadioButton) findViewById(viewType.getCheckedRadioButtonId())).getText());
          intent.putExtra("make", viewMake.getSelectedItem().toString());
          intent.putExtra("year", year);
          intent.putExtra("model", viewModel.getSelectedItem().toString());

          double quote = calculate(type, gender, year, drivingRecord, financing);

          intent.putExtra("quote", quote);

          startActivity(intent);
        }
      }
    });
  }

  private int getAge(Date dateOfBirth) {
    //  from http://stackoverflow.com/questions/1116123/how-do-i-calculate-someones-age-in-java

    Calendar today = Calendar.getInstance();
    Calendar birthDate = Calendar.getInstance();

    int age = 0;

    birthDate.setTime(dateOfBirth);
    if (birthDate.after(today)) {
      throw new IllegalArgumentException("Can't be born in the future");
    }

    age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

    // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
    if ((birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
            (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
      age--;

      // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
    } else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH)) &&
            (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))) {
      age--;
    }

    return age;
  }

  private DrivingRecord calculateDrivingRecord(float record) {
    if (record < 1) {
      return DrivingRecord.POOR;
    } else if (record < 2) {
      return DrivingRecord.FAIR;
    } else if (record <= 3) {
      return DrivingRecord.GOOD;
    } else {
      return DrivingRecord.EXCELLENT;
    }
  }

  public double calculate(AutomobileType type, Gender gender, int year, DrivingRecord drivingRecord, Financing financing) {
    // from http://atlis-svn-pm/svn/pm/branches/DemoApp/InsuranceWebModelControl/src/insurance/model/service/impl/QuoteServiceImpl.java

    double price = 1000;
    int currentYear = 2016;

    if (type == AutomobileType.TRUCK) {
      price *= 1.2;
    }
    if (gender == Gender.MALE) {
      price *= 1.2;
    }

    switch (drivingRecord) {
      case POOR:
        price = price + 300;
        break;
      case FAIR:
        price = price + 200;
        break;
      case GOOD:
        price = price + 100;
        break;
    }

    if (year < currentYear) {
      price -= ((currentYear - year) * 10);
    }

    switch (financing) {
      case FINANCED:
        price = price * 1.2;
        break;
      case LEASE:
        price = price * 1.4;
        break;
    }

    BigDecimal bd = new BigDecimal(price);
    bd = bd.setScale(2, BigDecimal.ROUND_UP);
    return bd.doubleValue();
  }

}
