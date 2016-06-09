/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import silktest.insurancemobile.model.User;

public class MainActivity extends AppCompatActivity {
  private String email;
  private ActionBarDrawerToggle toggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    email = getIntent().getStringExtra("email");
    ((TextView) findViewById(R.id.logged_in_as)).setText(Html.fromHtml("Logged in as <b>" + User.getUserByEmail(email).toString() + "</b>"));

    final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    toolbar.setNavigationIcon(R.drawable.menu);

    final ListView drawerList = (ListView) findViewById(R.id.left_drawer);
    drawerList.setAdapter(new ArrayAdapter<String>(this,
            R.layout.menu_list_item, getResources().getStringArray(R.array.menu_items)) {

      @Override
      public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        view.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            drawerLayout.closeDrawer(drawerList);
            switch (position) {
              case 0:
                autoQuote();
                break;
              case 1:
                agentLookup();
                break;
              case 2:
                about();
                break;
              case 3:
                logout();
                break;
            }
          }
        });

        return view;
      }
    });


    toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

    drawerLayout.setDrawerListener(toggle);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    toggle.syncState();
  }

  protected void logout() {
    finish();
    startActivity(new Intent(this, LoginActivity.class));
  }

  protected void agentLookup() {
    startActivity(new Intent(this, AgentLookupActivity.class));
  }

  protected void autoQuote() {
    Intent intent = new Intent(this, AutoQuoteActivity.class);
    intent.putExtra("email", email);
    startActivity(intent);
  }

  protected void about() {
    startActivity(new Intent(this, AboutActivity.class));
  }
}
