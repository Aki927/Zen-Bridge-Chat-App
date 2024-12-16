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
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cs.zenbridgechat.utils.FirebaseUtil;

public class FlashWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flash_window);

        /*
        Uses a Handler with a 2 sec delay. If the user has exited the app and runs the app again,
        they are sent to MainActivity and skip the CopyrightActivity for terms and conditions
         */
        new Handler().postDelayed(() -> {
            Intent intent;
            if (FirebaseUtil.isLoggedIn()) {
                intent = new Intent(FlashWindow.this, MainActivity.class);
            } else {
                intent = new Intent(FlashWindow.this, CopyrightActivity.class);
            }
            startActivity(intent);
            finish();
        }, 2000); // 2 second delay

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}