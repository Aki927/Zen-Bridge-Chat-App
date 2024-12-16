package com.cs.zenbridgechat.model;

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

import com.google.firebase.Timestamp;

/*
ChatMessageModel is used to set up a FirestoreRecyclerOptions object of type ChatMessageModel.
This model is what creates the chat room interface where two users chat with each other by entering
plain text in an EditText, click send, then the the messages are displayed on screen in a descending order
based on timestamp. This follows the standard UX of an actual chatroom or any messaging platform.
 */
public class ChatMessageModel {
    private String message;
    private String senderId;
    private Timestamp timestamp;

    public ChatMessageModel() {
    }

    public ChatMessageModel(String message, String senderId, Timestamp timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ChatMessageModel{" +
                "message='" + message + '\'' +
                ", senderId='" + senderId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
