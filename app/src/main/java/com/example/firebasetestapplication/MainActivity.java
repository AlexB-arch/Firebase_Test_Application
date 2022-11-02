package com.example.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

    // Current User
    private FirebaseUser currentUser;

    // TODO: Add Firebase Cloud Messaging
    private FirebaseMessaging mFirebaseMessaging; // Firebase Cloud Messaging

    private TextView currentUserTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Current User
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserTextView = findViewById(R.id.current_user_email);
        assert currentUser != null;
        currentUserTextView.setText(currentUser.getEmail());

        // If the user is not logged in, go to the login screen
        if (currentUser == null) {
            startActivity(new Intent(this, EmailPasswordActivity.class));
            finish();
        }

        // Log the user out
        // Views
        Button logOutButton = findViewById(R.id.button_logout);
        logOutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, EmailPasswordActivity.class));
            finish();
        });

    }

    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        currentUser = null;
        startActivity(new Intent(this, EmailPasswordActivity.class));
        finish();
    }
}