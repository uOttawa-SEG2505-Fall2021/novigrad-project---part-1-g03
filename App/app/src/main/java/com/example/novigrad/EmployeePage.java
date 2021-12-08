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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Classe EmployeePage qui représente le landing page lorsqu'on se connecte en tant qu'employé
 * */

public class EmployeePage extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference dbRef = database.getReference();
    private UserAccount user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        Intent intent = getIntent();
        String username = intent.getStringExtra("userId");

        TextView welcomeMessage = findViewById(R.id.welcomeEmploye);

        dbRef.child("users").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> snapshot) {
                if (snapshot.getResult().exists()) {

                user = snapshot.getResult().getValue(UserAccount.class);
                welcomeMessage.setText(String.format(getString(R.string.bonjour), user.getPrenom()));
                } else {
                    //some sort of error handling here
                }
            }
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
        //put extra values to get succursale's info, if needed
        changeTimeIntent.putExtra("succursaleName", user.getNomDeUtilisateur());
        startActivity(changeTimeIntent);
    }

    public void onDemandeServices(View view) {
        Intent demandeServicesIntent = new Intent(EmployeePage.this, SuccursaleDemandsPage.class);
        demandeServicesIntent.putExtra("succursaleName", user.getNomDeUtilisateur());
        startActivity(demandeServicesIntent);
    }

    public void onDetailsSuccursale(View view) {
        Intent detailsIntent = new Intent(EmployeePage.this, SuccursaleDetailsPage.class);
        detailsIntent.putExtra("succursaleName", user.getNomDeUtilisateur());
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