package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SuccursaleDetailsPage extends AppCompatActivity {

    private String dbSuccursaleName;
    private String succName;
    private DatabaseReference dbSucc;

    private EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succursale_details_page);



        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras != null){
                dbSuccursaleName = extras.getString("succursaleName");
            } else {
                //some sort of error handling here ig?
                finish();
            }
        }

        nameText = findViewById(R.id.newNameSucc);
        dbSucc = FirebaseDatabase.getInstance().getReference("succursales").child(dbSuccursaleName);

        //this auto-updates the name field
        dbSucc.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                succName = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Retourne à la page précédente
    public void onNameChange(View view){
        final String newName = nameText.getText().toString().trim();
        if (newName.equals(succName)) {
            //error message here optional, but should be obvious to the user what they're doing
            return;
        }

        dbSucc.getParent().get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> allSuccsShot) {

                boolean duplicateSucc = false;

                for (DataSnapshot snapshot:
                     allSuccsShot.getResult().getChildren()) {
                    String succName = snapshot.child("name").getValue(String.class);
                    if (succName.equalsIgnoreCase(newName)) {
                        duplicateSucc = true;
                    }
                }

                if (duplicateSucc) {
                    Toast.makeText(SuccursaleDetailsPage.this, "Erreur: Ce succursale existe déjà", Toast.LENGTH_SHORT).show();
                } else {
                    dbSucc.child("name").setValue(newName);
                    Toast.makeText(SuccursaleDetailsPage.this, "Nom de succursale modifié", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    // Retourne à la page précédente
    public void onReturn(View view){
        finish();
    }
}