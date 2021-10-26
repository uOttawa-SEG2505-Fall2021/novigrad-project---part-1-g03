package com.example.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class WelcomePage extends AppCompatActivity {

    private String username;
    private int accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("userId");
        accountType = intent.getIntExtra("acctype", 0);
    }
}