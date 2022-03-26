package com.example.yolofitness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Dentist_page extends AppCompatActivity {
    public TextView hint;
    public Button bt_add, bt_menu;
    public ListView lt_appointment;

    DatabaseHelper database;

    /**
     * Instructor display page view all classes
     * @param savedInstanceState
     * @return null
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dentist_page);
        hint = (TextView) findViewById(R.id.textView2);
        bt_add = (Button) findViewById(R.id.bt_Add);
        bt_menu = (Button) findViewById(R.id.bt_backtomain);
        lt_appointment = (ListView)findViewById(R.id.LT_appointment);
        database = new DatabaseHelper(this);
        Bundle bundle;
        bundle = getIntent().getExtras();
        Bundle finalBundle = bundle;
        String SSN = bundle.getString("SSN");

        displayAppList(database);

        bt_menu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override

            /**
             * user clicks button, change button name and its function.
             * @param view
             * @return null
             *
             */
            public void onClick(View v) {
                Appointment appointment;
                try {
                    String dentistname = database.getName(SSN);
                    appointment = new Appointment(-1, "null","null","null",dentistname,"Monday","null","1","null");
                    Toast.makeText(Dentist_page.this, appointment.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(Dentist_page.this, "Error making appointment", Toast.LENGTH_SHORT).show();
                    appointment = new Appointment(-1,"error","error","error","error","error","error","1","error");
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(Dentist_page.this);
                boolean success = databaseHelper.addOne(appointment);
                Toast.makeText(Dentist_page.this, "Success: "+success, Toast.LENGTH_SHORT).show();
                displayAppList(databaseHelper);

            }
        });


        lt_appointment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            /**
             * Listview class info&instructor, display conflict message
             * @param view,i,l
             * @return null
             */
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Appointment clickedApp = (Appointment) adapterView.getItemAtPosition(i);
                String dentistname = clickedApp.getDentistid();
                boolean verify = database.Verify_Appname(dentistname);
                if (verify){
                    finalBundle.putInt("appid",clickedApp.getAppid());
                    finalBundle.putString("patientname",clickedApp.getPatientname());
                    finalBundle.putString("branch",clickedApp.getBranch());
                    finalBundle.putString("status",clickedApp.getStatus());
                    finalBundle.putString("dentist",clickedApp.getDentistid());
                    finalBundle.putString("date",clickedApp.getDate());
                    finalBundle.putString("time",clickedApp.getTime());
                    finalBundle.putString("hours",clickedApp.getHours());
                    finalBundle.putString("type",clickedApp.getType());
                    Intent intent = new Intent(Dentist_page.this, modifyApp_page.class);
                    intent.putExtras(finalBundle);
                    startActivity(intent);
                }else{
                    Toast.makeText(Dentist_page.this, "This appointment is already owned by another dentist. ", Toast.LENGTH_SHORT).show();
                }
            }

        });
        }

    /**
     * displayclass
     * @param databaseHelper
     * @return null
     *
     */
    private void displayAppList(DatabaseHelper databaseHelper) {
        List<Appointment> appointmentList = databaseHelper.getAll();
        ArrayAdapter appointmentArrayAdapter = new ArrayAdapter<Appointment>(Dentist_page.this, android.R.layout.simple_list_item_1, appointmentList);
        lt_appointment.setAdapter(appointmentArrayAdapter);
    }
    }



