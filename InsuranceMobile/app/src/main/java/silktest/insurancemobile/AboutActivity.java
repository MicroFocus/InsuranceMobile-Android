/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */package silktest.insurancemobile;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.InputStream;

public class AboutActivity extends BackNavigatingActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    WebView.setWebContentsDebuggingEnabled(true);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    setupToolbar();

    WebView webView = (WebView) findViewById(R.id.webView);
    webView.getSettings().setJavaScriptEnabled(true);

    webView.setBackgroundColor(Color.TRANSPARENT);

    InputStream is = getResources().openRawResource(R.raw.about);

    try {
      java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
      String str = s.next();
      s.close();
      webView.loadData(str, "text/html", null);
      is.close();
    }catch(Exception ex) {
      if (is != null) {
        try {
          is.close();
        }catch(Exception ex2) {
          // Ignored
        }
      }
    }
  }
}
