package com.example.yolofitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class memberpage extends AppCompatActivity {
    public Button bt_pay, bt_member_viewApp;
    public EditText et_insurance;
    public ListView lt_view_apps;
    private String ssn,patientname;
    DatabaseHelper database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberview_page);
        bt_pay = (Button) findViewById(R.id.Paybutton);
        bt_member_viewApp = (Button) findViewById(R.id.member_viewclass);
        et_insurance = (EditText) findViewById(R.id.insurancenum);
        lt_view_apps = (ListView) findViewById(R.id.lt_view_apps);
        database = new DatabaseHelper(this);
        displayAppsList(database);
        Bundle bundle = getIntent().getExtras();
        ssn = bundle.getString("SSN");
        patientname = database.getName(ssn);
        Bundle finalBundle = bundle;


        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String insurance = et_insurance.getText().toString();
                if (database.Verify_Insurance(ssn,insurance)){
                    Toast.makeText(memberpage.this, "Pay successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(memberpage.this, "Pay unsuccessful, Please go to checkout counter instead.", Toast.LENGTH_SHORT).show();
                }
                }
        });
        bt_member_viewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String allorowned = bt_member_viewApp.getText().toString();
                if (allorowned.equals("VIEW Registered appointment")){
                    bt_member_viewApp.setText("VIEW ALL");
                    List<Appointment> enrolApps = getEnrolAppsList(database,patientname);
                    ArrayAdapter classarrayadapter = new ArrayAdapter<Appointment>(memberpage.this, android.R.layout.simple_list_item_1, enrolApps);
                    lt_view_apps.setAdapter(classarrayadapter);
                }else if (allorowned.equals("VIEW ALL")){
                    bt_member_viewApp.setText("VIEW Registered appointment");
                    displayAppsList(database);
                }
            }
        });
        lt_view_apps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Appointment clickedApp = (Appointment) adapterView.getItemAtPosition(i);
                List<Appointment>enrolApps = getEnrolAppsList(database,patientname);

                if(timeconflict(database,enrolApps,clickedApp)){
                    String currentpatient = clickedApp.getPatientname();
                    boolean x = false;
                    if (currentpatient.equals(patientname)){
                            x=true;
                    }

                    if (x){
                        finalBundle.putInt("appid",clickedApp.getAppid());
                        finalBundle.putString("patient",patientname);
                        finalBundle.putString("curpatient",currentpatient);
                        finalBundle.putString("status",clickedApp.getStatus());
                        finalBundle.putString("branch",clickedApp.getBranch());
                        finalBundle.putString("date",clickedApp.getDate());
                        finalBundle.putString("time",clickedApp.getTime());
                        finalBundle.putString("hours",clickedApp.getHours());
                        finalBundle.putString("type",clickedApp.getType());
                        finalBundle.putString("dentist",clickedApp.getDentistid());
                        finalBundle.putBoolean("enrolled",true);
                        Intent intent = new Intent(memberpage.this, EnrolPage.class);
                        intent.putExtras(finalBundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(memberpage.this,"This appointment has time conflict with enrolled appointment",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    finalBundle.putInt("appid",clickedApp.getAppid());
                    finalBundle.putString("patient",patientname);
                    finalBundle.putString("curpatient",clickedApp.getPatientname());
                    finalBundle.putString("status",clickedApp.getStatus());
                    finalBundle.putString("branch",clickedApp.getBranch());
                    finalBundle.putString("date",clickedApp.getDate());
                    finalBundle.putString("time",clickedApp.getTime());
                    finalBundle.putString("hours",clickedApp.getHours());
                    finalBundle.putString("type",clickedApp.getType());
                    finalBundle.putString("dentist",clickedApp.getDentistid());
                    finalBundle.putBoolean("enrolled",false);
                    Intent intent = new Intent(memberpage.this, EnrolPage.class);
                    intent.putExtras(finalBundle);
                    startActivity(intent);
                }

            }
        });
    }
    private void displayAppsList(DatabaseHelper databaseHelper) {
        List<Appointment> allapps = databaseHelper.getAll();
        ArrayAdapter classarrayadapter = new ArrayAdapter<Appointment>(memberpage.this, android.R.layout.simple_list_item_1, allapps);
        lt_view_apps.setAdapter(classarrayadapter);
    }

    private List<Appointment> getEnrolAppsList(DatabaseHelper databaseHelper, String patientname) {
        List<Appointment> enrolapps = databaseHelper.getRegisterApp(patientname);
        //ArrayAdapter classarrayadapter = new ArrayAdapter<ClassModel>(memberpage.this, android.R.layout.simple_list_item_1, enrolclass);
        return enrolapps;
    }
    private boolean timeconflict(DatabaseHelper databaseHelper,List<Appointment> EnrolClassList, Appointment classModel){
        for (int i = 0; i < EnrolClassList.size();i++){
            String exdate = EnrolClassList.get(i).getDate();
            String curdate = classModel.getDate();
            if(exdate.equals(curdate)){
                String ets = EnrolClassList.get(i).getTime();
                int hours = Integer.parseInt(EnrolClassList.get(i).getHours());
                String ct = classModel.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                Date extime = null;
                Date curtime = null;
                Date extimel = null;
                try {
                    extime = sdf.parse(ets);
                    curtime = sdf.parse(ct);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(extime);
                    calendar.add(Calendar.HOUR_OF_DAY, hours);
                    extimel = calendar.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!extime.after(curtime) && !extimel.before(curtime)) {
                    return true;
                }
            }
        }

        return false;
    }

}
