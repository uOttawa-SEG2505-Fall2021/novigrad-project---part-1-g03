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

import com.example.novigrad.user.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitDemandePage extends AppCompatActivity {

    private TextView serviceNameView, serviceDocsView, serviceInfoView;

    private String succName;
    private String servKey;
    private Service applyServ;
    DatabaseReference dbServices;
    DatabaseReference dbDemandes;

    private RatingBar bar;
    private double cote; // This is the number

    private boolean submitted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_demande_page);

        // Samy Stuff
        bar = (RatingBar) findViewById(R.id.rating_bar);
        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                if (!submitted) {
                    ratingBar.setRating(0);
                    Toast.makeText(SubmitDemandePage.this, "Erreur: Vous n'avez pas encore soumit le demande", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SubmitDemandePage.this, "Service évalué à: " + rating, Toast.LENGTH_SHORT).show();
                    cote = rating;
                }
            }
        });

        // End Samy stuff
        dbServices = FirebaseDatabase.getInstance().getReference("services");
        dbDemandes = FirebaseDatabase.getInstance().getReference("demandes");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                servKey = extras.getString("serviceKey");
                succName = extras.getString("succName");
            } else {
                //error handling
                finish();
            }
        }

        serviceNameView = findViewById(R.id.details_name);
        serviceDocsView = findViewById(R.id.details_docs);
        serviceInfoView = findViewById(R.id.details_info);

        dbServices.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot serviceSnap:
                     task.getResult().getChildren()) {
                    if (serviceSnap.getKey().equals(servKey)) {
                        applyServ = serviceSnap.getValue(Service.class);
                        serviceNameView.setText(applyServ.getNomService());
                        serviceDocsView.setText(applyServ.getDocsRequis());
                        serviceInfoView.setText(applyServ.getInfosRequises());
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
        if (submitted) {
            Toast.makeText(this, "Erreur: Vous avez déjà soumit le demande", Toast.LENGTH_SHORT).show();
            return;
        }
        UserAccount user = UserAccount.getUserInstance();
        Demande newDemande = new Demande(user.getPrenom(), user.getNomDeFamille(), user.getNomDeUtilisateur(), applyServ.getNomService(), succName, 0);
        dbDemandes.push().setValue(newDemande);
        Toast.makeText(this, "Votre demande a été soumise, SVP évaluez (optionnel)", Toast.LENGTH_SHORT).show();
        submitted = true;
    }

    public void onSubmitRating(View view) {
        if (submitted) {
            Toast.makeText(this, "Votre évaluation a été soumit.", Toast.LENGTH_SHORT).show();
            FirebaseDatabase.getInstance().getReference("ratings").push().setValue(new Rating(cote, succName)); //ratings --> ratingKey --> rating
            finish();
        } else {
            Toast.makeText(this, "Erreur: Vous n'avez pas encore soumis la demande", Toast.LENGTH_SHORT).show();
        }
    }

    public void onReturn(View view) {
        finish();
    }
}