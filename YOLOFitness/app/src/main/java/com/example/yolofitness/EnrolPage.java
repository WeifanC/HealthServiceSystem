package com.example.yolofitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnrolPage extends AppCompatActivity {
    public Button confirm,back;
    public TextView courseinfo,hint;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("user");
        int classid = bundle.getInt("classid");
        String classname = bundle.getString("classname");
        String classdes = bundle.getString("classdes");
        String classdifficult = bundle.getString("diff");
        String classdate = bundle.getString("date");
        String classtime = bundle.getString("time");
        int hours = Integer.parseInt(bundle.getString("hours"));
        String capacity = bundle.getString("capacity");
        String[] memberslist = bundle.getStringArray("membername");
        databaseHelper = new DatabaseHelper(this);

        setContentView(R.layout.activity_memberenrolpage);
        confirm = findViewById(R.id.bt_enrolconfirm);
        back = findViewById(R.id.bt_enrolpageback);
        courseinfo = findViewById(R.id.tx_cklassinfo);
        hint = findViewById(R.id.tx_hitmsg);
        courseinfo.setText("Class Name: "+ classname +"\n" + "Class Description: " + classdes + "\n" +
                "Class Difficult: " + classdifficult + "\n" + "Class date: "+ classdate + "\n" +
                "Class time: " + classtime + "\n" +
                "Class Hour: " + hours + "\n" + "Capacity: " + capacity);
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
                    int index = 0;
                    for (int i =0; i<memberslist.length;i++){
                        if (memberslist[i].equals(username)){
                            memberslist[i] = "";
                        }
                    }

                    databaseHelper.UpdateMemberList(classid,memberslist);

                }else{

                    int membernumber = memberslist.length;
                    if (membernumber <= Integer.parseInt(capacity)){
                        if (memberslist[0].equals("")){
                            memberslist[0] = username;
                            databaseHelper.UpdateMemberList(classid,memberslist);
                        }else{
                            String arrNew[] = new String[membernumber + 1];
                            int i;
                            for(i = 0; i < memberslist.length; i++) {
                                arrNew[i] = memberslist[i];
                            }
                            arrNew[i] = username;
                        }


                    }else{
                        Toast.makeText(EnrolPage.this,"This class is full, Please try others",Toast.LENGTH_SHORT).show();
                    }
                }

                Intent intent = new Intent(EnrolPage.this, memberpage.class);
                Bundle finalBundle = new Bundle();;
                finalBundle.putString("user",username);
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
