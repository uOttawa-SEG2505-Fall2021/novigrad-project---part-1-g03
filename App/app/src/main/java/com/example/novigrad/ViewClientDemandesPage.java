package com.example.novigrad;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewClientDemandesPage extends AppCompatActivity {

    //notez bien: 0 - pending; 1 - approved; 2 - rejected
    final String[] STATUS_NAMES = new String[]{"pending", "approved", "rejected"}; //needs to be translated
    int selectedList; //0 - pending; 1 - approved; 2 - rejected

    DatabaseReference databaseDemandes;
    String clientName;
    ListView listViewDemandes;
    TextView message;
    List<Demande> demandesP;
    List<Demande> demandesA;
    List<Demande> demandesR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client_demandes);

        selectedList = 0;
        message = findViewById(R.id.headerDemandes);
        listViewDemandes = findViewById(R.id.listViewDemandes);

        demandesP = new ArrayList<>();
        demandesA = new ArrayList<>();
        demandesR = new ArrayList<>();

        databaseDemandes = FirebaseDatabase.getInstance().getReference("demandes");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                clientName = extras.getString("clientName");
            }
        }
    }

    private void clearDemandes() {
        demandesP.clear();
        demandesA.clear();
        demandesR.clear();
    }

    private List<Demande> getSelectedDemandes(int status) {
        switch (status) {
            case 0: return demandesP;
            case 1: return demandesA;
            case 2: return demandesR;
        }
        return null;
    }

    private void updateDemandes() {
        final List<Demande> selectedDemandes = getSelectedDemandes(selectedList);
        DemandeList demandesList = new DemandeList(ViewClientDemandesPage.this, selectedDemandes);
        listViewDemandes.setAdapter(demandesList);
        if (selectedDemandes.size() == 0) {
            message.setText(R.string.errorMessageDemandes);
        } else {
            message.setText(R.string.instructionsDemande);
        }
        listViewDemandes.setLongClickable(true);
        listViewDemandes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Demande demandeActuelle = selectedDemandes.get(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewClientDemandesPage.this);
                alert.setTitle(demandeActuelle.toString());
                alert.setMessage("Blah Blah Blah something or other");
                alert.setPositiveButton("RETOURNER", new DialogInterface.OnClickListener() {
                    //dont think there's anything to be done here
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                alert.setNegativeButton("SUPPRIMER", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Demande supprimée", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                alert.show();
                return true;
            }
        });

        message.setText("Liste des demandes " + STATUS_NAMES[selectedList]);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseDemandes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                clearDemandes();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Demande demande = postSnapshot.getValue(Demande.class);
                    System.out.println(demande);
                    System.out.println(clientName);
                    if (demande.getNomDeUtilisateur().equals(clientName)) {
                        getSelectedDemandes(demande.getStatus()).add(demande);
                    }
                }
                updateDemandes();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    public void onSelectList(View view) {
        switch (view.getId()) {
            case R.id.btnPending: selectedList=0; break;
            case R.id.btnApproved: selectedList=1; break;
            case R.id.btnRejected: selectedList=2; break;
        }
        updateDemandes();
    }



    // Retourne à la page précédente
    public void onReturn(View view) {
        finish();
    }
}