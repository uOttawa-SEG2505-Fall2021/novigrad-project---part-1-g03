package com.example.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        Service newService = new Service(nom, infos, docs);

        if (Service.verifyService(newService, getApplicationContext())) {
            databaseServices.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    boolean duplicateService = false;

                    //check if there's a duplicate
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Service service = postSnapshot.getValue(Service.class);
                        if (service.getNomService().equalsIgnoreCase(newService.getNomService())) {
                            duplicateService = true;
                        }
                    }

                    if (!duplicateService) {
                        String id = databaseServices.push().getKey();
                        databaseServices.child(id).setValue(newService);

                        nomService.setText("");
                        infosRequises.setText("");
                        docsRequis.setText("");
                        Toast.makeText(AddService.this, "Service ajouté", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddService.this, "Erreur: Ce service existe déjà", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}