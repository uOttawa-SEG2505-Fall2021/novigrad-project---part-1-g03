package com.example.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddService extends AppCompatActivity {

    EditText nomService;
    EditText infosRequises;
    EditText docsRequis;
    Button buttonAddService;

    List<Service> services;
    DatabaseReference databaseServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        nomService = (EditText) findViewById(R.id.NomService);
        infosRequises = (EditText) findViewById(R.id.boxInfoRequis);
        docsRequis = (EditText) findViewById(R.id.boxDocuRequis);
        buttonAddService = (Button) findViewById(R.id.ajouter);

        services = new ArrayList<>();

        databaseServices = FirebaseDatabase.getInstance().getReference("services");

        buttonAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addService();
            }
        });
    }

    private void addService() {
        String nom = nomService.getText().toString().trim();
        String infos = infosRequises.getText().toString().trim();
        String docs = docsRequis.getText().toString().trim();

        char[] chars = nom.toCharArray();
        boolean nameContainsDigit = false;

        for(char c: chars){
            if(Character.isDigit(c)){
                nameContainsDigit = true;
                break;
            }
        }
        
        if(nameContainsDigit){
            Toast.makeText(this, "Le nom de service ne devrait pas contenir de chiffres", Toast.LENGTH_LONG).show();
        }
        else if(!TextUtils.isEmpty(nom) && !TextUtils.isEmpty(infos) && !TextUtils.isEmpty(docs)) {
            String id = databaseServices.push().getKey();

            Service service = new Service(nom,infos,docs);

            databaseServices.child(id).setValue(service);

            nomService.setText("");
            infosRequises.setText("");
            docsRequis.setText("");

            Toast.makeText(this, "Service ajouté", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Il y a des champs de textes vides", Toast.LENGTH_LONG).show();
        }
    }

}