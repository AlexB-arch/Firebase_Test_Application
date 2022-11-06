package com.example.firebasetestapplication;

import static com.google.firebase.auth.FirebaseAuth.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.view.View;
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

    private FirebaseListAdapter<ChatMessages> adapter;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        FirebaseListAdapter<ChatMessages> adapter;

        if (getInstance().getCurrentUser() == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
            return;
        } else {
            // Get current user
            currentUser = getInstance().getCurrentUser();
            displayChatMessages();
        }

        // Push a message to the Firebase Realtime Database
        fab.setOnClickListener(view -> {
            EditText input = findViewById(R.id.input);
            FirebaseDatabase.getInstance()
                    .getReference()
                    .push()
                    .setValue(new ChatMessages(input.getText().toString(),
                            Objects.requireNonNull(getInstance()
                                            .getCurrentUser())
                                    .getDisplayName())
                    );
            input.setText("");
        });

    }

    private void displayChatMessages() {
        ListView listOfMessages = findViewById(R.id.list_of_messages);

        FirebaseListOptions<ChatMessages> options = new FirebaseListOptions.Builder<ChatMessages>()
                .setQuery(FirebaseDatabase.getInstance().getReference(), ChatMessages.class).setLayout(R.layout.messages).build();

        adapter = new FirebaseListAdapter<ChatMessages>(options) {
            @Override
            protected void populateView(@NonNull android.view.View v, @NonNull ChatMessages model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (currentUser != null) {
            adapter.stopListening();
        }
    }
}