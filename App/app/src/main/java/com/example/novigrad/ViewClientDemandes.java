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

public class ViewClientDemandes extends AppCompatActivity {

    DatabaseReference databaseDemandes;
    String clientName;

    //notez bien: 0 - pending; 1 - approved; 2 - rejected
    final int[] STATUS_CODES = new int[]{0, 1, 2};
    ListView[] listViewDemandes;
    TextView[] messages;
    List<Demande> demandesP;
    List<Demande> demandesA;
    List<Demande> demandesR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client_demandes);

        messages = new TextView[3];
        messages[0] = (TextView) findViewById(R.id.messageDemandePending);
        messages[1] = (TextView) findViewById(R.id.messageDemandeApproved);
        messages[2] = (TextView) findViewById(R.id.messageDemandeRejected);

        listViewDemandes = new ListView[3];
        listViewDemandes[0] = (ListView) findViewById(R.id.listViewDemandesPending);
        listViewDemandes[1] = (ListView) findViewById(R.id.listViewDemandesApproved);
        listViewDemandes[2] = (ListView) findViewById(R.id.listViewDemandesRejected);

        demandesP = new ArrayList<>();
        demandesA = new ArrayList<>();
        demandesR = new ArrayList<>();

        databaseDemandes = FirebaseDatabase.getInstance().getReference("demandes");

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                clientName = extras.getString("clientName");
            }
        }
    }

    private void clearDemandes() {
        demandesP.clear();
        demandesA.clear();
        demandesR.clear();
    }

    @Override
    protected void onStart(){
        super.onStart();

        databaseDemandes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                clearDemandes();
                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Demande demande = postSnapshot.getValue(Demande.class);
                    System.out.println(demande);
                    System.out.println(clientName);
                    if(demande.getNomDeUtilisateur().equals(clientName)) {
                        switch (demande.getStatus()) {
                            case 0: demandesP.add(demande); break;
                            case 1: demandesA.add(demande); break;
                            case 2: demandesR.add(demande); break;
                        }
                    }
                }

                DemandeList[] demandesList = new DemandeList[3];
                demandesList[0] = new DemandeList(ViewClientDemandes.this, demandesP);
                demandesList[1] = new DemandeList(ViewClientDemandes.this, demandesA);
                demandesList[2] = new DemandeList(ViewClientDemandes.this, demandesR);

                for (int i: STATUS_CODES) { //
                    listViewDemandes[i].setAdapter(demandesList[i]);
                    if (demandesList[i].size() == 0) {
                        messages[i].setText(R.string.errorMessageDemandes);
                    } else {
                        messages[i].setText(R.string.instructionsDemande);
                    }
                    listViewDemandes[i].setLongClickable(true);
                    listViewDemandes[i].setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            List<Demande> demandes = demandesList[i].getList();
                            Demande demandeActuelle = demandes.get(position);
                            AlertDialog.Builder alert = new AlertDialog.Builder(ViewClientDemandes.this);
                            alert.setTitle(demandeActuelle.toString());
                            alert.setMessage(R.string.approve_decline_msg);
                            alert.setPositiveButton("RETOURNER", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), "Demande approuvée", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                            alert.setNegativeButton("SUPPRIMER", new DialogInterface.OnClickListener() {

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