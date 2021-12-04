package com.example.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ClientPage extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_page);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                username = extras.getString("username");
            } else {
                //Cette code ici devrait jamais s'exécuter
                // S'il exécute, il y a des problèmes avec l'information transmise dans la page précédente

                //assigner des valeurs vides pour éviter les erreurs
                username = "";
            }
        }
    }

    public void onDemandes(View view) {
        Intent demandes = new Intent(ClientPage.this, ViewClientDemandesPage.class);
        demandes.putExtra("clientName", username);
        startActivity(demandes);
    }
}