package com.example.DentalHealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.DatePickerDialog;
import android.widget.Toast;


public class modifyApp_page extends AppCompatActivity {
    private Button Confirm,Cancel;
    DatabaseHelper databaseHelper;
    private static final String TAG = "TestDatePickerActivity";
    private TextView texttime,textpn,et_pn,et_dentist;
    private EditText et_time,et_branch;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private NumberPicker Hours = null;
    String[] statusArray = {"complete","incomplete"};
    String[] typeArray = {"scalping", "cleaning ", "Extrating"};
    String[] dateArray = {"Monday", "Tuesday ", "Wednesday","Thursday","Friday","Saturday","Sunday"};
    private int appid;
    private String patientname;
    private String branch;
    private String status;
    private String dentistid;
    private String A_date;
    private String A_time;
    private String A_hours;
    private String type;

    /**
     * onclick  to modify appointment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        appid = bundle.getInt("appid");
        patientname= bundle.getString("patientname");
        branch = bundle.getString("branch");
        status = bundle.getString("status");
        dentistid = bundle.getString("dentist");
        A_date = bundle.getString("date");
        A_time = bundle.getString("time");
        type = bundle.getString("type");
        int hours = Integer.parseInt(bundle.getString("hours"));
        setContentView(R.layout.activity_modifyapp_page);



        et_dentist = (TextView)findViewById(R.id.et_dentist);
        et_dentist.setText(dentistid);
        et_branch = (EditText) findViewById(R.id.et_branch);
        et_branch.setText(branch);
        textpn =(TextView) findViewById(R.id.tx_Patientname);
        texttime =(TextView) findViewById(R.id.tx_starttime);
        et_time = (EditText) findViewById(R.id.et_starttime);
        et_time.setText(A_time);
        et_pn = (TextView) findViewById(R.id.et_patientname);
        et_pn.setText(patientname);
        Confirm = (Button) findViewById(R.id.modify_confirm);
        Cancel = (Button) findViewById(R.id.modify_cancel);
        databaseHelper = new DatabaseHelper(this);


        Hours = (NumberPicker) findViewById(R.id.modify_hours);
        Hours.setMinValue(1);
        Hours.setMaxValue(5);
        Hours.setValue(hours);
        Spinner spinnerType = findViewById(R.id.spinner_type);
        ArrayAdapter<String> stype = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, typeArray);
        spinnerType.setAdapter(stype);
        for (int i =0; i<typeArray.length;i++){
            if (type.equals(typeArray[i])){
                spinnerType.setSelection(i);
            }else{
                spinnerType.setSelection(0);
            }
        }
        spinnerType.setOnItemSelectedListener(new MytypeOnItemSelectedListener());

        Spinner spinnerStatus = findViewById(R.id.spinner_Status);
        ArrayAdapter<String> spstatus = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, statusArray);
        spinnerStatus.setAdapter(spstatus);
        for (int i =0; i<statusArray.length;i++){
            if (status.equals(statusArray[i])){
                spinnerStatus.setSelection(i);
            }else{
                spinnerStatus.setSelection(0);
            }
        }
        spinnerStatus.setOnItemSelectedListener(new MystatusOnItemSelectedListener());

        Spinner spinnerdate = findViewById(R.id.spinner_date);
        ArrayAdapter<String> date = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dateArray);
        spinnerdate.setAdapter(date);
        for (int i =0; i<dateArray.length;i++){
            if (date.equals(dateArray[i])){
                spinnerdate.setSelection(i);
            }else{
                spinnerdate.setSelection(0);
            }
        }
        spinnerdate.setOnItemSelectedListener(new MydateOnItemSelectedListener());
//        check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(modifycourse_page.this, checkmember.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });


        /**
         * update data to database
         * @return null
         */
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientname = et_pn.getText().toString();
                A_time = et_time.getText().toString();
                branch = et_branch.getText().toString();

                Date time = null;
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                try {
                    time = sdf.parse(A_time);
                } catch (ParseException e) {
                    Toast.makeText(modifyApp_page.this, "Class time incorrect formula", Toast.LENGTH_SHORT).show();
                }
                if (time != null){
                    if (A_hours == null){
                        A_hours = hours+"";
                    }
                    databaseHelper.UpdateApp(appid,branch,status,dentistid,A_date,A_time,A_hours,type);
                    Bundle finalBundle = new Bundle();;
                    finalBundle.putString("dentist",dentistid);
                    Intent intent = new Intent(modifyApp_page.this, Dentist_page.class);
                    intent.putExtras(finalBundle);
                    startActivity(intent);
                }else {
                    Toast.makeText(modifyApp_page.this,"Incorrect formula",Toast.LENGTH_SHORT).show();
                }
                }

        });
            /**
             * cancel appointment
             */
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.cancelAppointment(appid);
                Bundle finalBundle = new Bundle();;
                finalBundle.putString("dentist",dentistid);
                Intent intent = new Intent(modifyApp_page.this, Dentist_page.class);
                intent.putExtras(finalBundle);
                Toast.makeText(modifyApp_page.this, "This appointment is cancelled", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        /**
         * display message
         */
        Hours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                A_hours=newVal+"";
                Toast.makeText(modifyApp_page.this, "This appointment is " + A_hours + " hour long.", Toast.LENGTH_SHORT).show();   /**修改*/
            }
        });

   }

    /**
     * onselected method to select correct info
     */
        class MytypeOnItemSelectedListener implements AdapterView.OnItemSelectedListener{
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type=typeArray[i];

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        }
        class MystatusOnItemSelectedListener implements AdapterView.OnItemSelectedListener{
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status=statusArray[i];

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        }
        class MydateOnItemSelectedListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            A_date=dateArray[i];
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }
    }



