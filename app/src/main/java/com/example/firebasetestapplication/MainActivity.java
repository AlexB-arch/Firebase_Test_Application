package com.example.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // TODO: Add Firebase Cloud Messaging
    private FirebaseMessaging mFirebaseMessaging; // Firebase Cloud Messaging


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If the user is not logged in, go to the login screen
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, EmailPasswordActivity.class));
            finish();
            return;
        }
    }
}