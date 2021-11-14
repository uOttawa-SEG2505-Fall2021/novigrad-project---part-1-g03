package com.example.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.novigrad.user.UserAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccountDeletionPage extends AppCompatActivity {


    ListView listViewAccounts;
    List<UserAccount> accounts; // devrait avoir seulement les comptes employee et client
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_deletion);

        listViewAccounts = (ListView) findViewById(R.id.listViewAccounts);
        accounts = new ArrayList<>();

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                accounts.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserAccount account = postSnapshot.getValue(UserAccount.class);
                    if (account.getAccountType() == 0 || account.getAccountType() == 1) {
                        accounts.add(account);
                    }
                }
                AccountList accountAdapter = new AccountList(AccountDeletionPage.this, accounts);
                listViewAccounts.setAdapter(accountAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}