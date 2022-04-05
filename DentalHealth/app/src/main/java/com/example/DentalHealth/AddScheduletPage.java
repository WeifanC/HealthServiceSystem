package com.example.DentalHealth;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddScheduletPage extends AppCompatActivity {

    public Button bt_add, bt_viewall,bt_modifyAccount;
    public EditText tx_classname,tx_description,tx_difficulty,tx_time;
    public ListView list_user;
    public DatabaseHelper database;

    /**
     * deleting all account message
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addschedulepage);
        bt_viewall = findViewById(R.id.bt_viewall);
        list_user = findViewById(R.id.list_class);
        bt_modifyAccount = findViewById(R.id.modifyAccount);

        database = new DatabaseHelper(this);
        displayUserList(database);
        bt_modifyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
               DatabaseHelper databaseHelper = new DatabaseHelper(AddScheduletPage.this);
               Toast.makeText(AddScheduletPage.this,"Both patient and  employee Account has been deleted",Toast.LENGTH_LONG).show();   /**修改*/


           }
         });
/**
 * Database onclickclick
 */
        bt_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper  databaseHelper = new DatabaseHelper(AddScheduletPage.this);

                displayUserList(databaseHelper);



            }
        });

        /**
         * admin able to delete appointment  onclick
         *
         */
        list_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserInfo clickedUser = (UserInfo) adapterView.getItemAtPosition(i);
                database.deleteoneUser(clickedUser);
                displayUserList(database);
                Toast.makeText(AddScheduletPage.this, "Deleted " + clickedUser.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * patient/dentist display
     * @param databaseHelper
     */
    private void displayUserList(DatabaseHelper databaseHelper) {
        List<UserInfo> allUser = databaseHelper.getAllUser();
        ArrayAdapter Userarrayadapter = new ArrayAdapter<UserInfo>(AddScheduletPage.this, android.R.layout.simple_list_item_1, allUser);
        list_user.setAdapter(Userarrayadapter);
    }
}
