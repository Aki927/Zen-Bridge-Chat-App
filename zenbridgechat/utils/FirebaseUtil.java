package com.cs.zenbridgechat.utils;

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

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

// This is where all users are stored
public class FirebaseUtil {

    // Returns UID
    public static String currentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    // Returns a CollectionReference of all users in the USERS dataset in Firestore Database
    public static CollectionReference allUserCollectionReference() {
        return FirebaseFirestore.getInstance().collection("USERS");
    }

    // Returns a DocumentReference of the current user's instance in the USERS dataset in Firestore Database
    public static DocumentReference currentUserDetails() {
        return FirebaseFirestore.getInstance().collection("USERS").document(currentUserId());
    }

    // Format the timestamps
    public static String formatTimestamp(Timestamp t) {
        return new SimpleDateFormat("HH:mm", Locale.CANADA).format(t.toDate());
    }

    // Return true if user is already logged in and false otherwise. Used for a case where user should stay logged in.
    public static boolean isLoggedIn() {
        return currentUserId() != null;
    }

    // Returns a DocumentReference of a the instance of an existing chatroom in the CHAT_ROOMS dataset
    public static DocumentReference getChatRoomReference(String chatroomId) {
        return FirebaseFirestore.getInstance().collection("CHAT_ROOMS").document(chatroomId);
    }

    // Creates a unique chat room ID between two users.
    public static String getChatRoomId(String u1, String u2) {
        return u1.hashCode() < u2.hashCode() ? u1 + "_" + u2 : u2 + "_" + u1;
    }

    /*
    Returns a DocumentReference for a user's details based on a list of user IDs.
    a List of type string is passed as a parameter of user IDs where the current user is included.
    The List contains two user IDs. One for the current user and another for the other session user.
     */
    public static DocumentReference getSessionUsers(List<String> uid) {
        if (uid.get(0).equals(FirebaseUtil.currentUserId())) {
            return allUserCollectionReference().document(uid.get(1));
        } else {
            return allUserCollectionReference().document(uid.get(0));
        }
    }

    /*
    Returns a CollectionReference of all the instances of the SESSIONS dataset in Firestore Database.
    A Log.d is used for testing to count the sessions to ensure the method performs correct task.
     */
    public static CollectionReference getChatRoomMessageReference(String id) {
        Log.d("TAG", "" + getChatRoomReference(id).collection("SESSIONS").count());
        return getChatRoomReference(id).collection("SESSIONS");
    }

    // Returns a CollectionReference of all the instances of the CHAT_ROOMS dataset in Firestore Database.
    public static CollectionReference allChatRoomCollectionReference(){
        return FirebaseFirestore.getInstance().collection("CHAT_ROOMS");
    }

    // Returns a CollectionReference of all the instances of the BUG_REPORTS dataset in Firestore Database.
    public static CollectionReference allBugReportCollectionReference() {
        return FirebaseFirestore.getInstance().collection("BUG_REPORTS");
    }


}
