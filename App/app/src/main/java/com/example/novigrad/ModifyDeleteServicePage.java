package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * ModifyDeleteServicePage class qui permet de modifier ou supprimer un service
 * */
public class ModifyDeleteServicePage extends AppCompatActivity {

    //note that these are the INITIAL values
    private String serviceName;
    private String serviceDocs;
    private String serviceInfo;
    private String serviceId;

    private EditText serviceNameEdit;
    private EditText serviceDocsEdit;
    private EditText serviceInfoEdit;

    private DatabaseReference databaseServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_service_page);

        // Information passée du service page à celle ci (pour savoir quel service affiché)
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                serviceName = extras.getString("serviceName");
                serviceDocs = extras.getString("serviceDocs");
                serviceInfo = extras.getString("serviceInfo");
                serviceId = extras.getString("serviceId");
            } else {
                //Cette code ici devrait jamais s'exécuter
                // S'il exécute, il y a des problèmes avec l'information transmise dans la page précédente

                //assigner des valeurs vides pour éviter les erreurs
                serviceName = "";
                serviceDocs = "";
                serviceInfo = "";
                serviceId = "";

                //retourner à ServicesPage et donner un message d'erreur
                returnWithError("Il y avait un erreur en tentant d'accéder les infos requises. S'il se persiste, SVP contactez les développeurs");
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

    // Exécuter lorsqu'on modifie le service
    public void onModify(View view) {
        String nom = serviceNameEdit.getText().toString().trim();
        String docs = serviceDocsEdit.getText().toString().trim();
        String infos = serviceInfoEdit.getText().toString().trim();

        Service updatedService = new Service(nom, infos, docs);
        if (Service.verifyService(updatedService, getApplicationContext())) {



            databaseServices.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> dataSnapshot) {

                    boolean duplicateService = false;

                    //check if there's a duplicate
                    for(DataSnapshot postSnapshot : dataSnapshot.getResult().getChildren()) {
                        Service service = postSnapshot.getValue(Service.class);
                        //check if the service name isn't being renamed to a service that already exists
                        //note that we don't want to check if the case is the same if the name is the same (second half of the if), in case there's a case change
                        //e.g. "driver's License" changed to "Driver's License" shouldn't raise an error
                        if (service.getNomService().equalsIgnoreCase(updatedService.getNomService()) && !service.getNomService().equals(serviceName)) {
                            duplicateService = true;
                        }
                    }

                    if (!duplicateService) {

                        Service updatedService = new Service(nom, infos, docs);
                        databaseServices.child(serviceId).setValue(updatedService);

                        Toast.makeText(ModifyDeleteServicePage.this, "Service modifié", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(ModifyDeleteServicePage.this, "Erreur: Ce service existe déjà", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // Exécuter lorsqu'on supprime le service
    public void onDelete(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(ModifyDeleteServicePage.this);
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

    // Retourne à la page précédente
    public void onReturn(View view) {
        returnWithError("");
    }

    //this way you don't have to copy the intent code
    private void returnWithError(String errorMsg) {
        Intent returnToServicesIntent = new Intent(ModifyServicePage.this, ServicesPage.class);
        if (errorMsg != "") {
            returnToServicesIntent.putExtra("errorMsg", errorMsg);
            startActivity(returnToServicesIntent);
        } else {
            finish();
        }
    }


}