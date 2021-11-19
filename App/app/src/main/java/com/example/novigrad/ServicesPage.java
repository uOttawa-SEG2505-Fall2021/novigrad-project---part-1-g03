package com.example.novigrad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServicesPage extends AppCompatActivity {

    ListView listViewServices;
    List<Service> services;
    DatabaseReference databaseServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        listViewServices = (ListView) findViewById(R.id.listViewServices);
        services = new ArrayList<>();

        databaseServices = FirebaseDatabase.getInstance().getReference("services");
    }

    public void onAddService(View view){
        Intent intent = new Intent(getApplicationContext(), AddService.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                services.clear();
                ArrayList<String> serviceIds = new ArrayList<>();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    serviceIds.add(postSnapshot.getKey());
                    services.add(service);
                }
                ServiceList servicesAdapter = new ServiceList(ServicesPage.this, services);
                listViewServices.setAdapter(servicesAdapter);
                listViewServices.setLongClickable(true);
                listViewServices.setClickable(true);

                listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent myIntent = new Intent(ServicesPage.this, ModifyServicePage.class);
                        Service currentService = services.get(position);
                        myIntent.putExtra("serviceName", currentService.getNomService());
                        myIntent.putExtra("serviceDocs", currentService.getDocsRequis());
                        myIntent.putExtra("serviceInfo", currentService.getInfosRequises());
                        myIntent.putExtra("serviceId", serviceIds.get(position));
                        startActivityForResult(myIntent, 0);
                        return true;
                    }
                });

                listViewServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent myIntent = new Intent(ServicesPage.this, ServiceDetails.class);
                        Service currentService = services.get(position);
                        myIntent.putExtra("serviceName", currentService.getNomService());
                        myIntent.putExtra("serviceDocs", currentService.getDocsRequis());
                        myIntent.putExtra("serviceInfo", currentService.getInfosRequises());
                        myIntent.putExtra("serviceId", serviceIds.get(position));
                        startActivityForResult(myIntent, 0);
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