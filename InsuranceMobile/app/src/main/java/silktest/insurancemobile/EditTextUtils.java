/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

public class EditTextUtils {
  public static boolean mustNotBeEmpty(EditText editText) {
    editText.setError(null);
    if (TextUtils.isEmpty(editText.getText().toString())) {
      TextInputLayout textInputLayout = (TextInputLayout) editText.getParent();
      editText.setError(textInputLayout.getHint() + " must be provided");
      return true;
    }
    return false;
  }
}
