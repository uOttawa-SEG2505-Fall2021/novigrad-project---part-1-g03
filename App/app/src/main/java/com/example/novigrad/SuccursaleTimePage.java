package com.example.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.novigrad.user.UserAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SuccursaleTimePage extends AppCompatActivity {
    // Initialize variable

    String succursaleName;
    DatabaseReference databaseSuccursale;
    ListView dayIntervalList;

    TextView tvTimer1, tvTimer2;
    int t1Hour, t1minute, t2Hour, t2Minute;
    int[] times = new int[14];
    HashMap<String, Integer> timesMap;
    Interval[] dayIntervals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succursale_time_page);

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
                    System.out.println(time.getKey() + time.getValue(Integer.class));
                }
                dayIntervals = Helpers.convertTimeHashMapToIntervals(timesMap);
                IntervalList intervalAdapter = new IntervalList(SuccursaleTimePage.this, Arrays.asList(dayIntervals));
                dayIntervalList.setAdapter(intervalAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Assign Variable
        tvTimer1 = findViewById(R.id.tv_timer1);
        tvTimer2 = findViewById(R.id.tv_timer2);

        tvTimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        SuccursaleTimePage.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Initialize hour and minute
                                t1Hour = hourOfDay;
                                t1minute = Helpers.approximateTime(minute);

                                // Initialize calendar
                                Calendar calendar = Calendar.getInstance();

                                // Set hour and minute
                                calendar.set(0,0,0, t1Hour, t1minute);

                                // Set selected time on text view
                                tvTimer1.setText(DateFormat.format("hh:mm aa", calendar));

                            }
                        }, 12, 0, false
                );
                // Display previous selected time
                timePickerDialog.updateTime(t1Hour, t1minute);

                // Show dialog
                timePickerDialog.show();
            }
        });
        tvTimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize time picker dialog

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        SuccursaleTimePage.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Initialize hour and minute
                                t2Hour = hourOfDay;
                                t2Minute = minute;

                                // Store hour and minute in String
                                String time = t2Hour + ":" + t2Minute;

                                // Initialize 24h time format
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);

                                    // Initialize 12 hours time format
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );

                                    // Set selected time on text view
                                    tvTimer2.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                // Set transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // Display previously selected time
                timePickerDialog.updateTime(t2Hour, t2Minute);

                // Show dialog
                timePickerDialog.show();
            }
        });
    }

    public void onReturn(View view){
        finish();
    }

    public void onUpdateTimes(View view) {

    }
}