package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.novigrad.user.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCreateAccount(View view){
        Intent intent = new Intent(getApplicationContext(), CreateAccountPage.class);
        startActivityForResult(intent, 0);
    }

    public void onLogin(View view) {
        // get username
        EditText usernameET = (EditText) findViewById(R.id.username);
        String username = usernameET.getText().toString();

        EditText passwordET = findViewById(R.id.password);
        String password = passwordET.getText().toString();

        if (username.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Erreur, s'il vous plait entrez le nom d'utilisateur ET mot de passe!", Toast.LENGTH_LONG).show();
            return;
        }

        final String[] dbPassword = new String[1];

        // infoChecks[0] is username existence, infoChecks[1] is password correctness
        final boolean[] infoCheck = new boolean[1];
        final UserAccount[] user = new UserAccount[1];

        myRef.child("users").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> snapshot) {
                if (snapshot.getResult().exists()) {
                    user[0] = snapshot.getResult().getValue(UserAccount.class);
                    assert user[0] != null;
                    if (user[0].getMotDePasse().compareTo(password) == 0) {
                        if (user[0].getAccountType()==2){
                            Intent myIntent = new Intent(LoginPage.this, AdminPage.class);
                            myIntent.putExtra("userId", username);
                            startActivity(myIntent);
                        } else {
                            Intent myIntent = new Intent(LoginPage.this, WelcomePage.class);
                            myIntent.putExtra("userId", username);
                            startActivity(myIntent);
                        }
                    } else {
                        //wrong password
                        Toast.makeText(getApplicationContext(), "Erreur, le mot de passe est incorrect ", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    //account does not exist
                    Toast.makeText(getApplicationContext(), "Erreur, ce compte n'existe pas ", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}