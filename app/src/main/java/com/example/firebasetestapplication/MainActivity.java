package com.example.firebasetestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // Current User
    private FirebaseUser currentUser;

    // TODO: Add Firebase Storage
    private FirebaseStorage mFirebaseStorage; // Firebase Storage

    // TODO: Add Firebase Realtime Database
    private FirebaseDatabase mFirebaseDatabase; // Firebase Realtime Database




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
            return;
        } else {
            // Get current user
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            displayChatMessages();
        }

        // Push a message to the Firebase Realtime Database
        fab.setOnClickListener(view -> {
            EditText input = findViewById(R.id.input);
            FirebaseDatabase.getInstance()
                    .getReference()
                    .push()
                    .setValue(new ChatMessages(input.getText().toString(),
                            Objects.requireNonNull(FirebaseAuth.getInstance()
                                            .getCurrentUser())
                                    .getDisplayName())
                    );
            input.setText("");
        });

    }

    private void displayChatMessages() {

    }

}