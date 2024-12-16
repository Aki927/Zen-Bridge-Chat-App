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
A BugReportModel creates an instance of a user's bug report or feedback from the BugReportActivity.
The report instances are created and stored using the user ID, the message, and the timestamp. This
Firestore Database dataset is important for future scalability and improvements in the app as the app will
scale based on user feedback.
 */
public class BugReportModel {
    String userId, bugReportMessage;
    Timestamp createdTimestamp;

    public BugReportModel() {
    }

    public BugReportModel(String bugReportMessage, Timestamp createdTimestamp, String userId) {
        this.bugReportMessage = bugReportMessage;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
    }

    public String getBugReportMessage() {
        return bugReportMessage;
    }

    public void setBugReportMessage(String bugReportMessage) {
        this.bugReportMessage = bugReportMessage;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
