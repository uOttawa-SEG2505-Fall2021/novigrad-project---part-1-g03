package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.novigrad.user.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccount extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void onCreateAcc(View view) {
        String username, password, rePassword, prenom, nom;
        int acctype = -1;
        boolean allInfo = true;
        boolean matchingPass = true;
        final boolean[] usernameAvail = { true };

        EditText usernameET = (EditText) findViewById(R.id.nomUtilisateur);
        EditText passwordET = (EditText) findViewById(R.id.motDepasse);
        EditText rePasswordET = (EditText) findViewById(R.id.motDepasse2);
        EditText prenomET = (EditText) findViewById(R.id.prenom);
        EditText nomET = (EditText) findViewById(R.id.nomDeFamille);
        RadioGroup acctypeRG = (RadioGroup) findViewById(R.id.radioButton_choix);

        username = usernameET.getText().toString();
        if(username.compareTo("") == 0) allInfo = false;
        password = passwordET.getText().toString();
        if(password.compareTo("") == 0) allInfo = false;
        rePassword = rePasswordET.getText().toString();
        if(rePassword.compareTo("") == 0) allInfo = false;
        nom = nomET.getText().toString();
        if(nom.compareTo("") == 0) allInfo = false;
        prenom = prenomET.getText().toString();
        if(prenom.compareTo("") == 0) allInfo = false;

        int choice = acctypeRG.getCheckedRadioButtonId();
        if(choice == R.id.radio_button_client) acctype = 0;
        else if (choice == R.id.radio_button_empoyee) acctype = 1;
        else allInfo = false;

        if(password.compareTo(rePassword) != 0) matchingPass = false;

        myRef.child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    usernameAvail[0] = false;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        if(allInfo && matchingPass && usernameAvail[0]) {
            //figure out good way of doing id
            UserAccount user;
            if(acctype == 0) {
                user = new ClientAccount(0, prenom, nom, username, password);
            } else {
                user = new EmployeeAccount(0, prenom, nom, username, password);
            }

            myRef.child("users").child(username).setValue(user);
            Intent myIntent = new Intent(CreateAccount.this, WelcomePage.class);
            startActivity(myIntent);

        } else {
            Intent myIntent = new Intent(CreateAccount.this, MainActivity.class);
            startActivity(myIntent);
        }
    }
}