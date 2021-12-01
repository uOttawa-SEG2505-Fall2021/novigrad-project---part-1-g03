package com.example.novigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Classe ServiceList permet d'afficher la liste des services
 * */

public class ServiceList extends ArrayAdapter<Service> {

    private final Activity context;
    List<Service> services;

    public ServiceList(Activity context, List<Service> services) {
        super(context, R.layout.layout_item_list, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_item_list, null,true);
        TextView serviceName = (TextView) listViewItem.findViewById(R.id.listItemName);

        Service service = services.get(position);
        serviceName.setText(service.getNomService());
        return listViewItem;
    }
}
