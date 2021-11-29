package com.example.novigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Classe DemandeList qui nous permet d'afficher la liste des demandes soumises
 * */

public class DemandeList extends ArrayAdapter<Demande> {
    private final Activity context;
    List<Demande> demandes;

    public DemandeList(Activity context, List<Demande> demandes) {
        super(context, R.layout.layout_item_list, demandes);
        this.context = context;
        this.demandes = demandes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_item_list, null,true);
        TextView DemandeInfo = (TextView) listViewItem.findViewById(R.id.listItemName);

        Demande demande = demandes.get(position);
        DemandeInfo.setText(String.format(context.getString(R.string.InfosSurDemande), demande.getFirstName(), demande.getLastName(), demande.getNomDeUtilisateur(), demande.getNomDuServiceDemande()));
        return listViewItem;
    }
}
