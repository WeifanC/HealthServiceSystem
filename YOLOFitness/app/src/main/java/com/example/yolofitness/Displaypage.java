package com.example.yolofitness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

import java.util.Timer;
import java.util.TimerTask;

public class Displaypage extends AppCompatActivity {
    public String ID,identity;
    public TextView msg_welcome;
    public Button bt_member, bt_instructor;
    Timer timer;


    /**
     * display message when user log in
     * @param savedInstanceState
     * @return null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaypage);
        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("user");
        identity = bundle.getString("identify");
        msg_welcome = (TextView) findViewById(R.id.textView2);
        msg_welcome.setText("Welcome "+ ID + ", You are now successfully sign in as "+ identity+".");
        timer = new Timer();
        Bundle finalBundle = bundle;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (identity.equals("Instructor")){
                    Intent intent = new Intent(Displaypage.this,instructor_page.class);
                    intent.putExtras(finalBundle);
                    startActivity(intent);
                }else{
                    // member page
                    Intent intent = new Intent(Displaypage.this,memberpage.class);
                    intent.putExtras(finalBundle);
                    startActivity(intent);
                }

            }
        },3000);

    }
}