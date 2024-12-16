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
import com.cs.zenbridgechat.model.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

// Adapter for creating the list of user(s) that match the phone number based on user search.
public class ContactListRecyclerAdapter extends FirestoreRecyclerAdapter<UserModel, ContactListRecyclerAdapter.UserModelViewHolder> {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ContactListRecyclerAdapter(@NonNull FirestoreRecyclerOptions<UserModel> options, Context c) {
        super(options);
        this.context = c;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder holder, int position, @NonNull UserModel model) {
        holder.usernameText.setText(model.getUserName());
        holder.phoneText.setText(model.getPhoneNumber());

        holder.itemView.setOnClickListener(view -> {
            sharedPreferences = context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();

            editor.putString("TIME_STAMP", model.getCreatedTimestamp().toString());
            editor.putString("USER_NAME", model.getUserName());
            editor.putString("PHONE_NUMBER", model.getPhoneNumber());
            editor.putString("USER_ID", model.getUserId());

            Log.d("TAG", "######## ContactListRecyclerAdapter TIME_STAMP set in SharedPreferences >>> " + model.getCreatedTimestamp());
            Log.d("TAG", "######## ContactListRecyclerAdapter PHONE_NUMBER set in SharedPreferences >>> " + model.getPhoneNumber());
            Log.d("TAG", "######## ContactListRecyclerAdapter USER__NAME set in SharedPreferences >>> " + model.getUserName());
            Log.d("TAG", "######## ContactListRecyclerAdapter USER_ID set in SharedPreferences >>> " + model.getUserId());

            editor.apply();

            Intent intent = new Intent(context, MemberChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_recycler_row, parent, false);
        return new UserModelViewHolder(view);
    }

    public class UserModelViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText, phoneText, userId;

        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            phoneText = itemView.findViewById(R.id.phone_number_text);
        }
    }
}
