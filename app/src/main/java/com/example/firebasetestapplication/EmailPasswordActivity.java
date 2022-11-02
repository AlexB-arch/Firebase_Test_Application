package com.example.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class EmailPasswordActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";

    // TODO: Add Firebase Authentication
    private FirebaseAuth mAuth; // Firebase Authentication

    private EditText email;
    private EditText password;
    Button logInButton = findViewById(R.id.button_login);
    Button signUpButton = findViewById(R.id.button_signup);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        // TODO: Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance(); // Firebase Authentication

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn(email.getText().toString(), password.getText().toString());
            }
        });

        // Takes you to the sign up page
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailPasswordActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void logIn(String toString, String toString1) {
        mAuth.signInWithEmailAndPassword(toString, toString1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(EmailPasswordActivity.this, R.string.login_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(EmailPasswordActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = this.email.getText().toString();
        if (email.isEmpty()) {
            this.email.setError("Required.");
            valid = false;
        } else {
            this.email.setError(null);
        }

        String password = this.password.getText().toString();
        if (password.isEmpty()) {
            this.password.setError("Required.");
            valid = false;
        } else {
            this.password.setError(null);
        }

        return valid;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void signUpClick(View view) {
        // Launch the sign up activity
        startActivity(new Intent(EmailPasswordActivity.this, SignUpActivity.class));
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(EmailPasswordActivity.this, MainActivity.class));
            finish();
        }
    }
}