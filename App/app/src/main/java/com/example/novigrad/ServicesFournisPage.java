package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.novigrad.user.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe ServiceFournisPage qui nous permet de visualiser les services fournis par la succursale
 * */

public class ServicesFournisPage extends AppCompatActivity {

    ListView listViewServicesFournis;
    List<Service> services;
    HashMap<Service, String> serviceToServiceId;
    DatabaseReference databaseServices = FirebaseDatabase.getInstance().getReference("services");
    DatabaseReference dbSucc;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_fournis_page);

        listViewServicesFournis = findViewById(R.id.listViewServicesFournis);
        message = (TextView) findViewById(R.id.details);

        //db variables
        String succursaleAccountName = UserAccount.getUserInstance().getNomDeUtilisateur();
        dbSucc = FirebaseDatabase.getInstance().getReference("succursales").child(succursaleAccountName);
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbSucc.child("servicesFournis").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot succSnapshot) {
                ArrayList<String> serviceIds = new ArrayList<>();

                //contains a serviceId and the reference to where that id is stored within
                // the succursale's "servicesFournis" table
                HashMap<String, String> serviceIdToServiceRefRef = new HashMap<>();

                for(DataSnapshot postSnapshot : succSnapshot.getChildren()) {
                    //children of this node have keys to service IDs
                    serviceIds.add(postSnapshot.getValue(String.class));
                    serviceIdToServiceRefRef.put(postSnapshot.getValue(String.class), postSnapshot.getKey());
                }

                //read from the service database
                databaseServices.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot servicesSnapshot) {
                        services = new ArrayList<>();
                        serviceToServiceId = new HashMap<>();

                        for(DataSnapshot postSnapshot : servicesSnapshot.getChildren()) {
                            Service service = postSnapshot.getValue(Service.class);

                            //if the service is one of the services offered for this succursale, add it
                            if (serviceIds.contains(postSnapshot.getKey())) {
                                services.add(service);
                                serviceToServiceId.put(service, postSnapshot.getKey());
                            }
                        }

                        ServiceList servicesAdapter = new ServiceList(ServicesFournisPage.this, services);
                        listViewServicesFournis.setAdapter(servicesAdapter);
                        if (services.size() == 0) {
                            message.setText(R.string.errorMessageServices);
                        } else {
                            message.setText(R.string.infoServiceFournis);
                        }
                        listViewServicesFournis.setLongClickable(true);
                        listViewServicesFournis.setClickable(true);

                        listViewServicesFournis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent myIntent = new Intent(ServicesFournisPage.this, ServiceDetailsPage.class);
                                Service currentService = services.get(position);
                                myIntent.putExtra("serviceName", currentService.getNomService());
                                myIntent.putExtra("serviceDocs", currentService.getDocsRequis());
                                myIntent.putExtra("serviceInfo", currentService.getInfosRequises());
                                myIntent.putExtra("serviceId", serviceIds.get(position));
                                startActivityForResult(myIntent, 0);
                            }
                        });

                        listViewServicesFournis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                                AlertDialog.Builder alert = new AlertDialog.Builder(ServicesFournisPage.this);
                                alert.setTitle("Attention!!");
                                alert.setMessage("Êtes-vous sûr de vouloir supprimer ce service de la liste de services fournis?");
                                alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //delete serviceRef here

                                        dbSucc.child("servicesFournis").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DataSnapshot> succSnapshot) {
                                                String serviceRefToDelete = serviceToServiceId.get(services.get(position));
                                                for(DataSnapshot postSnapshot : succSnapshot.getResult().getChildren()) {
                                                    String serviceRef = postSnapshot.getValue(String.class);

                                                    //if the serviceRef matches that of the service that was clicked...
                                                    if (serviceRefToDelete.equals(serviceRef)) {
                                                        //remove it
                                                        dbSucc.child("servicesFournis").child(serviceIdToServiceRefRef.get(serviceRefToDelete)).removeValue();
                                                        dialog.dismiss();
                                                    }
                                                }
                                            }
                                        });

                                    }
                                });
                                alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                                return true;
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void onSeeAll(View view){
        Intent intent = new Intent(getApplicationContext(), AllServicesAvailPage.class);
        startActivityForResult(intent, 0);
    }

    public void onReturn(View view){
        finish();
    }
}