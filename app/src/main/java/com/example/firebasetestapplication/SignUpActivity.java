package com.example.firebasetestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    // TODO: Add Firebase Authentication
    private FirebaseAuth mAuth; // Firebase Authentication

    // TODO: Add Firebase Realtime Database
    private FirebaseDatabase mDatabase; // Firebase Realtime Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}