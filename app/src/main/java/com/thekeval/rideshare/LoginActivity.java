package com.thekeval.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thekeval.rideshare.database.DatabaseHandler;
import com.thekeval.rideshare.model.RiderModel;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnSignUp;

    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp_onLoginScreen);

        dbHandler = new DatabaseHandler(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText() == null ? "" : etEmail.getText().toString();
                String password = etPassword.getText() == null ? "" : etPassword.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter valid Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                RiderModel rider = dbHandler.getRider(email);
                if (rider == null) {
                    Toast.makeText(LoginActivity.this, "No rider exist with that email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!password.equals(rider.password)){
                    Toast.makeText(LoginActivity.this, "Username and Password don't match", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    // Login successful

                    // Create an object of SharedPreferences.
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    //now get Editor
                    SharedPreferences.Editor editor = sharedPref.edit();
                    //put your value
                    editor.putString("email", rider.email);
                    //commits your edits
                    editor.apply();

                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    etPassword.setText("");
                    etEmail.setText("");
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentSignUp = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intentSignUp);
            }
        });
    }

    private void login() {

    }

}