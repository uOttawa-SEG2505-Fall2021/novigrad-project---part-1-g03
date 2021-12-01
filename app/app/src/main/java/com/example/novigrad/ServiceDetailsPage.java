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

/**
 * ServiceDetails classe qui permet d'afficher les détails d'un service sur une page
 * */

public class ServiceDetailsPage extends AppCompatActivity {

   private String serviceName, serviceDocs, serviceInfo, serviceId;
   private TextView serviceNameView, serviceDocsView, serviceInfoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                serviceName = extras.getString("serviceName");
                serviceDocs = extras.getString("serviceDocs");
                serviceInfo = extras.getString("serviceInfo");
                serviceId = extras.getString("serviceId");
            }
        }

        serviceNameView = findViewById(R.id.details_name);
        serviceDocsView = findViewById(R.id.details_docs);
        serviceInfoView = findViewById(R.id.details_info);
        serviceNameView.setText(serviceName);
        serviceDocsView.setText(serviceDocs);
        serviceInfoView.setText(serviceInfo);

    }

    // Retourne à la page précédente
    public void onReturn(View view){
        finish();
    }


}