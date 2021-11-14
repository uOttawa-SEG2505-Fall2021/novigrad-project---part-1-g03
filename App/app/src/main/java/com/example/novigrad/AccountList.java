package com.example.novigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.novigrad.user.UserAccount;

import java.util.List;

public class AccountList extends ArrayAdapter<UserAccount> {

    private Activity context;
    List<UserAccount> accounts;

    public AccountList(Activity context, List<UserAccount> accounts) {
        super(context, R.layout.layout_item_list, accounts);
        this.context = context;
        this.accounts = accounts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_item_list, null,true);
        TextView AccountName = (TextView) listViewItem.findViewById(R.id.listItemName);

        UserAccount account = accounts.get(position);
        AccountName.setText(account.getNomDeUtiliseur());
        return listViewItem;
    }
}
