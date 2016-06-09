/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import silktest.insurancemobile.model.User;

public class SignUpActivity extends BackNavigatingActivity {
  private EditText firstname;
  private EditText birthday;
  private EditText lastname;
  private EditText email;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_sign_up);

    setupToolbar();

    firstname = (EditText) findViewById(R.id.firstName);
    firstname.setError(null);
    lastname = (EditText) findViewById(R.id.lastName);
    lastname.setError(null);

    birthday = (EditText) findViewById(R.id.birthday);
    birthday.setText("01.01.1980");
    birthday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int year = 0;
        int month = 0;
        int day = 0;

        try {
          Date date = User.BIRTHDAY_FORMAT.parse(birthday.getText().toString());

          Calendar c = Calendar.getInstance();
          c.setTime(date);

          year = c.get(Calendar.YEAR);
          month = c.get(Calendar.MONTH);
          day = c.get(Calendar.DAY_OF_MONTH);
        } catch (Exception ex) {
          // Ignored
        }
        DatePickerDialog mDatePicker = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
          public void onDateSet(DatePicker datepicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            birthday.setText(User.BIRTHDAY_FORMAT.format(c.getTime()));
          }
        }, year, month, day);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
      }

    });

    email = (EditText) findViewById(R.id.email);
    email.setError(null);
    final EditText password = (EditText) findViewById(R.id.password);

    String defaultEmail = getIntent().getStringExtra("email");
    if (!TextUtils.isEmpty(defaultEmail)) {
      email.setText(defaultEmail);
    }

    ((Button) findViewById(R.id.sign_up_button)).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        boolean hasErrors = false;

        hasErrors |= EditTextUtils.mustNotBeEmpty(firstname);
        hasErrors |= EditTextUtils.mustNotBeEmpty(lastname);
        hasErrors |= EditTextUtils.mustNotBeEmpty(email);
        hasErrors |= EditTextUtils.mustNotBeEmpty(birthday);
        hasErrors |= EditTextUtils.mustNotBeEmpty(password);

        if (!hasErrors) {
          try {
            User newUser = new User(email.getText().toString(), firstname.getText().toString(), lastname.getText().toString(), birthday.getText().toString(), password.getText().toString());
            User.signUp(newUser);

            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            intent.putExtra("createdUser", newUser.toString());

            startActivity(intent);
          } catch (Exception ex) {
            email.setError(ex.getMessage());
          }
        }
      }
    });
  }
}
