package com.example.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ServicesFournisPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_fournis_page);
    }

    public void onSeeAll(View view){
//        Intent intent = new Intent(getApplicationContext(), AllServicesAvailPage.class);
//        startActivityForResult(intent, 0);
    }

    public void onReturn(View view){
        finish();
    }
}