package com.example.yolofitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class checkmember extends AppCompatActivity {
    private Button bt_back;
    private TextView tx_memberlist;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String[] members = bundle.getStringArray("members");
        setContentView(R.layout.activity_checkmember);
        databaseHelper = new DatabaseHelper(this);
        bt_back = findViewById(R.id.bt_checkback);
        tx_memberlist = findViewById(R.id.tx_memberlist);
        String tx = "";
        for (int i = 0; i<members.length;i++){
            tx = tx + members[i] + "\n";
        }
        tx_memberlist.setText(tx);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(checkmember.this, modifycourse_page.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
