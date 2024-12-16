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
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OneTimePassActivity extends AppCompatActivity {

    private String phoneNumber;         // gets assigned to the intent passed from LoginActivity
    private String verificationCode;    // verify OTP
    private PhoneAuthProvider.ForceResendingToken resendToken;      // Re-send OTP to user

    private EditText userInputOTP;
    private Button verifyButton;
    private ProgressBar loadingProgressBar;
    private TextView resendOTPCode;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();  // Instantiate Firebase Authorization object
    private CountDownTimer cdt;                                     // Countdown for resending OTP

    private static final long START_TIME = 60000;                   // Initialize start timer to 60 seconds
    private long timeRemaining = START_TIME;

    private final String OTP_KEY = "otpKEY";                        // Key: OTP instance state
    private final String OTP_TEXT_KEY = "otpTextView";              // Key: OTP textview instance state
    private final String SECONDS_KEY = "secondsRemaining";          // Key: seconds instance state
    private final String VERIFY_KEY = "verifyBtn";                  // Key: verify button visibility state
    private final String VERIFICATION_KEY = "OTPCode";              // Key: OTP verification code state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_one_time_pass);

        userInputOTP = findViewById(R.id.OTPText);
        verifyButton = findViewById(R.id.verifyButton);
        loadingProgressBar = findViewById(R.id.progressBar);
        resendOTPCode = findViewById(R.id.resendOTPCode);

        // Get the intent of the phone number that was passed from LoginActivity
        phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        Log.i("TAG", "OneTimePassActivity >>> PHONE_NUMBER: " + phoneNumber);

        sendVerificationCode(phoneNumber, false);    // Send the OTP when user enters this activity
        startCountdownTimer(timeRemaining);       // and start the timer from time remaining (initially 60 seconds)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Save instance state for user input for OTP, and countdown textview and remaining time
        if (savedInstanceState != null) {
            userInputOTP.setText(savedInstanceState.getString(OTP_KEY, ""));
            resendOTPCode.setText(savedInstanceState.getString(OTP_TEXT_KEY, ""));
            timeRemaining = savedInstanceState.getLong(SECONDS_KEY, START_TIME);
            verifyButton.setVisibility(savedInstanceState.getInt(VERIFY_KEY, View.VISIBLE));
            verificationCode = savedInstanceState.getString(VERIFICATION_KEY, "");
        } else {
            timeRemaining = START_TIME;
        }
    }

    /*
    Use Firebase Authentication phone provider to send the user an OTP via SMS.
    User then has to verify this OTP by entering it in the otpInput EditText and click verify in the future.
     */
    public void sendVerificationCode(String p_num, boolean resend) {
        setInProgress(true);
        long TIMEOUT = 60L;
        /*
        Instantiate Firebase Authentication builder with Phone sign-in provider
         */
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)              // Initialize builder with Firebase Authorization
                        .setPhoneNumber(phoneNumber)            // Phone number to verify
                        .setTimeout(TIMEOUT, TimeUnit.SECONDS)  // Timeout = 60 seconds
                        .setActivity(this)                      // Activity for callback binding

                        /*
                        If no activity is passed, reCAPTCHA verification can't be used.
                        Sets callbacks.
                         */
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            // Callback when verified, calls signIn passing the phone auth credential as a parameter
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                setInProgress(false);
                            }

                            // Callback when verification fails, user is notified with a toast message.
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.e("TAG", "OneTimePassActivity >>> Verification failed: " + e.getMessage());
                                Toast.makeText(OneTimePassActivity.this,
                                        "Verification Failed!", Toast.LENGTH_LONG).show();
                                setInProgress(false);
                            }

                            // Callback when the OTP is send to the users phone via SMS.
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendToken = forceResendingToken;  // Save the OTP to be sent
                                Toast.makeText(OneTimePassActivity.this,
                                        "One Time Pass Sent! Please check your SMS.", Toast.LENGTH_LONG).show();
                                setInProgress(false);
                            }
                        });
        if (resend) {
            // When resending the OTP in the event timer ran out and user clicked resend OTP button
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendToken).build());
        } else {
            // When sending the OTP for the first time and timer has never timed out
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    /*
    When the timer runs out and user clicks 'Resend OTP', the verify button is temporarily hidden
    until another OTP is sent, when becomes visible again when the user receives the OTP via SMS.
     */
    public void setInProgress(boolean inProgress) {
        if (inProgress) {
            loadingProgressBar.setVisibility(View.VISIBLE);
            verifyButton.setVisibility(View.GONE);
        } else {
            loadingProgressBar.setVisibility(View.GONE);
            verifyButton.setVisibility(View.VISIBLE);
        }
    }

    /*
    When verification is complete, an intent is used to go to UsernameActivity. It passes the user's phone number
    and the user is sent to UsernameActivity.
     */
    public void signIn(PhoneAuthCredential pac) {
        startCountdownTimer(timeRemaining);
        setInProgress(true);
        /*
        FirebaseAuth uses the credential that was made when verify button was clicked and OTP was verified,
        a listener is created to see if this task was successful. If so, the user is sent to UsernameActivity,
        otherwise this method is returned and user is notified that OTP was invalid.
         */
        mAuth.signInWithCredential(pac).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(OneTimePassActivity.this, UsernameActivity.class);
                intent.putExtra("PHONE_NUMBER", phoneNumber);
                Toast.makeText(OneTimePassActivity.this, "OTP verified!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            } else {
                Toast.makeText(OneTimePassActivity.this, "OTP not valid!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Starts the countdown. When it reaches 0, the countdown only stats up when user clicks 'Resend OTP'
    private void startCountdownTimer(long startTimeInMillis) {
        resendOTPCode.setEnabled(false);
        if (cdt != null) cdt.cancel(); // Cancel any countdown timers to avoid overlapping timers
        cdt = new CountDownTimer(startTimeInMillis, 1000) {
            long toSeconds;
            @Override
            public void onTick(long l) {
                timeRemaining = l;
                toSeconds = timeRemaining / 1000;
                resendOTPCode.setText(String.valueOf("Resend OTP in " + toSeconds + " seconds"));
            }

            @Override
            public void onFinish() {
                resendOTPCode.setEnabled(true);
                resendOTPCode.setText("Click here to resend OTP.");
            }
        }.start();
    }

    /*
    When the verify button is clicked, a PhoneAuthCredential is created using the OTP code entered by the user,
    then it passed as a parameter in signIn() method.
     */
    public void verifyButtonClicked(View view) {
        String enteredOTP = userInputOTP.getText().toString();
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationCode, enteredOTP);
        signIn(phoneAuthCredential);
        setInProgress(true);
    }

    // Resends the OTP to the user and resets the timeRemaining variable back to 60 seconds, and timer restarts.
    public void resendOTP(View view) {
        sendVerificationCode(phoneNumber, true);
        timeRemaining = START_TIME; // START_TIME = 60 seconds
        startCountdownTimer(timeRemaining);
    }

    // Out-state for saved instances
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(OTP_KEY, userInputOTP.getText().toString());
        outState.putString(OTP_TEXT_KEY, resendOTPCode.getText().toString());
        outState.putLong(SECONDS_KEY, timeRemaining);
        outState.putInt(VERIFY_KEY, verifyButton.getVisibility());
        outState.putString(VERIFICATION_KEY, verificationCode);
    }

    // Restores timer when user changes screen orientation between portrait and landscape mode
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userInputOTP.setText(savedInstanceState.getString(OTP_KEY, ""));
        resendOTPCode.setText(savedInstanceState.getString(OTP_TEXT_KEY, ""));
        timeRemaining = savedInstanceState.getLong(SECONDS_KEY, START_TIME);
        verifyButton.setVisibility(savedInstanceState.getInt(VERIFY_KEY, View.VISIBLE));
        verificationCode = savedInstanceState.getString(VERIFICATION_KEY, null);

        startCountdownTimer(timeRemaining);
    }

}