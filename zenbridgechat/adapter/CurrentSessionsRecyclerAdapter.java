package com.cs.zenbridgechat.adapter;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs.zenbridgechat.MemberChatActivity;
import com.cs.zenbridgechat.R;
import com.cs.zenbridgechat.model.ChatRoomModel;
import com.cs.zenbridgechat.model.UserModel;
import com.cs.zenbridgechat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

// Adapter for creating the list of ongoing chatrooms between two users in the MainActivity ChatFragment
public class CurrentSessionsRecyclerAdapter extends FirestoreRecyclerAdapter<ChatRoomModel, CurrentSessionsRecyclerAdapter.ChatRoomModelViewHolder> {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CurrentSessionsRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatRoomModel> options, Context c) {
        super(options);
        this.context = c;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatRoomModelViewHolder holder, int position, @NonNull ChatRoomModel model) {
        FirebaseUtil.getSessionUsers(model.getUserIds()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel um = task.getResult().toObject(UserModel.class);
                holder.usernameText.setText(um.getUserName());
                holder.recentMessage.setText(model.getLastMessage());
                holder.recentTimestamp.setText(FirebaseUtil.formatTimestamp(model.getTimestamp()));

                holder.itemView.setOnClickListener(view -> {
                    UserModel intentUserModel = task.getResult().toObject(UserModel.class);
                    if (intentUserModel != null) {
                        sharedPreferences = context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("TIME_STAMP", intentUserModel.getCreatedTimestamp().toString());
                        editor.putString("USER_NAME", intentUserModel.getUserName());
                        editor.putString("PHONE_NUMBER", intentUserModel.getPhoneNumber());
                        editor.putString("USER_ID", intentUserModel.getUserId());
                        editor.apply();

                        Log.d("TAG", "CurrentSessionsRecyclerAdapter >>> USER_DATA saved to SharedPreferences");

                        Intent intent = new Intent(context, MemberChatActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Log.e("TAG", "UserModel is null, unable to save USER_DATA to SharedPreferences");
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public ChatRoomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.current_sessions_recycler_row, parent, false);
        return new ChatRoomModelViewHolder(view);
    }

    public class ChatRoomModelViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText, recentMessage, recentTimestamp;

        public ChatRoomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.sessions_user_name_text);
            recentMessage = itemView.findViewById(R.id.sessions_last_message_text);
            recentTimestamp = itemView.findViewById(R.id.sessions_timestamp_text);
        }
    }
}
