package com.example.DentalHealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EnrolPage extends AppCompatActivity {
    public Button confirm,back;
    public TextView courseinfo,hint;
    DatabaseHelper databaseHelper;

    /**
     * appointment page, allow the patient to input all the information
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String ssn = bundle.getString("SSN");
        int appid = bundle.getInt("appid");
        String patientname = bundle.getString("patient");
        String curpatient = bundle.getString("curpatient");
        String status = bundle.getString("status");
        String type = bundle.getString("type");
        String date = bundle.getString("date");
        String time = bundle.getString("time");
        int hours = Integer.parseInt(bundle.getString("hours"));
        String dentist = bundle.getString("dentist");
        String branch = bundle.getString("branch");
        String price = bundle.getString("price");
        databaseHelper = new DatabaseHelper(this);

        setContentView(R.layout.activity_memberenrolpage);
        confirm = findViewById(R.id.bt_enrolconfirm);
        back = findViewById(R.id.bt_enrolpageback);
        courseinfo = findViewById(R.id.tx_cklassinfo);
        hint = findViewById(R.id.tx_hitmsg);
        courseinfo.setText("Patient Name: "+ curpatient +"\n" + "Branch location: " + branch + "\n" +
                "Appointment Type: " + type + "\n" + "Appointment date: "+ date + "\n" +
                "Appointment time: " + time + "\n" +
                "Appointment Hour: " + hours + "\n" + "Dentist: " + dentist +"\n"+ "Price: " + price +"\n"+
                "Status: "+ status);
        boolean enrolled = bundle.getBoolean("enrolled");
        if (enrolled){
            confirm.setText("UNENROL");
            hint.setText("You are enrolled to this course.");
        }else{
            confirm.setText("ENROL");
            hint.setText("You are not in this course.");
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enrolled){
                    databaseHelper.UpdatePatient(appid,"null");
                }else{
                    databaseHelper.UpdatePatient(appid,patientname);
                }
                Intent intent = new Intent(EnrolPage.this, memberpage.class);
                Bundle finalBundle = new Bundle();;
                finalBundle.putString("SSN",ssn);
                intent.putExtras(finalBundle);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });




    }
}
