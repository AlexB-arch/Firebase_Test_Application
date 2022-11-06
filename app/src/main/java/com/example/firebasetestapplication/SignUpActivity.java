package com.example.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    // TODO: Add Firebase Authentication
    private FirebaseAuth mAuth; // Firebase Authentication

    // TODO: Add Firebase Realtime Database
    private FirebaseDatabase mDatabase; // Firebase Realtime Database

    private Button submitButton;
    private EditText email;
    private EditText password;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Widgets
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        cancelButton = findViewById(R.id.button_cancel);
        submitButton = findViewById(R.id.button_submit);

        // TODO: Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance(); // Firebase Authentication

        // TODO: Initialize Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance(); // Firebase Realtime Database

        // TODO: Add a click listener to the sign up button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add new user to Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(SignUpActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Cancel button takes you back to the log in page
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, EmailPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
        // TODO: Create a new user in Firebase Realtime Database
        // TODO: Launch the main activity
        // TODO: Handle errors

    private boolean validateForm() {
        boolean valid = true;

        email.getText().toString();
        if (TextUtils.isEmpty((CharSequence) email)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        password.getText().toString();
        if (TextUtils.isEmpty((CharSequence) password)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        // If the user is not null, launch the main activity
        if (user != null) {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        }
    }

}