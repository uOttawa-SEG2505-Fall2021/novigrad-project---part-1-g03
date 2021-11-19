package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.novigrad.user.ClientAccount;
import com.example.novigrad.user.EmployeeAccount;
import com.example.novigrad.user.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountPage extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference dbRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

    }

    public void returnToLogin(View view) {
        finish();
    }

    // Méthode de Samy qui vérifie si une chaîne de charactères contient des chiffres
    private boolean noNumbers(String str){
        char[] chars = str.toCharArray();
        for(char c : chars){
            if(Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public void onCreateAcc(View view ) {
        String username, password, rePassword, prenom, nom;
        int acctype = -1;
        boolean allInfo = true;
        boolean matchingPass = true;

        EditText usernameET = (EditText) findViewById(R.id.docRequis);
        EditText passwordET = (EditText) findViewById(R.id.motDepasse);
        EditText rePasswordET = (EditText) findViewById(R.id.motDepasse2);
        EditText prenomET = (EditText) findViewById(R.id.NomService);
        EditText nomET = (EditText) findViewById(R.id.infoRequises);
        RadioGroup acctypeRG = (RadioGroup) findViewById(R.id.radioButton_choix);

        username = usernameET.getText().toString();
        password = passwordET.getText().toString();
        rePassword = rePasswordET.getText().toString();
        nom = nomET.getText().toString();
        prenom = prenomET.getText().toString();
        if (username.compareTo("") == 0 || password.compareTo("") == 0 || rePassword.compareTo("") == 0 || nom.compareTo("") == 0 || prenom.compareTo("") == 0) {
            allInfo = false;
        }


        int choice = acctypeRG.getCheckedRadioButtonId();
        if (choice == R.id.radio_button_client) {
            acctype = 0;
        } else if (choice == R.id.radio_button_empoyee) {
            acctype = 1;
        } else {
            allInfo = false;
        }

        if (password.compareTo(rePassword) != 0) {
            matchingPass = false;
        }

        //to use inside class
        int finalAcctype = acctype;

        if (!allInfo) {
            // missing text fields
            Toast.makeText(getApplicationContext(), "Il y a des champs de textes manquants", Toast.LENGTH_LONG).show();
            return;
        } else if (!matchingPass) {
            //password does not match
            Toast.makeText(getApplicationContext(), "Les deux mots de passe ne correspondent pas", Toast.LENGTH_LONG).show();
            return;
        } else if(!noNumbers(prenom) || !noNumbers(nom)){
            Toast.makeText(getApplicationContext(), "Le nom et prénom ne doivent pas contenir des chiffres", Toast.LENGTH_LONG).show();
            return;
        }

        dbRef.child("users").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> snapshot) {
                if (!snapshot.getResult().exists()) {
                    //figure out good way of doing id
                    UserAccount user;
                    if (finalAcctype == 0) {
                        user = new ClientAccount(0, prenom, nom, username, password);
                    } else {
                        user = new EmployeeAccount(0, prenom, nom, username, password);
                    }

                    dbRef.child("users").child(username).setValue(user);
                    Intent myIntent = new Intent(CreateAccountPage.this, LoginPage.class);
                    myIntent.putExtra("userId", username);
                    startActivity(myIntent);
                } else {
                    // username already taken
                    Toast.makeText(getApplicationContext(), "Le nom d'utilisateur choisi existe déjà", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}