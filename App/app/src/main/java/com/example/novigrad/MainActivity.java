package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.novigrad.user.AdminAccount;
import com.example.novigrad.user.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCreateAccount(View view){
        Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
        startActivityForResult(intent, 0);
    }

    public void onLogin(View view) {
        // get username
        EditText usernameET = (EditText) findViewById(R.id.username);
        String username = usernameET.getText().toString();

        EditText passwordET = (EditText) findViewById(R.id.password);
        String password = usernameET.getText().toString();

        final String[] dbPassword = new String[1];

        // infoChecks[0] is username existence, infoChecks[1] is password correctness
        final boolean[] infoChecks = new boolean[2];
        final UserAccount[] user = new UserAccount[1];

        myRef.child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    infoChecks[0] = true;
                    user[0] = snapshot.getValue(UserAccount.class);
                }
                else {
                    //feedback stuff
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        if(infoChecks[0]){
            infoChecks[1] = (user[0].getMotDePasse().compareTo(password) == 0);

        } else {
            usernameET.setText("");
        }

       if(infoChecks[1]) {
           Intent myIntent = new Intent(MainActivity.this, WelcomePage.class);
           myIntent.putExtra("userId", username);
           myIntent.putExtra("acctype", user[0].getAccountType());
           startActivity(myIntent);
       } else {
           passwordET.setText("");
       }
    }
}