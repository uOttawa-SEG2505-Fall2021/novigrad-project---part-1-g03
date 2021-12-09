package com.example.novigrad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Classe DemandeList permet d'afficher la liste des demandes soumises
 * */

public class DemandeList extends ArrayAdapter<Demande> {
    private final Activity context;
    List<Demande> demandes;

    public int size() {
        return demandes.size();
    }

    public List<Demande> getList() {
        return demandes;
    }

    public DemandeList(Activity context, List<Demande> demandes) {
        super(context, R.layout.layout_item_list, demandes);
        this.context = context;
        this.demandes = demandes;
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_item_list, null,true);
        TextView DemandeInfo = (TextView) listViewItem.findViewById(R.id.listItemName);

        Demande demande = demandes.get(position);
//        int statusInt = demande.getStatus();
//        String demandeStatus;
//        if(statusInt==0){
//            demandeStatus = "En attente";
//        } else if (statusInt==1){
//            demandeStatus = "Approuvée";
//        } else {
//            demandeStatus = "Rejetée";
//        }
        DemandeInfo.setText(String.format(context.getString(R.string.InfosSurDemande), demande.getFirstName(), demande.getLastName(), demande.getNomDeUtilisateur(), demande.getNomDuServiceDemande(), demande.getNomSuccursaleDemande(),
                (demande.getStatus() == 0 ? "En attente" : demande.getStatus() == 1 ? "Approuvée" : "Rejetée")));
        return listViewItem;
    }
}
