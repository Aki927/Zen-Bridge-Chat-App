package com.cs.zenbridgechat;

//*********************************************************************
//	Jerome Laranang, T00635622
//
//	COMP 2161 Final Project, Zen Bridge Chat Application, December 7, 2024
//
//  This Android program is a chat application where therapists, patients,
//  and Zen Bridge members can connect and have a counselling sessions via messaging.
//  It uses Firebase Authentication using phone number for login including OTP
//  verification. The user data, chatroom data and bug report/feedback data are stored
//  in Firestore Database.
//*********************************************************************

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private EditText phoneNumber;

    private final String PHONE_KEY = "phoneNumberInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        countryCodePicker = findViewById(R.id.countryCode);
        phoneNumber = findViewById(R.id.phoneNumber);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        // Progress bar is only visible once user clicks verify button
        progressBar.setVisibility(View.GONE);

        // Registers the carrier number country code i.e., +1 for Canada and US.
        countryCodePicker.registerCarrierNumberEditText(phoneNumber);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Saves current state of phone number when user re-enters the app or changes screen orientation
        if (savedInstanceState != null) {
            phoneNumber.setText(savedInstanceState.getString(PHONE_KEY, ""));
        }
    }

    /*
    Validates user entry for phone number and shows an error if number is invalid or empty.
    Phone number is put into an intent and is passed with the user to OneTimePassActivity
     */
    public void loginButtonClicked(View view) {
        String phoneNumberText = phoneNumber.getText().toString().trim();
        if (phoneNumberText.isEmpty()) {
            phoneNumber.setError("Number cannot be left blank!");
        } else if (!countryCodePicker.isValidFullNumber()) {
            phoneNumber.setError("Not a valid number");
        } else {
            Intent intent = new Intent(LoginActivity.this, OneTimePassActivity.class);
            intent.putExtra("PHONE_NUMBER", countryCodePicker.getFullNumberWithPlus());
            startActivity(intent);
        }

    }

    // Out-state for saved instances
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PHONE_KEY, phoneNumber.getText().toString());
    }
}