package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.novigrad.user.UserAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Classe AdminPage représente le landing page lorsqu'on se connecte en tant qu'administrateur
 * */

public class AdminPage extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference dbRef = database.getReference();
    private UserAccount user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Intent intent = getIntent();
        String username = intent.getStringExtra("userId");

        TextView welcomeMessage = findViewById(R.id.welcomeAdmin);
        LinearLayout ServiceBtn = findViewById(R.id.ServiceBtn);
        LinearLayout AccountBtn = findViewById(R.id.AccountBtn);

        dbRef.child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(UserAccount.class);

                welcomeMessage.setText(String.format(getString(R.string.welcomeAdmin), user.getPrenom()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        ServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), ServicesPage.class);
                startActivityForResult(intent, 0);
            }
        });

        AccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), AccountDeletionPage.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    // Déconnecte l'utilisateur
    public void onLogout(View view){
        Intent myIntent = new Intent(AdminPage.this, LoginPage.class);
        myIntent.putExtra("username", "");
        myIntent.putExtra("password", "");
        UserAccount.unsetUserInstance();
        startActivity(myIntent);
    }


}
