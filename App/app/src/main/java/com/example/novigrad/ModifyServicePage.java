package com.example.novigrad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novigrad.user.UserAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModifyServicePage extends AppCompatActivity {

    private String serviceName;
    private String serviceDocs;
    private String serviceInfo;
    private String serviceId;

    private EditText serviceNameEdit ;
    private EditText serviceDocsEdit ;
    private EditText serviceInfoEdit ;

    private DatabaseReference databaseServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_service_page);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                serviceName = extras.getString("serviceName");
                serviceDocs = extras.getString("serviceDocs");
                serviceInfo = extras.getString("serviceInfo");
                serviceId = extras.getString("serviceId");
            }
        }

        serviceNameEdit = findViewById(R.id.serviceNameEdit);
        serviceDocsEdit = findViewById(R.id.serviceDocsEdit);
        serviceInfoEdit = findViewById(R.id.serviceInfoEdit);
        serviceNameEdit.setText(serviceName);
        serviceDocsEdit.setText(serviceDocs);
        serviceInfoEdit.setText(serviceInfo);

        //for testing and seeing the service ID
        //TextView serviceIdView = findViewById(R.id.serviceIdView);
        //serviceIdView.setText("Service Id: " + serviceId);

        databaseServices = FirebaseDatabase.getInstance().getReference("services");

    }

    public void onModify(View view) {
        String nom = serviceNameEdit.getText().toString().trim();
        String docs = serviceDocsEdit.getText().toString().trim();
        String infos = serviceInfoEdit.getText().toString().trim();

        Service updatedService = new Service(nom, infos, docs);
        if (Service.verifyService(updatedService, getApplicationContext())) {

            databaseServices.addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {

                    boolean duplicateService = false;

                    //check if there's a duplicate
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Service service = postSnapshot.getValue(Service.class);
                        if (service.getNomService().toLowerCase().equals(updatedService.getNomService().toLowerCase())) {
                            duplicateService = true;
                        }
                    }

                    if (!duplicateService) {

                        Service updatedService = new Service(nom, infos, docs);
                        databaseServices.child(serviceId).setValue(updatedService);


                        Toast.makeText(getApplicationContext(), "Service modifié", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Erreur: Ce service existe déjà", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    public void onDelete(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(ModifyServicePage.this);
        alert.setTitle("Attention!!");
        alert.setMessage("Êtes-vous sûr de vouloir supprimer ce service? Cette action ne peut pas être renversée!");
        alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete account here

                //get account to be deleted
                databaseServices.child(serviceId).removeValue();

                Toast.makeText( getApplicationContext(), "Suppression du service réussi!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                onReturn(view);
            }
        });
        alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();

    }

    public void onReturn(View view) {
        Intent returnToServicesIntent = new Intent(ModifyServicePage.this, ServicesPage.class);
        startActivity(returnToServicesIntent);
    }


}