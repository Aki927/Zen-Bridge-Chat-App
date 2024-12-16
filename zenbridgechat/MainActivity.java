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
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ChatFragment chatFragment;
    private SettingsFragment settingsFragment;
    private InfoFragment infoFragment;

    private final String NAV_KEY = "bottomNav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Navigation used to set up options for: Chat, Settings, Info
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        ImageButton searchButton = findViewById(R.id.main_search_btn);

        chatFragment = new ChatFragment();          // Chat
        settingsFragment = new SettingsFragment();  // Settings
        infoFragment = new InfoFragment();          // Info

        // Uses an intent to direct the user to SearchUserActivity where they can search existing users
        searchButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchUserActivity.class);
            startActivity(intent);
        });

        // Changes the fragment view based on user selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_chat) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, chatFragment).commit();
            }

            if (item.getItemId() == R.id.menu_settings) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, settingsFragment).commit();
            }

            if (item.getItemId() == R.id.menu_info) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, infoFragment).commit();
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_chat);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Save instance state for navigation
        if (savedInstanceState != null) {
            int selectedItemId = savedInstanceState.getInt(NAV_KEY);
            bottomNavigationView.setSelectedItemId(selectedItemId);
        }

    }

    // Allows a fragment's instance state to be saved during screen orientation change
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_KEY, bottomNavigationView.getSelectedItemId());
    }




}