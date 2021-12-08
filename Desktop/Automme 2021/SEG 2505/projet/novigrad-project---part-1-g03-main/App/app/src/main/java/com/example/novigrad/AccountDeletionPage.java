package com.example.novigrad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.novigrad.user.UserAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de supprimer un compte
 * */

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
                    if (account.getAccountType() == 0 || account.getAccountType() == 1) { // devrait avoir seulement les comptes employee et client
                        accounts.add(account);
                    }
                }
                AccountList accountAdapter = new AccountList(AccountDeletionPage.this, accounts);
                listViewAccounts.setAdapter(accountAdapter);
                listViewAccounts.setLongClickable(true);
                listViewAccounts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(AccountDeletionPage.this);
                        alert.setTitle("Attention!!");
                        alert.setMessage("Êtes-vous sûr de vouloir supprimer ce compte? Cette action ne peut pas être renversée!");
                        alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete account here

                                //get account to be deleted
                                UserAccount userToBeDeleted = accounts.get(position);
                                if (userToBeDeleted.getAccountType() == 1) { //s'il est un compte employe, supprimer le succursale
                                    FirebaseDatabase.getInstance().getReference("succursales").child(userToBeDeleted.getNomDeUtilisateur()).removeValue();
                                }
                                databaseUsers.child(userToBeDeleted.getNomDeUtilisateur()).removeValue();

                                Toast.makeText( getApplicationContext(), "Suppression du compte réussi", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                        });
                        alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alert.show();

                        return true;

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // permet de retourner à la page précédente
    public void onReturn(View view) {
        finish();
    }

}