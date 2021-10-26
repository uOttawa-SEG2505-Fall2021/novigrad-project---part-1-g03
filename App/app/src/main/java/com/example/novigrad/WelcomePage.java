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

    private String username;
    private int accountType;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private UserAccount user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("userId");

        TextView welcomeMessage = findViewById(R.id.welcomeMessage);

        myRef.child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                user = snapshot.getValue(UserAccount.class);
                String acctype;
                if(user.getAccountType() == 0) acctype = "client";
                else if(user.getAccountType() == 1) acctype = "employé";
                else acctype = "administrateur";
                welcomeMessage.setText("Bonjour " + user.getPrenom() +"!\n Bienvenue à Service Novigrad \n Vous êtes connecté en tant que " + acctype + ".");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}