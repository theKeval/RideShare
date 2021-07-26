package com.thekeval.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RideDetailActivity extends AppCompatActivity {

    TextView txtName, txtFrom, txtTo, txtStart, txtDuration, txtSeats, txtFare;
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        txtName = findViewById(R.id.txtName);
        txtFrom = findViewById(R.id.tvFromValue_ridedetail);
        txtTo = findViewById(R.id.tvToValue_ridedetail);
        txtStart = findViewById(R.id.tvStartTimeValue_ridedetail);
        txtDuration = findViewById(R.id.tvDurationValue_ridedetail);
        txtSeats = findViewById(R.id.tvSeatsValue_ridedetail);
        txtFare = findViewById(R.id.tvFareValue_ridedetail);

        btnBooking = findViewById(R.id.btnBook);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("ride_owner");
            if (name.equalsIgnoreCase("amit")) {
                txtName.setText("Amit Singh");
                txtFrom.setText("Chingcousy Park");
                txtTo.setText("bramlea Go station");
                txtStart.setText("2021-07-29  09:45");
                txtDuration.setText("27 minutes");
                txtSeats.setText("2 Seats");
                txtFare.setText("C$27.90");
            }
            else if (name.equalsIgnoreCase("john")) {
                txtName.setText("John Casey");
                txtFrom.setText("Main St/Bovaird, Brampton");
                txtTo.setText("city center Etobicoke");
                txtStart.setText("2021-07-29  09:00");
                txtDuration.setText("45 minutes");
                txtSeats.setText("3 Seat");
                txtFare.setText("C$25.00");
            }
            else if (name.equalsIgnoreCase("sam")) {
                txtName.setText("Sam Wyne");
                txtFrom.setText("Williams Parkway");
                txtTo.setText("downtown toronto");
                txtStart.setText("2021-07-29  11:30");
                txtDuration.setText("80 minutes");
                txtSeats.setText("1 Seat");
                txtFare.setText("C$37.23");
            }

        }

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RideDetailActivity.this, "Booking Confirmed! You will get SMS shortly", Toast.LENGTH_LONG).show();

                Intent mainIntent = new Intent(RideDetailActivity.this, MainActivity.class);
                mainIntent.putExtra("source", "booking");
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainIntent);
                finish();
            }
        });

    }
}