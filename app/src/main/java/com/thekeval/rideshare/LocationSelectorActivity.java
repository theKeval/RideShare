package com.thekeval.rideshare;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thekeval.rideshare.databinding.ActivityLocationSelectorBinding;

public class LocationSelectorActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityLocationSelectorBinding binding;
    private static final String TAG = "MapActivity";

    LocationManager loc_man;
    LatLng lat_and_lng;
    LocationListener loc_listener;
    Geocoder geocoder;
    Marker marker;
    String source = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLocationSelectorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            source = extras.getString("source");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        loc_man = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        loc_listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (marker == null) {
                    lat_and_lng = new LatLng(location.getLatitude(), location.getLongitude());
                    if (mMap != null) {
                        drawMarker(lat_and_lng);
                    }
                }
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
        };

    }

    private void drawMarker(LatLng point) {
        mMap.clear();
        marker = mMap.addMarker(new MarkerOptions().position(point).title("Selected Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15.0F));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                Log.d("Map","Map clicked");
                // double latitude = point.latitude;
                // double longitude = point.longitude;
                // locationTextView.setText(getCompleteAdressString(latitude,longitude ));

                drawMarker(point);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent mainIntent = new Intent(LocationSelectorActivity.this, MainActivity.class);
                mainIntent.putExtra("lat", marker.getPosition().latitude);
                mainIntent.putExtra("lng", marker.getPosition().longitude);
                mainIntent.putExtra("source", source);
                // setResult(RESULT_OK, mainIntent);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainIntent);
                finish();

                return false;
            }
        });

        // if the permission is granted, we request the update and if the permission is not granted, we request permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            loc_man.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loc_listener);
            Location lastKnownLocation = loc_man.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {
                updateLocation(lastKnownLocation);
            }
        }

    }

    private void updateLocation(Location location) {
        Log.i(TAG, "updateLocation: " + location);

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        drawMarker(latLng);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            // location permission result
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startListening();
            }
        }

    }

    private void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            loc_man.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loc_listener);
            Log.d(TAG, "startListening: ");
        }
    }



}