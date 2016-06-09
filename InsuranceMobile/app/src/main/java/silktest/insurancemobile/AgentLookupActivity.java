/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.google.android.gms.common.api.GoogleApiClient.*;

public class AgentLookupActivity extends BackNavigatingActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
  private GoogleApiClient client;
  private Location location;
  private EditText locationText;

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onStop() {
    if (client.isConnected()) {
      client.disconnect();
    }
    super.onStop();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_agent_lookup);
    setupToolbar();

    client = new Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();

    locationText = ((EditText) findViewById(R.id.agent_search_zip));

    findViewById(R.id.search_by_location_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!EditTextUtils.mustNotBeEmpty(locationText)) {
          Intent intent = new Intent(AgentLookupActivity.this, ShowAgentsActivity.class);
          intent.putExtra("location", locationText.getText().toString());
          startActivity(intent);
        }
      }
    });

    final ImageButton getCurrentLocation = (ImageButton) findViewById(R.id.use_current_location_button);
    getCurrentLocation.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (client.isConnected()) {
          client.disconnect();
        }
        client.connect();
      }
    });

    final EditText name = ((EditText) findViewById(R.id.agent_search_name));

    findViewById(R.id.search_by_name_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!EditTextUtils.mustNotBeEmpty(name)) {
          Intent intent = new Intent(AgentLookupActivity.this, ShowAgentsActivity.class);
          intent.putExtra("name", name.getText().toString());
          startActivity(intent);
        }
      }
    });

    findViewById(R.id.show_all_agents_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(AgentLookupActivity.this, ShowAgentsActivity.class));
      }
    });
  }

  @Override
  public void onConnected(Bundle bundle) {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      showErrorMessage("This app doesn't have the permission to access your location");
      return;
    }

    location = LocationServices.FusedLocationApi.getLastLocation(client);
    LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

    if (location != null) {
      updateLocation(location);
    } else {
      showErrorMessage("Could not retrieve your current location");
    }
  }

  private void updateLocation(Location location) {
    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    try {
      List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

      if (addresses == null || addresses.size() == 0) {
        showErrorMessage("Could not find a city near your current location (" + location.getLatitude() + "/" + location.getLongitude() + ")");
        return;
      }

      Address address = addresses.get(0);
      String zipCode = address.getPostalCode();
      locationText.setText(zipCode);

    } catch (IOException e) {
      showErrorMessage("Could not retrieve your current location");
    }
  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
    String message = "Could not retrieve your current location";
    if (connectionResult.getErrorMessage() != null) {
      message += " (" + connectionResult.getErrorMessage() + ")";
    }
    showErrorMessage(message);
  }

  @Override
  public void onLocationChanged(Location location) {
    updateLocation(location);
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

  }

  @Override
  public void onProviderEnabled(String provider) {

  }

  @Override
  public void onProviderDisabled(String provider) {

  }
}
