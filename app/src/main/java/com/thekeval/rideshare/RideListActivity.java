package com.thekeval.rideshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RideListActivity extends AppCompatActivity {

    CardView card_amit, card_sam, card_john;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_list);

        card_amit = findViewById(R.id.card_amit);
        card_john = findViewById(R.id.card_john);
        card_sam = findViewById(R.id.card_sam);

        card_amit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRideDetails("amit");
            }
        });

        card_john.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRideDetails("john");
            }
        });

        card_sam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRideDetails("sam");
            }
        });

    }

    private void navigateToRideDetails(String name) {
        Intent rideDetailIntent = new Intent(RideListActivity.this, RideDetailActivity.class);
        rideDetailIntent.putExtra("ride_owner", name);
        startActivity(rideDetailIntent);
    }

}