package com.example.novigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Classe ServiceList qui nous permet d'afficher la liste des intervals
 * */

public class IntervalList extends ArrayAdapter<Interval> {

    private final Activity context;
    List<Interval> intervals;

    public IntervalList(Activity context, List<Interval> intervals) {
        super(context, R.layout.layout_interval_list, intervals);
        this.context = context;
        this.intervals = intervals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View intervalListItem = inflater.inflate(R.layout.layout_interval_list, null,true);
        TextView interLabel = (TextView) intervalListItem.findViewById(R.id.intervalLabel);
        TextView interStart = (TextView) intervalListItem.findViewById(R.id.intervalStart);
        TextView interEnd = (TextView) intervalListItem.findViewById(R.id.intervalEnd);

        Interval interval = intervals.get(position);
        interLabel.setText(interval.name+": ");
        if(interval.getStart() != -1) {
            interStart.setText("Heure d'ouverture: " + Helpers.formatHHmm(interval.getStart()));
            interEnd.setText("Heure de fermeture: " + Helpers.formatHHmm(interval.getEnd()));
        } else {
            interStart.setText("Fermé");
            interEnd.setText("");
        }
        return intervalListItem;
    }
}
