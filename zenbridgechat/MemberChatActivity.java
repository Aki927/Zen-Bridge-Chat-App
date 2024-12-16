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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs.zenbridgechat.adapter.SessionRecyclerAdapter;
import com.cs.zenbridgechat.model.ChatMessageModel;
import com.cs.zenbridgechat.model.ChatRoomModel;
import com.cs.zenbridgechat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class MemberChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView other_user_name;
    ImageButton sendButton;
    EditText userInput;
    String otherUserName, phoneNumber, chatRoomId, otherUserId;
    ChatRoomModel chatRoomModel;
    SessionRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_member_chat);

        recyclerView = findViewById(R.id.member_chat_recycler_view);
        other_user_name = findViewById(R.id.other_user_name);
        userInput = findViewById(R.id.memberUserInput);
        sendButton = findViewById(R.id.sendButton);

        SharedPreferences sharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "Default Phone");
        otherUserName = sharedPreferences.getString("USER_NAME", "Default Name");
        otherUserId = sharedPreferences.getString("USER_ID", "Default ID");
        Log.d("TAG", "############## MemberChatActivity >>> otherUserId = " + otherUserId);

        chatRoomId = FirebaseUtil.getChatRoomId(FirebaseUtil.currentUserId(), otherUserId);
        Log.d("TAG", "############## MemberChatActivity >>> chatRoomId = " + chatRoomId);
        
        other_user_name.setText(otherUserName);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         */
        sendButton.setOnClickListener(view -> {
            String userMessage = userInput.getText().toString().trim();
            if (userMessage.isEmpty()) {
                return;
            }
            sendMessage(userMessage);
        });

        getOrCreateChatRoomModel();
        setupChatRecyclerView();
    }

    public void setupChatRecyclerView() {
        Query query = FirebaseUtil.getChatRoomMessageReference(chatRoomId)
                .orderBy("timestamp", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ChatMessageModel> user = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query, ChatMessageModel.class).build();

        adapter = new SessionRecyclerAdapter(user, getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); // test
        layoutManager.setStackFromEnd(true); // Scroll to the bottom for new messages
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });

    }

    public void getOrCreateChatRoomModel() {
        FirebaseUtil.getChatRoomReference(chatRoomId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                chatRoomModel = task.getResult().toObject(ChatRoomModel.class);
                if (chatRoomModel == null) {
                    chatRoomModel = new ChatRoomModel(
                            chatRoomId,
                            "",
                            Timestamp.now(),
                            Arrays.asList(FirebaseUtil.currentUserId(), otherUserId)
                    );
                    FirebaseUtil.getChatRoomReference(chatRoomId).set(chatRoomModel);
                }
            }
        });
    }

    public void backButtonClicked(View view) {
        finish();
    }

    /*
    public void sendButtonClicked(View view) {
        String userMessage = userInput.getText().toString().trim();
        if (userMessage.isEmpty()) {
            return;
        }
        sendMessage(userMessage);
    }
     */

    public void sendMessage(String userMessage) {
        chatRoomModel.setTimestamp(Timestamp.now());
        chatRoomModel.setLastMessageSenderId(FirebaseUtil.currentUserId());
        chatRoomModel.setLastMessage(userMessage);
        FirebaseUtil.getChatRoomReference(chatRoomId).set(chatRoomModel);

        ChatMessageModel cmm = new ChatMessageModel(userMessage, FirebaseUtil.currentUserId(), Timestamp.now());
        FirebaseUtil.getChatRoomMessageReference(chatRoomId).add(cmm).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    userInput.setText("");
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.startListening();
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
            adapter.startListening();
    }

}