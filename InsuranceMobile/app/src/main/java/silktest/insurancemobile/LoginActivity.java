/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import silktest.insurancemobile.model.User;

public class LoginActivity extends Activity {
  private UserLoginTask mAuthTask = null;

  private AutoCompleteTextView emailView;
  private EditText passwordView;
  private View progressView;
  private View loginForm;
  private FloatingActionButton signUpFab;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_login);

    emailView = (AutoCompleteTextView) findViewById(R.id.email);

    passwordView = (EditText) findViewById(R.id.password);
    passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
          attemptLogin();
          return true;
        }
        return false;
      }
    });

    Button mEmailLogInButton = (Button) findViewById(R.id.log_in_button);
    mEmailLogInButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        attemptLogin();
      }
    });

    signUpFab = ((FloatingActionButton) findViewById(R.id.sign_up_fab));
    signUpFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("email", emailView.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
      }
    });

    progressView = findViewById(R.id.login_progress);
    loginForm = findViewById(R.id.email_login_form);

    findViewById(R.id.default_user).setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        emailView.setText("john.smith@gmail.com");
        passwordView.setText("john");
        return false;
      }
    });

    String createdUser = getIntent().getStringExtra("createdUser");
    if (!TextUtils.isEmpty(createdUser)) {
      Snackbar.make(signUpFab, "User " + createdUser + " signed up", Snackbar.LENGTH_LONG).show();
    }
  }

  private void attemptLogin() {
    if (mAuthTask != null) {
      return;
    }

    emailView.setError(null);
    passwordView.setError(null);

    String email = emailView.getText().toString();
    String password = passwordView.getText().toString();

    boolean cancel = false;
    View focusView = null;

    if (TextUtils.isEmpty(password)) {
      passwordView.setError(getString(R.string.error_invalid_password));
      focusView = passwordView;
      cancel = true;
    }

    if (TextUtils.isEmpty(email)) {
      emailView.setError(getString(R.string.error_field_required));
      focusView = emailView;
      cancel = true;
    } else if (!isEmailValid(email)) {
      emailView.setError(getString(R.string.error_invalid_email));
      focusView = emailView;
      cancel = true;
    }

    if (cancel) {
      focusView.requestFocus();
    } else {
      showProgress(true);
      mAuthTask = new UserLoginTask(email, password);
      mAuthTask.execute((Void) null);
    }
  }

  private boolean isEmailValid(String email) {
    return email.contains("@");
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
  private void showProgress(final boolean show) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

      loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
      signUpFab.setVisibility(show ? View.GONE : View.VISIBLE);
      progressView.setVisibility(show ? View.VISIBLE : View.GONE);
      progressView.animate().setDuration(shortAnimTime).alpha(
              show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
      });
    } else {
      // The ViewPropertyAnimator APIs are not available, so simply show
      // and hide the relevant UI components.
      progressView.setVisibility(show ? View.VISIBLE : View.GONE);
      loginForm.setVisibility(show ? View.VISIBLE : View.GONE);
      signUpFab.setVisibility(show ? View.VISIBLE : View.GONE);
    }
  }

  /**
   * Represents an asynchronous login/registration task used to authenticate
   * the user.
   */
  public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mEmail;
    private final String mPassword;

    UserLoginTask(String email, String password) {
      mEmail = email;
      mPassword = password;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
      try {
        // Simulate network access.
        Thread.sleep(500);
      } catch (InterruptedException e) {
        return false;
      }

      User user = User.getUserByEmail(mEmail);
      if (user != null) {
        return user.getPassword().equals(mPassword);
      }

      return false;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
      mAuthTask = null;
      showProgress(false);

      if (success) {
        finish();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("email", mEmail);
        startActivity(intent);
      } else {
        passwordView.setError(getString(R.string.error_incorrect_password));
        passwordView.requestFocus();
      }
    }

    @Override
    protected void onCancelled() {
      mAuthTask = null;
      showProgress(false);
    }
  }
}

