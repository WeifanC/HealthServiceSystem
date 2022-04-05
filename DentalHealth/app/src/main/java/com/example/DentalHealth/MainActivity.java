package com.example.DentalHealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public EditText SSN;
    public Button logInbotton,signupbotton, forgetBotton;
    public Switch login_indentity;
    public String ssn,identify;
    boolean switchState;
    DatabaseHelper database;

    /**
     * main page for user to log in & create account
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SSN = (EditText)findViewById(R.id.signin_SSN);
        login_indentity = (Switch)findViewById(R.id.sw_login);
        switchState = login_indentity.getShowText();
        logInbotton = (Button) findViewById(R.id.logIn);
        signupbotton =(Button) findViewById(R.id.signup);
        database = new DatabaseHelper(this);

        signupbotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(intent);
            }
        });
/**
 * user select identity (employee/ patient) and log in
 */
        logInbotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ssn = SSN.getText().toString();
                switchState = login_indentity.isChecked();
                if (switchState){
                    identify = "employee";
                }else{
                    identify = "patient";
                }
                Intent intent;
                //pass information to another activity
                Bundle bundle = new Bundle();
                boolean verify;
                if (identify == "employee"){
                    verify = database.Verify_identity(ssn,identify);
                }else{
                    verify = database.Verify_Account(ssn);
                }
                if ((ssn.equals("admin"))) {
                    intent = new Intent(MainActivity.this, AddScheduletPage.class);
                    startActivity(intent);
                }else if (verify == true){
                    Toast.makeText(MainActivity.this,"Login successfully",Toast.LENGTH_LONG).show();
                    bundle.putString("SSN",ssn);
                    bundle.putString("identify", identify);
                    intent = new Intent(MainActivity.this, Displaypage.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"Information incorrected",Toast.LENGTH_LONG).show();
                }


            }



        });


    }

}





