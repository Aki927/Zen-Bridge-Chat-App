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

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs.zenbridgechat.model.UserModel;
import com.cs.zenbridgechat.utils.FirebaseUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    EditText changeNameInput;
    Button changeNameButton;
    UserModel userModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // User can change their username
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        changeNameInput = view.findViewById(R.id.change_name_input);
        changeNameButton = view.findViewById(R.id.change_name_button);

        /*
        A user enters the name in the EditText they would like to change their username to, then click
        change name button. The username is validated to ensure it is at least 3 characters long.
        The UserModel is updated and sets the user's new username.
         */
        changeNameButton.setOnClickListener(view1 -> {
            String name = changeNameInput.getText().toString();
            int minLength = 3;
            if (name.length() < minLength) {
                Toast.makeText(SettingsFragment.this.getActivity(), "Name must be at least 3 characters long!",
                        Toast.LENGTH_SHORT).show();
            }
            userModel.setUserName(name);
            FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(SettingsFragment.this.getActivity(), "Username change complete!", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "SettingsFragment changeNameButtonClicked >>> name change to: " + name);
                }
            });
        });

        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            userModel = task.getResult().toObject(UserModel.class);
            changeNameInput.setText(userModel.getUserName());
        });
        return view;
    }


}