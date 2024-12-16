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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cs.zenbridgechat.model.UserModel;
import com.cs.zenbridgechat.utils.FirebaseUtil;
import com.google.firebase.Timestamp;

public class UsernameActivity extends AppCompatActivity {

    private EditText usernameInput;
    private Button startButton;
    private ProgressBar progressBar;
    private String phoneNumber;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_username);

        // Get the phoneNumber from the intent passed from OneTimePassActivity
        phoneNumber = getIntent().getExtras().getString("PHONE_NUMBER");
        Log.d("TAG", "UsernameActivity getIntent PHONE_NUMBER >>> " + phoneNumber);

        usernameInput = findViewById(R.id.usernameInput);
        startButton = findViewById(R.id.startButton);
        progressBar = findViewById(R.id.progressBar);

        // If a user already exists, their name is populated in usernameInput EditText
        getUsername();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /*
    Start button is temporarily hidden for a second when the user clicks the start button. This prevents
    multiple intentional or non-intentional clicks since this UsernameActivity is followed by the MainActivity
     */
    public void setInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            startButton.setVisibility(View.VISIBLE);
        }
    }

    /*
    If a user already exists, their name is populated in usernameInput EditText.
    To get username, FirebaseUtil.currentUserDetails() is called. This method returns a DocumentReference of
    the user if the instance exists in the Firestore Database. If it exists, then the username is loaded onto
    usernameInput.
     */
    public void getUsername() {
        setInProgress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            setInProgress(false);
            if (task.isSuccessful()) {
                userModel = task.getResult().toObject(UserModel.class);
                if (userModel != null) usernameInput.setText(userModel.getUserName());
            }
        });
    }


    /*
    User enters their desired name to be used in the application and must be 3 characters minimum,
    otherwise a toast message warns the user.
     */
    public void startButtonClicked(View view) {
        setUsername();
    }

    public void setUsername() {
        String name = usernameInput.getText().toString();
        int minLength = 3;
        if (name.length() < minLength) {
            Toast.makeText(this, "Name must be at least 3 characters long!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        setInProgress(true);

        /*
        If a user already exists, the user's username is set to whatever is in the EditText. Otherwise,
        a new UserModel is created with the current timestamp, phone number, user ID, and username.
        An intent is used to connect the user to the MainActivity.
         */
        if
            (userModel != null) userModel.setUserName(name);
        else
            userModel = new UserModel(Timestamp.now(), phoneNumber, name, FirebaseUtil.currentUserId());


        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(task -> {
            Intent intent = new Intent(UsernameActivity.this, MainActivity.class);

            /*
            Reset the navigation stack so other activities are removed and new task is created
            // for MainActivity
             */
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

}