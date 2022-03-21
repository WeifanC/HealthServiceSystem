package com.example.yolofitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class memberpage extends AppCompatActivity {
    public Button member_searchbutton,bt_member_viewclass;
    public EditText member_searchbox;
    public ListView lt_view_class;
    private String membername;
    DatabaseHelper database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberview_page);
        member_searchbutton = (Button) findViewById(R.id.member_searchbutton);
        bt_member_viewclass = (Button) findViewById(R.id.member_viewclass);
        member_searchbox = (EditText) findViewById(R.id.member_searchbox);
        lt_view_class = (ListView) findViewById(R.id.lt_view_class);
        database = new DatabaseHelper(this);
        displayClassList(database);
        Bundle bundle = getIntent().getExtras();
        membername = bundle.getString("user");
        Bundle finalBundle = bundle;

        member_searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classn = member_searchbox.getText().toString();
                if (classn != null){
                    List<ClassModel> search = database.search(classn);
                    List<ClassModel> searchd = database.searchdate(classn);
                    search.addAll(searchd);
                    ArrayAdapter classarrayadapter = new ArrayAdapter<ClassModel>(memberpage.this, android.R.layout.simple_list_item_1, search);
                    lt_view_class.setAdapter(classarrayadapter);
                }else{
                    Toast.makeText(memberpage.this, "N/A", Toast.LENGTH_SHORT).show();
                    displayClassList(database);
                }

            }
        });
        bt_member_viewclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String allorowned = bt_member_viewclass.getText().toString();
                if (allorowned.equals("VIEW ENROLLED CLASS")){
                    bt_member_viewclass.setText("VIEW ALL");
                    List<ClassModel> enrolclass = getEnrolClassList(database,membername);
                    ArrayAdapter classarrayadapter = new ArrayAdapter<ClassModel>(memberpage.this, android.R.layout.simple_list_item_1, enrolclass);
                    lt_view_class.setAdapter(classarrayadapter);
                }else if (allorowned.equals("VIEW ALL")){
                    bt_member_viewclass.setText("VIEW ENROLLED CLASS");
                    displayClassList(database);
                }
            }
        });
        lt_view_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClassModel clickedClass = (ClassModel) adapterView.getItemAtPosition(i);
                List<ClassModel>enrolclass = getEnrolClassList(database,membername);

                if(timeconflict(database,enrolclass,clickedClass)){
                    String[] memberlist = clickedClass.getStudentname();
                    boolean x = false;
                    for (int j =0; j<memberlist.length;j++){
                        if (memberlist[j].equals(membername)){
                            x=true;
                        }
                    }
                    if (x){
                        finalBundle.putInt("classid",clickedClass.getId());
                        finalBundle.putString("classname",clickedClass.getName());
                        finalBundle.putString("classdes",clickedClass.getDescription());
                        finalBundle.putString("diff",clickedClass.getDifficulty());
                        finalBundle.putString("date",clickedClass.getDate());
                        finalBundle.putString("time",clickedClass.getTime());
                        finalBundle.putString("hours",clickedClass.getHours());
                        finalBundle.putString("capacity",clickedClass.getCapacity());
                        finalBundle.putStringArray("membername",clickedClass.getStudentname());
                        finalBundle.putBoolean("enrolled",true);
                        Intent intent = new Intent(memberpage.this, EnrolPage.class);
                        intent.putExtras(finalBundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(memberpage.this,"This Class has time conflict with enrolled classes",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    finalBundle.putInt("classid",clickedClass.getId());
                    finalBundle.putString("classname",clickedClass.getName());
                    finalBundle.putString("classdes",clickedClass.getDescription());
                    finalBundle.putString("diff",clickedClass.getDifficulty());
                    finalBundle.putString("date",clickedClass.getDate());
                    finalBundle.putString("time",clickedClass.getTime());
                    finalBundle.putString("hours",clickedClass.getHours());
                    finalBundle.putString("capacity",clickedClass.getCapacity());
                    finalBundle.putStringArray("membername",clickedClass.getStudentname());
                    finalBundle.putBoolean("enrolled",false);
                    Intent intent = new Intent(memberpage.this, EnrolPage.class);
                    intent.putExtras(finalBundle);
                    startActivity(intent);
                }

            }
        });
    }
    private void displayClassList(DatabaseHelper databaseHelper) {
        List<ClassModel> allclass = databaseHelper.getAll();
        ArrayAdapter classarrayadapter = new ArrayAdapter<ClassModel>(memberpage.this, android.R.layout.simple_list_item_1, allclass);
        lt_view_class.setAdapter(classarrayadapter);
    }

    private List<ClassModel> getEnrolClassList(DatabaseHelper databaseHelper,String username) {
        List<ClassModel> enrolclass = databaseHelper.enroledClass(username);
        //ArrayAdapter classarrayadapter = new ArrayAdapter<ClassModel>(memberpage.this, android.R.layout.simple_list_item_1, enrolclass);
        return enrolclass;
    }
    private boolean timeconflict(DatabaseHelper databaseHelper,List<ClassModel> EnrolClassList, ClassModel classModel){
        for (int i = 0; i < EnrolClassList.size();i++){
            String exdate = EnrolClassList.get(i).getDate();
            String curdate = classModel.getDate();
            if(exdate.equals(curdate)){
                String ets = EnrolClassList.get(i).getTime();
                int hours = Integer.parseInt(EnrolClassList.get(i).getHours());
                String ct = classModel.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                Date extime = null;
                Date curtime = null;
                Date extimel = null;
                try {
                    extime = sdf.parse(ets);
                    curtime = sdf.parse(ct);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(extime);
                    calendar.add(Calendar.HOUR_OF_DAY, hours);
                    extimel = calendar.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!extime.after(curtime) && !extimel.before(curtime)) {
                    return true;
                }
            }
        }

        return false;
    }

}
