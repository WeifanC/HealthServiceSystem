package com.example.yolofitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.renderscript.*;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public EditText UserID, Password;
    public Button logInbotton,signupbotton, forgetBotton;
    public Switch login_indentity;
    public String id,password,identify;
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
        UserID = (EditText)findViewById(R.id.User);
        Password = (EditText)findViewById(R.id.password);
        login_indentity = (Switch)findViewById(R.id.sw_login);
        switchState = login_indentity.getShowText();
        logInbotton = (Button) findViewById(R.id.logIn);
        signupbotton =(Button) findViewById(R.id.signup);
        forgetBotton = (Button) findViewById(R.id.forget);
        database = new DatabaseHelper(this);

        signupbotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(intent);
            }
        });
/**
 * user select different identity
 */
        logInbotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id =UserID.getText().toString();
                password = Password.getText().toString();
                switchState = login_indentity.isChecked();
                if (switchState){
                    identify = "Instructor";
                }else{
                    identify = "Member";
                }
                Intent intent;
                //pass information to another activity
                Bundle bundle = new Bundle();
                boolean verify = database.Verify_password(id,password,identify);
                if ((id.equals("admin")) && (password.equals("admin123"))) {
                    intent = new Intent(MainActivity.this, AddClassPage.class);
                    startActivity(intent);
                }else if (verify == true){
                    Toast.makeText(MainActivity.this,"Login successfully",Toast.LENGTH_LONG).show();
                    bundle.putString("user", id);
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





