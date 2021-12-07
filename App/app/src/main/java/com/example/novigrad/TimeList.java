package com.example.novigrad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TimeList extends ArrayAdapter<Time>{

    private final Activity context;
    List<Time> times;

    public TimeList(Activity context, List<Time> times) {
        super(context, R.layout.layout_time_list, times);
        this.context = context;
        this.times = times;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        LayoutInflater inflater = context.getLayoutInflater();
        View timeListItem = inflater.inflate(R.layout.layout_time_list, null,true);
        TextView dayLabel = timeListItem.findViewById(R.id.dayOfWeek);
        TextView timeLabel = timeListItem.findViewById(R.id.timeOfDay);
        Time time = times.get(position);
        dayLabel.setText(days[time.getDay()]);
        timeLabel.setText((time.getTime() != 0) ? Helpers.formatHHmm(time.getTime()) : "");
        return timeListItem;
    }

}
