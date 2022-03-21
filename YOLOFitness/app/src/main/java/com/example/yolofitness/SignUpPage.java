package com.example.yolofitness;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;


public class SignUpPage extends AppCompatActivity {
    public EditText AccountNum, signupPassword;
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
        AccountNum = (EditText)findViewById(R.id.signuppage_user);
        signupPassword = (EditText)findViewById(R.id.signuppage_password);
        sign_identity = (Switch) findViewById(R.id.sw_signup);
        signupConfirm = (Button) findViewById(R.id.confirmbotton);
        database = new DatabaseHelper(this);
        signupConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean switchState = sign_identity.isChecked();
                String identify;
                if (switchState){
                    identify = "Instructor";
                }else{
                    identify = "Member";
                }
                UserModel userModel;
                try {
                    userModel = new UserModel(-1, AccountNum.getText().toString(), signupPassword.getText().toString(),identify);
                }catch (Exception e){
                    Toast.makeText(SignUpPage.this, "invalid input", Toast.LENGTH_SHORT).show();
                    userModel = new UserModel(-1,"error","error","error");
                }
                boolean verifyacc = database.Verify_Account(userModel.getUsername());
                if(verifyacc == false) {
                    boolean insert = database.addUser(userModel);
                    if (insert == true){
                        Toast.makeText(SignUpPage.this,"registered complete",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(SignUpPage.this,"registered failed",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText((SignUpPage.this), "please try different account", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }






}