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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs.zenbridgechat.adapter.ContactListRecyclerAdapter;
import com.cs.zenbridgechat.model.UserModel;
import com.cs.zenbridgechat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class SearchUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText searchInput;
    private ContactListRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_user);

        recyclerView = findViewById(R.id.search_user_recycler_view);
        ImageButton searchButton = findViewById(R.id.search_user_btn);
        ImageButton backButton = findViewById(R.id.search_back_button);
        searchInput = findViewById(R.id.search_phone_input);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Brings the user back to MainActivity
    public void backButtonClicked(View view) {
        finish();
    }

    // Searches the Firestore Database for the user input phone number.
    public void searchButtonClicked(View view) {
        String phoneNumber = searchInput.getText().toString().trim();

        // Validates user input for phone number and displays an error if it is empty.
        if (phoneNumber.length() != 12) {
            searchInput.setError("Must enter a phone number!");
            return;
        }
        setupSearchRecyclerView(phoneNumber);
    }

    /*
    Queries the USERS dataset in Firestore Database by querying the instance that matches the user inputted
    phone number. A Recycler View is set using an adapter based on the result. If the user exists in the Firestore
    Database, it will populate on this list. Otherwise it will show no search result.
     */
    private void setupSearchRecyclerView(String phoneNumber) {
        // Queries the USERS dataset in Firestore Database based on phone number
        Query query = FirebaseUtil.allUserCollectionReference()
                .whereEqualTo("phoneNumber", phoneNumber);

        // Builds a new FirestoreRecyclerOptions object of UserModel type
        FirestoreRecyclerOptions<UserModel> user = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query, UserModel.class).build();

        // Passed the FirestoreRecyclerOptions object into an adapter using a RecyclerView
        adapter = new ContactListRecyclerAdapter(user,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }


}