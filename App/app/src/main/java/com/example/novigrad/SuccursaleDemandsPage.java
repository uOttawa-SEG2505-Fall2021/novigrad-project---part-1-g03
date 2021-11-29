package com.example.novigrad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * SuccursaleDemandePage qui permet de visualiser, approuver ou rejeter des demandes
 * */
public class SuccursaleDemandsPage extends AppCompatActivity {

    ListView listViewDemandes;
    List<Demande> demandes;
    DatabaseReference databaseDemandes;
    TextView message;
    String succursaleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succursale_demands_page);
        message = (TextView) findViewById(R.id.messageDemande);
        listViewDemandes = (ListView) findViewById(R.id.listViewServicesDispo);
        demandes = new ArrayList<>();
        databaseDemandes = FirebaseDatabase.getInstance().getReference("demandes");

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                succursaleName = extras.getString("succursaleName");
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        databaseDemandes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                demandes.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Demande demande = postSnapshot.getValue(Demande.class);
                    if(demande.getNomSuccursaleDemandé().equals(succursaleName)) {
                        demandes.add(demande);
                    }
                }
                DemandeList demandeAdapter = new DemandeList(SuccursaleDemandsPage.this, demandes);
                listViewDemandes.setAdapter(demandeAdapter);
                if(demandes.size() == 0) {
                    message.setText(R.string.errorMessageDemandes);
                } else {
                    message.setText(R.string.instructionsDemande);
                }
                listViewDemandes.setLongClickable(true);
                listViewDemandes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Demande demandeActuelle = demandes.get(position);
                        AlertDialog.Builder alert = new AlertDialog.Builder(SuccursaleDemandsPage.this);
                        alert.setTitle(demandeActuelle.toString());
                        alert.setMessage(R.string.approve_decline_msg);
                        alert.setPositiveButton("APPROUVER", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Demande demandeToBeRemoved = demandes.get(position);
                                Toast.makeText(getApplicationContext(), "Demande approuvée", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        alert.setNegativeButton("REJETER", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Demande rejetée", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                        alert.show();
                        return true;
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    // Retourne à la page précédente
    public void onReturn(View view){
        finish();
    }
}