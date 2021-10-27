package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.novigrad.user.UserAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomePage extends AppCompatActivity {

    private int accountType;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference dbRef = database.getReference();
    private UserAccount user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Intent intent = getIntent();
        String username = intent.getStringExtra("userId");

        TextView welcomeMessage = findViewById(R.id.welcomeMessage);

        dbRef.child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(UserAccount.class);
                String acctype;

                assert user != null;
                if(user.getAccountType() == 0) {
                    acctype = "client";
                }
                else if (user.getAccountType() == 1) {
                    acctype = "employé";
                }
                else if (user.getAccountType() == 2) {
                    acctype = "administrateur";
                } else {
                    welcomeMessage.setText(R.string.badAccountTypeFoundErrorText);
                    return;
                }

                welcomeMessage.setText(String.format(getString(R.string.loginSuccess), user.getPrenom(), acctype));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}