package com.example.yolofitness;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class SignUpPage extends AppCompatActivity {
    public EditText signUpName,signUpSSN,signUpAddress,signUpAge,signUpGender,signUpBirth, signUpInsurance;
    public Switch sign_identity;


    public Button signupConfirm;
    DatabaseHelper database;


    /**
     * sign up page create account&display relative message
     * @param savedInstanceState
     */

    @Override// Signup page on click and get input.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        signUpName = (EditText)findViewById(R.id.signuppage_name);
        signUpSSN = (EditText)findViewById(R.id.signuppage_SSN);
        signUpAddress = (EditText)findViewById(R.id.signuppage_address);
        signUpAge = (EditText)findViewById(R.id.signuppage_age);
        signUpGender = (EditText)findViewById(R.id.signuppage_gender);
        signUpBirth = (EditText)findViewById(R.id.signuppage_birth);
        signUpInsurance = (EditText)findViewById(R.id.signuppage_phonenumber);
        sign_identity = (Switch) findViewById(R.id.sw_signup);
        signupConfirm = (Button) findViewById(R.id.confirmbotton);
        database = new DatabaseHelper(this);
        signupConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean switchState = sign_identity.isChecked();
                String identify;
                if (switchState){
                    identify = "employee";
                }else{
                    identify = "patient";
                }
                UserInfo userModel;
                try {
                    userModel = new UserInfo(-1,signUpName.getText().toString(),signUpSSN.getText().toString(),signUpAddress.getText().toString(),signUpAge.getText().toString(),signUpGender.getText().toString(),signUpBirth.getText().toString(),signUpInsurance.getText().toString(),identify);
                }catch (Exception e){
                    Toast.makeText(SignUpPage.this, "invalid input", Toast.LENGTH_SHORT).show();
                    userModel = new UserInfo(-1,"error","error","error","error","error","error","error","error");
                }
                boolean verifyacc = database.Verify_Account(userModel.getSSN());
                if(verifyacc == false) {
                    boolean insert = database.addUser(userModel);
                    if (insert == true){
                        Toast.makeText(SignUpPage.this,"registered completed",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(SignUpPage.this,"registered failed",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText((SignUpPage.this), "you already registered", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }






}