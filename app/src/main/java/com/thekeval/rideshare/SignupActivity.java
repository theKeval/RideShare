package com.thekeval.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

public class SignupActivity extends AppCompatActivity {

    EditText etFirstname, etLastName, etEmail, etPhoneNumber, etPassword;
    ImageView imgProfile;
    RadioButton rbMale, rbFemale;
    Button btnSignup, btnShowPassword, btnHidePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // finding View by ID
        etFirstname = findViewById(R.id.et_firstName);
        etLastName = findViewById(R.id.et_lastName);
        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);

        imgProfile = findViewById(R.id.imgProfile);

        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);

        btnSignup = findViewById(R.id.btnSignUp);
        btnShowPassword = findViewById(R.id.btnShowPassword);
        btnHidePassword = findViewById(R.id.btnHidePassword);

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

    }
}