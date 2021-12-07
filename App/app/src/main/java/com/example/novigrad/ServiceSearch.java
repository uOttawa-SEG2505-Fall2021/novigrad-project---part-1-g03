package com.example.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceSearch extends AppCompatActivity {

    private ArrayList<Time> times = new ArrayList<Time>();
    private ListView timeList;
    private Button search, addTime;
    private Spinner locationsSpinner, servicesSpinner;
    private DatabaseReference dbSuccursales, dbServices;
    private String username, firstName, lastName;
    private ArrayList<Integer[]> succursales_times;
    private ArrayList<String[]> service_fournis;
    private ArrayList<String> addresses;
    private ArrayList<String> service_ids;
    private String[] service_names;
    private int addTimeState = 0;
    private ArrayList<SearchDemande> possibleDemands = new ArrayList<SearchDemande>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_search);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                username = extras.getString("clientName");
                firstName = extras.getString("firstName");
                lastName = extras.getString("lastName");
            }
        }

        search = findViewById(R.id.btnChercher);
        addTime = findViewById(R.id.ajouter_date);
        locationsSpinner = findViewById(R.id.address_query);
        servicesSpinner = findViewById(R.id.service_query);
        timeList = findViewById(R.id.dates_query);

        dbSuccursales = FirebaseDatabase.getInstance().getReference("succursales");
        dbServices = FirebaseDatabase.getInstance().getReference("services");

    }

    @Override
    protected void onStart() {
        super.onStart();

        dbServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Service> services = new ArrayList<Service>();
                service_ids = new ArrayList<String>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Service serv = postSnapshot.getValue(Service.class);
                    service_ids.add(postSnapshot.getKey());
                    services.add(serv);
                }
                service_names = new String[services.size()];
                for (int i = 0; i < services.size(); i++) {
                    service_names[i] = services.get(i).getNomService();
                }
                updateServiceSpinner(service_names);
            }
            @Override
            public void onCancelled(DatabaseError error) { }});

        dbSuccursales.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                succursales_times = new ArrayList<Integer[]>();
                service_fournis = new ArrayList<String[]>();
                int i = 0;
                addresses = new ArrayList<String>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    succursales_times.add(new Integer[14]);
                    int[] localtimes = new int[14];
                    addresses.add(postSnapshot.child("adresse").getValue(String.class));
                    int j = 0;
                    for(DataSnapshot time: postSnapshot.child("times").getChildren()) {
                        succursales_times.get(i)[j] = time.getValue(Integer.class);
                        localtimes[j] = time.getValue(Integer.class);
                        j++;
                    }

                    ArrayList<String> offeredServices = new ArrayList<String>();
                    for(DataSnapshot servOffert : postSnapshot.child("services").getChildren()) {
                        offeredServices.add(servOffert.getValue(String.class));
                    }
                    String[] offServices = new String[offeredServices.size()];
                    for(int k = 0; k < offeredServices.size(); k++) {
                        offServices[k] = offeredServices.get(k);
                    }
                    service_fournis.add(offServices);
                    for(int l = 0; l < offServices.length; l++) {
                        SearchDemande newCandidate = new SearchDemande(addresses.get(i),firstName, lastName, username,
                                service_names[service_ids.indexOf(offServices[l])],
                                postSnapshot.child("name").getValue(String.class), localtimes);
                        possibleDemands.add(newCandidate);
                    }
                    i++;
                }
                updateLocationSpinner(addresses);
            }
            @Override
            public void onCancelled(DatabaseError error) { }});

    }

    private void updateTimeList() {
        TimeList timeAdapter = new TimeList(ServiceSearch.this, times);
        timeList.setAdapter(timeAdapter);
    }

    private void updateServiceSpinner(String[] options) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicesSpinner.setAdapter(adapter);
    }

    private void updateLocationSpinner(ArrayList<String> options) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationsSpinner.setAdapter(adapter);
    }

    public void onAddTime(View view) {
        if(addTimeState == 0) {
            times.add(new Time());
            addTime.setText("Selectionner un jour");
            addTimeState = (addTimeState + 1) % 3;
            updateTimeList();
        } else if(addTimeState == 1) {

            String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pick a color");
            builder.setItems(days, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    times.get(times.size() - 1).setDay(which);
                    addTimeState = (addTimeState + 1) % 3;
                    updateTimeList();
                }
            });
            builder.show();
            addTime.setText("Selectionner un temps");
        } else {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    ServiceSearch.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker pickerView, int hourOfDay, int minute) {
                            times.get(times.size() - 1).setTime(hourOfDay * 60 + Helpers.approximateTime(minute));
                            addTimeState = (addTimeState + 1) % 3;
                            updateTimeList();
                        }
                    }, 12, 0, false
            );
            // Set transparent background
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // Show dialog
            timePickerDialog.show();
            addTime.setText("Ajouter");
        }

    }

    // broken because I didn't understand how spinners worked

    public void onSearch(View view) {
        ArrayList<SearchDemande> filtered = new ArrayList<SearchDemande>();
        for (SearchDemande candidate : possibleDemands) {
            //filter out bad times
            if(!times.isEmpty()){
                boolean oneMatch = false;
                for (Time time: times) oneMatch = oneMatch || time.withinTimes(candidate.getTimes());
                if(!oneMatch) continue;
            }

            //filter out bad location/succursale
            if (locationsSpinner.getId() == 0) {
                if(!(candidate.getAddress().equals(addresses.get(locationsSpinner.getId())))) continue;
            }

            //filter out wrong service
            if (servicesSpinner.getId() != 0) {
                if(!(candidate.getNomDuServiceDemande().equals(service_names[servicesSpinner.getId()]))) continue;
            }

            // add correct result to filtered list
            filtered.add(candidate);

        }

        //for testing
        for(SearchDemande demande: filtered) System.out.println(demande.toString());

    }

}