package com.example.yolofitness;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.time.LocalDate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddClassPage extends AppCompatActivity {

    public Button bt_add,bt_delete,bt_modifyAccount;
    public EditText tx_classname,tx_description,tx_difficulty,tx_time;
    public ListView list_class;
    public DatabaseHelper database;

    /**
     * deleting all account message
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclasspage);
        bt_add = findViewById(R.id.bt_add);
        bt_delete = findViewById(R.id.bt_viewall);
        tx_classname = findViewById(R.id.tx_classname);
        tx_description = findViewById(R.id.tx_description);
        list_class = findViewById(R.id.list_class);
        bt_modifyAccount = findViewById(R.id.modifyAccount);

        database = new DatabaseHelper(this);
        displayClassList(database);
        bt_modifyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
               DatabaseHelper databaseHelper = new DatabaseHelper(AddClassPage.this);
               Toast.makeText(AddClassPage.this,"Both users and instructors  Account has been deleted",Toast.LENGTH_LONG).show();


           }
         });

        /**
         * validating account message
         * @return null
         */
        bt_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ClassModel classModel;
                try {
                    String[] studentname = new String[1];
                    studentname[0] = "";
                    classModel = new ClassModel(-1, tx_classname.getText().toString(), tx_description.getText().toString(),"null","Easy","Monday","null","1","0",studentname);
                    Toast.makeText(AddClassPage.this, classModel.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(AddClassPage.this, "Error create class", Toast.LENGTH_SHORT).show();
                    String[] studentname = new String[1];
                    studentname[0] = "";
                    classModel = new ClassModel(-1,"error","no detail","error","error","null","null","null","null",studentname);
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(AddClassPage.this);
                boolean success = databaseHelper.addOne(classModel);
                Toast.makeText(AddClassPage.this, "Success: "+success, Toast.LENGTH_SHORT).show();

                displayClassList(databaseHelper);
            }
        });
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper  databaseHelper = new DatabaseHelper(AddClassPage.this);

                displayClassList(databaseHelper);



            }
        });

        /**
         * admin able to delete class onclick
         *
         */
        list_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClassModel clickedClass = (ClassModel) adapterView.getItemAtPosition(i);
                database.deleteone(clickedClass);
                displayClassList(database);
                Toast.makeText(AddClassPage.this, "Deleted " + clickedClass.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void displayClassList(DatabaseHelper databaseHelper) {
        List<ClassModel> allclass = databaseHelper.getAll();
        ArrayAdapter classarrayadapter = new ArrayAdapter<ClassModel>(AddClassPage.this, android.R.layout.simple_list_item_1, allclass);
        list_class.setAdapter(classarrayadapter);
    }
}
