package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novigrad.user.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SuccursaleDetailsPage extends AppCompatActivity {

    //succ details
    private String succName;
    private String succAdresse;

    private DatabaseReference dbSucc;

    //UI elements
    private EditText nameText;
    private EditText adresseText;
    private TextView headerText;
    private TextView adresseLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succursale_details_page);


        //db variables
        String succursaleAccountName = UserAccount.getUserInstance().getNomDeUtilisateur();
        dbSucc = FirebaseDatabase.getInstance().getReference("succursales").child(succursaleAccountName);

        nameText = findViewById(R.id.newNameSucc);
        headerText = findViewById(R.id.succName);
        adresseText = findViewById(R.id.newAdresseSucc);
        adresseLabel = findViewById(R.id.adresseLabel);

        //auto-updates the name field
        dbSucc.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                succName = snapshot.getValue(String.class);
                headerText.setText(String.format(getString(R.string.succursale_nom), succName));
                nameText.setText(succName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //auto-updates address field
        dbSucc.child("adresse").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                succAdresse = snapshot.getValue(String.class);
                adresseLabel.setText(String.format(getString(R.string.adresseSucc), succAdresse));
                adresseText.setText(succAdresse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Retourne à la page précédente
    public void onNameChange(View view){
        changeSuccField(nameText, succName, "name", "Erreur: Un succursale avec cette nom existe déjà", "Le nom du succursale a été modifié");
    }

    public void onAdresseChange(View view) {
        changeSuccField(adresseText, succAdresse, "adresse", "Erreur: Un succursale déjà possède cette adresse", "L'adresse a été modifié");
    }


    private void changeSuccField(EditText field, String current, String dbField, String errorMsg, String successMsg) {
        final String newField = field.getText().toString().trim();
        if (newField.equals(current)) {
            //error message here optional, but should be obvious to the user what they're doing
            return;
        }

        dbSucc.getParent().get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> allSuccsShot) {

                boolean duplicateVal = false;

                for (DataSnapshot snapshot:
                        allSuccsShot.getResult().getChildren()) {
                    String succField = snapshot.child(dbField).getValue(String.class);
                    if (succField.equalsIgnoreCase(newField)) {
                        duplicateVal = true;
                    }
                }

                if (duplicateVal) {
                    Toast.makeText(SuccursaleDetailsPage.this, errorMsg, Toast.LENGTH_SHORT).show();
                } else {
                    dbSucc.child(dbField).setValue(newField);
                    Toast.makeText(SuccursaleDetailsPage.this, successMsg, Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    // Retourne à la page précédente
    public void onReturn(View view){
        finish();
    }
}