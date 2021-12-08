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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceSearch extends AppCompatActivity {

    private ArrayList<Time> times = new ArrayList<Time>();
    private TextView displayedResult;
    private ListView timeList;
    private Button search, addTime;
    private Spinner locationsSpinner, servicesSpinner;
    private DatabaseReference dbSuccursales, dbServices, dbDemandes;
    private String username, firstName, lastName;
    private ArrayList<String[]> service_fournis;
    private ArrayList<String> addresses;
    private ArrayList<String> service_ids;
    private String[] service_names;
    private int addTimeState = 0;
    private ArrayList<SearchDemande> possibleDemands = new ArrayList<SearchDemande>();
    private ArrayList<SearchDemande> filteredDemands = possibleDemands;
    private int currentResult = 0;


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
        displayedResult = findViewById(R.id.demande_cherchée);

        dbSuccursales = FirebaseDatabase.getInstance().getReference("succursales");
        dbServices = FirebaseDatabase.getInstance().getReference("services");
        dbDemandes = FirebaseDatabase.getInstance().getReference("demandes");

        servicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterResults();
                updateDemandeView();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        locationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterResults();
                updateDemandeView();
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                service_fournis = new ArrayList<String[]>();
                int i = 0;
                addresses = new ArrayList<String>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    int[] localtimes = new int[14];
                    addresses.add(postSnapshot.child("adresse").getValue(String.class));
                    String[] labels = {"lunA", "lunB", "marA", "marB", "merA", "merB", "jeuA", "jeuB",
                            "venA", "venB", "samA", "samB", "dimA", "dimB"};
                    ArrayList<String> conversion = new ArrayList<String>(Arrays.asList(labels));
                    for(DataSnapshot time: postSnapshot.child("times").getChildren()) {
                        String key = time.getKey();
                        localtimes[conversion.indexOf(key)] = time.getValue(Integer.class);
                    }

                    ArrayList<String> offeredServices = new ArrayList<String>();
                    for(DataSnapshot servOffert : postSnapshot.child("servicesFournis").getChildren()) {
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
                System.out.println(filteredDemands.isEmpty());
                System.out.println(possibleDemands.isEmpty());
                updateDemandeView();
            }
            @Override
            public void onCancelled(DatabaseError error) { }});

    }

    private void updateTimeList() {
        TimeList timeAdapter = new TimeList(ServiceSearch.this, times);
        timeList.setAdapter(timeAdapter);
    }

    private void updateServiceSpinner(String[] options) {

        String[] servicesSpinnerOptions = new String[options.length + 1];
        servicesSpinnerOptions[0] = "Selectionner un service";
        for(int i = 1; i < servicesSpinnerOptions.length; i++) {
            servicesSpinnerOptions[i] = options[i-1];
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, servicesSpinnerOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicesSpinner.setAdapter(adapter);
    }

    private void updateLocationSpinner(ArrayList<String> options) {

        String[] locationsSpinnerOptions = new String[options.size()+1];
        locationsSpinnerOptions[0] = "Selectionner une adresse";
        for(int i = 1; i < locationsSpinnerOptions.length; i++) {
            locationsSpinnerOptions[i] = options.get(i-1);
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, locationsSpinnerOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationsSpinner.setAdapter(adapter);
    }

    private void updateDemandeView() {
        if(filteredDemands.isEmpty()){ displayedResult.setText(""); return;}
        SearchDemande displayed = filteredDemands.get(currentResult);
        String display = "Service: " + displayed.getNomDuServiceDemande() + "\n Adresse: " + displayed.getAddress();
        displayedResult.setText(display);
    }

    public void onDelete(View view) {
        addTimeState = 0;
        if (times.size() > 0){
            times.remove(times.size() - 1);
            updateTimeList();
            filterResults();
            updateDemandeView();
        }
    }

    public void onPrev(View view) {
        if(currentResult == 0) return;
        currentResult--;
        updateDemandeView();
    }

    public void onNext(View view) {
        if(currentResult == filteredDemands.size() -1) return;
        currentResult++;
        updateDemandeView();
    }

    public void makeRequest(View view) {
        if(filteredDemands.isEmpty()) {
            Toast.makeText(this, "Il n'y a pas de succursale/service choisi", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void onAddTime(View view) {
        if(addTimeState == 0) {
            times.add(new Time());
            addTime.setText("Un jour?");
            addTimeState = (addTimeState + 1) % 3;
            updateTimeList();
        } else if(addTimeState == 1) {

            String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choisisez un jour de la semaine.");
            builder.setItems(days, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    times.get(times.size() - 1).setDay(which);
                    addTimeState = (addTimeState + 1) % 3;
                    updateTimeList();
                }
            });
            builder.show();
            addTime.setText("Un temps?");
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
                            filterResults();
                            updateDemandeView();
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

    private void filterResults() {
        // so that it only updates when time is fully entered
        int offset = 0;
        currentResult = 0;
        if (addTimeState != 0) {
            offset = 1;
        }
        ArrayList<SearchDemande> filtered = new ArrayList<SearchDemande>();
        for (SearchDemande candidate : possibleDemands) {
            //filter out bad times
            if(!times.isEmpty()){
                boolean oneMatch = false;
                for (int i = 0; i < times.size() - offset; i++) oneMatch = oneMatch || times.get(i).withinTimes(candidate.getTimes());
                if(!oneMatch) continue;
            }

            //filter out bad location/succursale
            if (locationsSpinner.getSelectedItemPosition() != 0) {
                if(!(candidate.getAddress().equals(addresses.get(locationsSpinner.getSelectedItemPosition() - 1)))) continue;
            }

            //filter out wrong service
            if (servicesSpinner.getSelectedItemPosition() != 0) {
                if(!(candidate.getNomDuServiceDemande().equals(service_names[servicesSpinner.getSelectedItemPosition() - 1]))) continue;
            }

            // add correct result to filtered list
            filtered.add(candidate);
        }

        //for testing
        filteredDemands = filtered;

    }

}