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
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs.zenbridgechat.R;
import com.cs.zenbridgechat.model.ChatMessageModel;
import com.cs.zenbridgechat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

// Adapter for creating the chat messaging interface between two users.
public class SessionRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel, SessionRecyclerAdapter.ChatModelViewHolder> {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SessionRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options, Context c) {
        super(options);
        this.context = c;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position, @NonNull ChatMessageModel model) {
        if (model.getSenderId().equals(FirebaseUtil.currentUserId())) {
            holder.receiverLayout.setVisibility(View.GONE);
            holder.senderLayout.setVisibility(View.VISIBLE);
            holder.senderTextView.setText(model.getMessage());
        } else {
            holder.senderLayout.setVisibility(View.GONE);
            holder.receiverLayout.setVisibility(View.VISIBLE);
            holder.receiverTextView.setText(model.getMessage());
        }
    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_recycler_row, parent, false);
        return new ChatModelViewHolder(view);
    }

    public class ChatModelViewHolder extends RecyclerView.ViewHolder {
        TextView receiverTextView;
        TextView senderTextView;
        LinearLayout receiverLayout;
        LinearLayout senderLayout;

        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverLayout = itemView.findViewById(R.id.receiver_layout);
            senderLayout = itemView.findViewById(R.id.sender_layout);
            receiverTextView = itemView.findViewById(R.id.receiver_textview);
            senderTextView = itemView.findViewById(R.id.sender_textview);
        }
    }
}
