package com.thekeval.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.thekeval.rideshare.database.DatabaseHandler;
import com.thekeval.rideshare.model.RiderModel;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    EditText etFirstname, etLastName, etEmail, etPhoneNumber, etPassword;
    ImageView imgProfile;
    RadioButton rbMale, rbFemale;
    Button btnSignup, btnShowPassword, btnHidePassword;

    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // finding View by ID
        etFirstname = findViewById(R.id.et_login_email);
        etLastName = findViewById(R.id.et_lastName);
        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_login_password);

        imgProfile = findViewById(R.id.imgProfile);

        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);

        btnSignup = findViewById(R.id.btnSignUp);
        btnShowPassword = findViewById(R.id.btnShowPassword);
        btnHidePassword = findViewById(R.id.btnHidePassword);

        // default setting & initialization
        dbHandler = new DatabaseHandler(this);
        rbMale.setChecked(true);

        // OnClick events
        btnShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnShowPassword.setVisibility(View.GONE);
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                etPassword.setSelection(etPassword.getText().length());
                btnHidePassword.setVisibility(View.VISIBLE);
            }
        });

        btnHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHidePassword.setVisibility(View.GONE);
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPassword.setSelection(etPassword.getText().length());
                btnShowPassword.setVisibility(View.VISIBLE);
            }
        });

        rbMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgProfile.setImageResource(R.drawable.male_profile_placeholder);
            }
        });

        rbFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgProfile.setImageResource(R.drawable.female_profile_placeholder);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = etFirstname.getText() == null ? "" : etFirstname.getText().toString();
                String lName = etLastName.getText() == null ? "" : etLastName.getText().toString();
                String password = etPassword.getText() == null ? "" : etPassword.getText().toString();
                String emailAddress = etEmail.getText() == null ? "" : etEmail.getText().toString();
                String phoneNumber = etPhoneNumber.getText() == null ? "" : etPhoneNumber.getText().toString();

                if (fName.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter valid First Name", Toast.LENGTH_SHORT).show();
                    etFirstname.requestFocus();
                    return;
                }

                if (lName.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter valid Last Name", Toast.LENGTH_SHORT).show();
                    etLastName.requestFocus();
                    return;
                }

                if (Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                    // pass - do nothing
                }
                else{
                    Toast.makeText(SignupActivity.this, "Enter valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Pattern.matches("^[+]?[0-9]{10,13}$", phoneNumber)) { // if (Patterns.PHONE.matcher(phoneNumber).matches()) {
                    // pass - do nothing
                }
                else {
                    Toast.makeText(SignupActivity.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                RiderModel rider = new RiderModel(fName + " " + lName, password, rbMale.isChecked() ? "male" : rbFemale.isChecked() ? "female" : "male", emailAddress, phoneNumber);
                dbHandler.addRider(rider);
                Toast.makeText(SignupActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();

                // Create an object of SharedPreferences.
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SignupActivity.this);
                //now get Editor
                SharedPreferences.Editor editor = sharedPref.edit();
                //put your value
                editor.putString("email", rider.email);
                //commits your edits
                editor.apply();

                Intent mainIntent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(mainIntent);

                etFirstname.setText("");
                etLastName.setText("");
                etEmail.setText("");
                etPassword.setText("");
                etPhoneNumber.setText("");

            }
        });

    }
}