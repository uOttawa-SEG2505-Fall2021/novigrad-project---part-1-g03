package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;

public class SuccursaleTimePage extends AppCompatActivity {
    // Initialize variable

    String succursaleName;
    DatabaseReference databaseSuccursale;
    Spinner daySelect;
    ListView dayIntervalList;

    HashMap<String, Integer> timesMap;
    Interval[] dayIntervals;

    void updateTimeIntervalList() {
        dayIntervals = Helpers.convertTimeHashMapToIntervals(timesMap);
        IntervalList intervalAdapter = new IntervalList(SuccursaleTimePage.this, Arrays.asList(dayIntervals));
        dayIntervalList.setAdapter(intervalAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succursale_time_page);

        daySelect = findViewById(R.id.daySelect);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.daysOfTheWeek, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySelect.setAdapter(adapter);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                succursaleName = extras.getString("succursaleName");
                databaseSuccursale = FirebaseDatabase.getInstance().getReference("succursales").child(succursaleName);
            }
        }

        dayIntervalList = findViewById(R.id.dayIntervalList);

        //get actual times from the succ
        databaseSuccursale.child("times").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot succSnapshot) {
                timesMap = new HashMap<>();
                for (DataSnapshot time : succSnapshot.getChildren()) {
                    timesMap.put(time.getKey(), time.getValue(Integer.class));
                }
                updateTimeIntervalList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onReturn(View view){
        finish();
    }

    public void onSetTime(View view) {
        //0 is the one that says "Selectionner un jour:"
        //therefore any clicks should be ignored
        if (daySelect.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Selectionner un jour SVP.", Toast.LENGTH_SHORT).show();
            return;
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                SuccursaleTimePage.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker pickerView, int hourOfDay, int minute) {
                        int isB = (view.getId() == R.id.timeSelectEnd) ? 1 : 0;

                        int time = hourOfDay * 60 + Helpers.approximateTime(minute);
                        Helpers.setValueInTimeHashMap(timesMap, time, 2*(daySelect.getSelectedItemPosition()-1) + isB);//daySelect positions start from 1
                        updateTimeIntervalList();
                    }
                }, 12, 0, false
        );
        // Set transparent background
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Show dialog
        timePickerDialog.show();

    }

    public void onUpdateTimes(View view) {
        if (Helpers.verifyTimesMap(timesMap)) {
            databaseSuccursale.child("times").setValue(timesMap);
        } else {
            Toast.makeText(this, "Il y a des temps invalides (commence après la fin)", Toast.LENGTH_SHORT).show();
        }
    }
}