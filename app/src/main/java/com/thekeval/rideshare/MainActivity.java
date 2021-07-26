package com.thekeval.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
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
                navigateToMapActivity();
            }
        });

        btnMapSelector_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMapActivity();
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

    private void navigateToMapActivity() {
        Intent mapIntent = new Intent(MainActivity.this, LocationSelectorActivity.class);
        startActivity(mapIntent);
    }

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


}