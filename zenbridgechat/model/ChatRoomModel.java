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

import java.util.List;

/*
ChatRoomModel is an instance in the CHAT_ROOMS dataset in Firestore Database.
Each ChatRoomModel is unique and keeps track of a unique chatroom between two people indefinitely.
Each ChatRoomModel is instantiated with a timestamp, chat room ID, last message sender's ID, their last
message, and the two user IDs that create the unique chatroom ID.
 */

public class ChatRoomModel {
    Timestamp timestamp;
    String chatroomId, lastMessageSenderId, lastMessage;
    List<String> userIds;

    public ChatRoomModel() {
    }

    public ChatRoomModel(String chatroomId, String lastMessageSenderId, Timestamp timestamp, List<String> userIds) {
        this.chatroomId = chatroomId;
        this.lastMessageSenderId = lastMessageSenderId;
        this.timestamp = timestamp;
        this.userIds = userIds;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
