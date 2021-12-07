package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitDemandePage extends AppCompatActivity {

    private TextView serviceNameView, serviceDocsView, serviceInfoView;

    private String serviceKey;
    private Service serviceApplyingTo;
    DatabaseReference databaseServices;

    private RatingBar bar;
    private float cote; // This is the number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_demande_page);

        // Samy Stuff
        bar = (RatingBar) findViewById(R.id.rating_bar);
        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(SubmitDemandePage.this, "Service évalué à: " + rating, Toast.LENGTH_SHORT).show();
                cote = rating;
            }
        });

        // End Samy stuff

        databaseServices = FirebaseDatabase.getInstance().getReference("services");


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                serviceKey = extras.getString("serviceKey");
            } else {
                //error handling
//                finish();
                serviceKey = "-MofyR4RKkazUR4ef6ap";
            }
        }

        serviceNameView = findViewById(R.id.details_name);
        serviceDocsView = findViewById(R.id.details_docs);
        serviceInfoView = findViewById(R.id.details_info);

        databaseServices.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot serviceSnap:
                     task.getResult().getChildren()) {
                    if (serviceSnap.getKey().equals(serviceKey)) {
                        serviceApplyingTo = serviceSnap.getValue(Service.class);
                        serviceNameView.setText(serviceApplyingTo.getNomService());
                        serviceDocsView.setText(serviceApplyingTo.getDocsRequis());
                        serviceInfoView.setText(serviceApplyingTo.getInfosRequises());
                    }
                }
            }
        });

    }

    public void onSelectPDF(View view) {
        Intent pdfSelectIntent = new Intent();
        pdfSelectIntent.setType("application/pdf");
        pdfSelectIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pdfSelectIntent,"SVP choisir un fichier .pdf"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData()!= null) {
            Toast.makeText(this, "Fichier .pdf ajouté avec succès", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fichier .pdf n'a pas été ajouté", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSubmitDemande(View view) {
        //do database stuff
    }

    public void onReturn(View view) {
        finish();
    }
}