package com.thekeval.rideshare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;
import com.thekeval.rideshare.util.RangeTimePickerDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    EditText etFrom, etTo;
    TextView txtDateTime;
    Button btnSearchRide, btnSetDateTime, btnMapSelector_from, btnMapSelector_to;

    private Geocoder geocoder;
    private LatLng fromLatLng, toLatLng;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting UI elements
        etFrom = findViewById(R.id.et_from_address);
        etTo = findViewById(R.id.et_to_address);
        txtDateTime = findViewById(R.id.txt_dateTime);
        btnSearchRide = findViewById(R.id.btnSearchRide);
        btnSetDateTime = findViewById(R.id.btnSetDateTime);
        btnMapSelector_from = findViewById(R.id.btnMapSelector_from);
        btnMapSelector_to = findViewById(R.id.btnMapSelector_to);

        // initialization & default setting
        geocoder = new Geocoder(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double lat = extras.getDouble("lat");
            double lng = extras.getDouble("lng");

            String source = extras.getString("source");
            if (source.equalsIgnoreCase("from")) {
                etFrom.setText(getCompleteAddressString(lat, lng));
            }
            else if (source.equalsIgnoreCase("to")) {
                etTo.setText(getCompleteAddressString(lat, lng));
            }
        }


        // OnClick events
        btnSetDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });

        btnMapSelector_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(MainActivity.this, LocationSelectorActivity.class);
                mapIntent.putExtra("source", "from");
                startActivity(mapIntent);
                // startActivityForResult(mapIntent, 1);

                finish();
            }
        });

        btnMapSelector_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(MainActivity.this, LocationSelectorActivity.class);
                mapIntent.putExtra("source", "to");
                startActivity(mapIntent);
                // startActivityForResult(mapIntent, 2);

                finish();
            }
        });

        btnSearchRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromAddress = etFrom.getText().toString();
                String toAddress = etTo.getText().toString();
                String time = txtDateTime.getText().toString();

                if (fromAddress.isEmpty() || toAddress.isEmpty() || time.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter from and to locations and select time to proceed", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    List<Address> fromAddr = geocoder.getFromLocationName(fromAddress, 1);
                    if (fromAddr.size() > 0) {
                        Address address = fromAddr.get(0);
                        fromLatLng = new LatLng(address.getLatitude(), address.getLongitude());
                    }

                    List<Address> toAddr = geocoder.getFromLocationName(toAddress, 1);
                    if (toAddr.size() > 0) {
                        Address address = toAddr.get(0);
                        toLatLng = new LatLng(address.getLatitude(), address.getLongitude());
                    }

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//
//            Bundle extras = data.getExtras();
//            double lat = 0;
//            double lng = 0;
//            if (extras != null) {
//                lat = extras.getDouble("lat");
//                lng = extras.getDouble("lng");
//            }
//
//            if (requestCode == 1) {
//                etFrom.setText(getCompleteAddressString(lat, lng));
//            }
//            else if (requestCode == 2) {
//                etTo.setText(getCompleteAddressString(lat, lng));
//            }
//        }
//    }

    private void datePicker(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // String date = getResources().getString(R.string.date_format);
                String formatDate = String.format("%02d-%02d-%d", year, month + 1, dayOfMonth);
                txtDateTime.setText(formatDate);
                timePicker(formatDate);
            }
        }, year, month, dayOfMonth);

        calendar.add(Calendar.MONTH, 1);
        long now = System.currentTimeMillis() - 1000;
        long maxDate = calendar.getTimeInMillis();
        // datePickerDialog.getDatePicker().setMinDate(now);
        // datePickerDialog.getDatePicker().setMaxDate(maxDate); //After one month from now

        datePickerDialog.show();
    }

    private void timePicker(final String date){
        final Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        RangeTimePickerDialog timePickerDialog = new RangeTimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // String time = getResources().getString(R.string.time_format);
                String formatTime = String.format("%02d:%02d", hourOfDay, minute);
                String dateTime = date + "  " + formatTime;
                txtDateTime.setText(dateTime);
            }
        }, hour, minute, false);

        // timePickerDialog.setMin(hour + 1, minute);
        timePickerDialog.show();
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {

        String strAdd = "";

        // Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE,
                    LONGITUDE, 1);

            if (addresses != null && addresses.size() > 0) {

                Address returnedAddress = addresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {

                    strReturnedAddress
                            .append(returnedAddress.getAddressLine(i)).append(
                            ",");
                }

                strAdd = strReturnedAddress.toString();

                Log.w(TAG, "" + strReturnedAddress.toString());
            } else {
                Log.w(TAG, "No Address returned!");
                strAdd = String.format("%.5g%n", LATITUDE) + ", " + String.format("%.5g%n", LONGITUDE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "Cannot get Address!");
        }
        return strAdd;
    }


}