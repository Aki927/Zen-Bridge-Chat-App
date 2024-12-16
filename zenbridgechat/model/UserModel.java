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
UserModel is a user record stored in USERS dataset in Firestore Database. Each UserModel object
is instantiated with a user name, phone number, user ID, recent message, and timestamp when created.
 */

public class UserModel {
    String userName;
    String phoneNumber;
    String userId;
    String recentMessageString;
    Timestamp createdTimestamp;

    public UserModel() {
    }

    public UserModel(Timestamp createdTimestamp, String phoneNumber, String userName, String userId) {
        this.createdTimestamp = createdTimestamp;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.userId = userId;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getRecentMessageString() { return recentMessageString; }

    public void setRecentMessageString(String recentMessageString) { this.recentMessageString = recentMessageString; }
}
