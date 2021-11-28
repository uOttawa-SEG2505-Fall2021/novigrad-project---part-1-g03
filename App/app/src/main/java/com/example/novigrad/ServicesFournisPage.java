package com.example.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ServicesFournisPage extends AppCompatActivity {

    String succursaleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_fournis_page);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                succursaleName = extras.getString("succursaleName");
            }
        }
    }

    public void onSeeAll(View view){
        Intent intent = new Intent(getApplicationContext(), AllServicesAvailPage.class);
        intent.putExtra("succursaleName", succursaleName);
        startActivityForResult(intent, 0);
    }

    public void onReturn(View view){
        finish();
    }
}