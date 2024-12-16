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

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cs.zenbridgechat.model.BugReportModel;
import com.cs.zenbridgechat.utils.FirebaseUtil;
import com.google.firebase.Timestamp;

public class ReportBugsActivity extends AppCompatActivity {
    EditText feedbackText;
    Button sendButton;
    ImageButton backButton;
    BugReportModel bugReportModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_bugs);

        feedbackText = findViewById(R.id.feedback_input);
        sendButton = findViewById(R.id.submit_bug_report_button);
        backButton = findViewById(R.id.feedback_back_button);

        sendButton.setOnClickListener(view -> {
            setBugReport();
        });

        backButton.setOnClickListener(view -> {
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Saves the user inputted bug report and feedback into Firestore Database
    private void setBugReport() {
        String userInput = feedbackText.getText().toString();
        if (userInput.length() < 3) {
            Toast.makeText(this, "Feedback must be at least 3 characters long!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (bugReportModel != null) {
            bugReportModel.setBugReportMessage(userInput);
        } else {
            bugReportModel = new BugReportModel(userInput, Timestamp.now(), FirebaseUtil.currentUserId());

            FirebaseUtil.allBugReportCollectionReference().add(bugReportModel).addOnSuccessListener(task -> {
                Toast.makeText(this, "Report sent successfully!", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "ReportBugsActivity >>> setBugReport was successful.");
                feedbackText.setText("");
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Error: Report failed to send.", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "ReportBugsActivity >>> Error saving bug report", e);
            });
        }

    }

}