package com.yogdroidtech.mallfirebase.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.yogdroidtech.mallfirebase.R;


public class ProfileFragment extends Fragment {
private TextView userName;
    //
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        userName = view.findViewById(R.id.textView10);
        userName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"");
        return view;
    }
}