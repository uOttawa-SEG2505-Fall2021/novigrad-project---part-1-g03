package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe AllServicesAvailPage qui nous permet de visualiser tous les services offerts par Service Novigrad
 * */

public class AllServicesAvailPage extends AppCompatActivity {

    ListView listViewServices;
    List<Service> services;
    DatabaseReference databaseServices;
    String succursaleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_services_avail_page);

        listViewServices = (ListView) findViewById(R.id.listViewServicesDispo);
        services = new ArrayList<>();

        databaseServices = FirebaseDatabase.getInstance().getReference("services");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                succursaleName = extras.getString("succursaleName");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference databaseSuccursale = FirebaseDatabase.getInstance().getReference("succursales").child(succursaleName);

        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                services.clear();
                ArrayList<String> serviceIds = new ArrayList<>();

                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    serviceIds.add(postSnapshot.getKey());
                    services.add(service);
                }
                ServiceList servicesAdapter = new ServiceList(AllServicesAvailPage.this, services);
                listViewServices.setAdapter(servicesAdapter);
                listViewServices.setLongClickable(true);
                listViewServices.setClickable(true);

                listViewServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent myIntent = new Intent(AllServicesAvailPage.this, ServiceDetailsPage.class);
                        Service currentService = services.get(position);
                        myIntent.putExtra("serviceName", currentService.getNomService());
                        myIntent.putExtra("serviceDocs", currentService.getDocsRequis());
                        myIntent.putExtra("serviceInfo", currentService.getInfosRequises());
                        myIntent.putExtra("serviceId", serviceIds.get(position));
                        startActivityForResult(myIntent, 0);
                    }
                });

                listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        databaseSuccursale.child("servicesFournis").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> snapshot) {

                                boolean duplicateServiceRef = false;

                                for(DataSnapshot postSnapshot : snapshot.getResult().getChildren()) {
                                    String serviceRef = postSnapshot.getValue(String.class);
                                    if (serviceRef.equals(serviceIds.get(position))) {
                                        duplicateServiceRef = true;
                                    }
                                }

                                if (!duplicateServiceRef) {
                                    databaseSuccursale.child("servicesFournis").push().setValue(serviceIds.get(position));
                                    Toast.makeText(AllServicesAvailPage.this, "Service ajouté", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(AllServicesAvailPage.this, "Erreur: Ce service existe déjà", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        return true;

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onReturn(View view){
        finish();
    }
}