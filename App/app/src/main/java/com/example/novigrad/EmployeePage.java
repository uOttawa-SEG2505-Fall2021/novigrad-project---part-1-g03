package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.novigrad.user.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Classe EmployeePage qui représente le landing page lorsqu'on se connecte en tant qu'employé
 * */

public class EmployeePage extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference dbRef = database.getReference();
    private UserAccount user;
    private String succursaleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        TextView welcomeMessage = findViewById(R.id.welcomeEmploye);

        user = UserAccount.getUserInstance();
        welcomeMessage.setText(String.format(getString(R.string.bonjour), user.getPrenom()));


    }

    @Override
    protected void onStart() {
        super.onStart();

        dbRef.child("succursales").child(user.getNomDeUtilisateur()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                succursaleName = snapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public void onServicesFournis(View view) {
        Intent servicesFournisIntent = new Intent(EmployeePage.this, ServicesFournisPage.class);
        //put extra values to get succursale's service info, if needed
        servicesFournisIntent.putExtra("succursaleName", user.getNomDeUtilisateur()); //nomDeUtiliseur is the same as the succursale name
        startActivity(servicesFournisIntent);
    }

    public void onChangeTime(View view) {
        Intent changeTimeIntent = new Intent(EmployeePage.this, SuccursaleTimePage.class);
        startActivity(changeTimeIntent);
    }

    public void onDemandeServices(View view) {
        Intent demandeServicesIntent = new Intent(EmployeePage.this, SuccursaleDemandsPage.class);
        demandeServicesIntent.putExtra("succursaleName", succursaleName);
        startActivity(demandeServicesIntent);
    }

    public void onDetailsSuccursale(View view) {
        Intent detailsIntent = new Intent(EmployeePage.this, SuccursaleDetailsPage.class);
        startActivity(detailsIntent);
    }

    // Déconnecte l'utilisateur
    public void onLogout(View view){
        Intent myIntent = new Intent(EmployeePage.this, LoginPage.class);
        myIntent.putExtra("username", "");
        myIntent.putExtra("password", "");
        UserAccount.unsetUserInstance();
        startActivity(myIntent);
    }
}