package com.example.yolofitness;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import androidx.annotation.Nullable;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String CLASS_TABLE = "CLASS_TABLE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CLASS_NAME = "CLASS_NAME";
    public static final String COLUMN_CLASS_DESCRIPT = "CLASS_DESCRIPT";
    public static final String COLUMN_INSTRUCTOR = "INSTRUCTOR";
    public static final String COLUMN_DIFFICULT = "DIFFICULT";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_HOUR = "HOUR";
    public static final String COLUMN_CAP = "CAP";
    public static final String COLUMN_MEMBERS = "MEMBERS";
    public static final String COLUMN_ID2 = "ID2";
    public static final String COLUMN_USER = "USER";
    public static final String COLUMN_PASS = "PASS";
    public static final String COLUMN_IDENTITY = "IDENTITY";





    public DatabaseHelper(@Nullable Context context) {

        super(context, "Database.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        String classtable = "CREATE TABLE " + CLASS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CLASS_NAME + " TEXT, " + COLUMN_CLASS_DESCRIPT + " TEXT, " + COLUMN_INSTRUCTOR + " TEXT, " + COLUMN_DIFFICULT + " TEXT, "+ COLUMN_DATE + " TEXT, " +  COLUMN_TIME + " TEXT, " + COLUMN_HOUR + " TEXT, " + COLUMN_CAP + " TEXT, " + COLUMN_MEMBERS + " TEXT)";
        String usertable = "CREATE TABLE " + USER_TABLE + "(" + COLUMN_ID2 +  " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER + " TEXT, " + COLUMN_PASS + " TEXT, " + COLUMN_IDENTITY +" TEXT)";
        sqldb.execSQL(usertable);
        sqldb.execSQL(classtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int i, int i1) {
        sqldb.execSQL("DROP TABLE if exists " + CLASS_TABLE);
        sqldb.execSQL("DROP TABLE if exists " + USER_TABLE);
        onCreate(sqldb);


    }

    /**
     * adding data into database
     * @param classModel
     * @return boolean
     */
    public boolean addOne(ClassModel classModel) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues cvalue = new ContentValues();
        cvalue.put(COLUMN_CLASS_NAME, classModel.getName());
        cvalue.put(COLUMN_CLASS_DESCRIPT, classModel.getDescription());
        cvalue.put(COLUMN_INSTRUCTOR,classModel.getInstructor());
        cvalue.put(COLUMN_DIFFICULT,classModel.getDifficulty());
        cvalue.put(COLUMN_DATE,classModel.getDate());
        cvalue.put(COLUMN_TIME,classModel.getTime());
        cvalue.put(COLUMN_HOUR,classModel.getHours());
        cvalue.put(COLUMN_CAP,classModel.getCapacity());
        cvalue.put(COLUMN_MEMBERS, Arrays.toString(classModel.getStudentname()));

        long insert = sqldb.insert(CLASS_TABLE, null, cvalue);
        return insert != -1;

    }

    /**
     * Deleting data from database
     * @param classModel
     * @return boolean
     */
    public boolean deleteone(ClassModel classModel) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CLASS_TABLE + " WHERE " + COLUMN_ID + " = " + classModel.getId();
        Cursor cursor = sqldb.rawQuery(queryString, null);
        return cursor.moveToFirst();

    }

    /**
     * Instructor class Cancel function
     * @param id
     * @return null
     */
    public void cancelclass(int id) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CLASS_TABLE + " WHERE " + COLUMN_ID + " = " + id;
        sqldb.execSQL(queryString);
    }

    /**
     * list view getting data to database&
     * @return list
     */
    public List<ClassModel> getAll() {
        List<ClassModel> allList = new ArrayList<>();
        String queryString = "SELECT * FROM " + CLASS_TABLE;
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor cursor = sqldb.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int classID = cursor.getInt(0);
                String className = cursor.getString(1);
                String classDes = cursor.getString(2);
                String classdifficulty=cursor.getString(4);
                String classinstructor = cursor.getString(3);
                String classdate=cursor.getString(5);
                String classtime=cursor.getString(6);
                String classhours=cursor.getString(7);
                String classcap=cursor.getString(8);
                String classmember=cursor.getString(9);

                if (classmember.contains(",")){
                    String[] membersname = classmember.split(",",0);
                    for (int i =0; i<membersname.length;i++){membersname[i] = membersname[i].replaceAll("[^a-zA-Z0-9]", "" );}
                    ClassModel newclass = new ClassModel(classID, className, classDes,classinstructor,classdifficulty,classdate,classtime,classhours,classcap,membersname);
                    allList.add(newclass);
                }else{
                    String[] membersname = new String[1];
                    membersname[0] = classmember.replaceAll("[^a-zA-Z0-9]", "" );
                    ClassModel newclass = new ClassModel(classID, className, classDes,classinstructor,classdifficulty,classdate,classtime,classhours,classcap,membersname);
                    allList.add(newclass);
                }

            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        sqldb.close();
        return allList;
    }

    public List<ClassModel> getOwnedClass(String instructor){
        List<ClassModel> ownclass = new ArrayList<>();
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor oc = sqldb.rawQuery("SELECT * FROM CLASS_TABLE where INSTRUCTOR = ?", new String[]{instructor});
        if (oc.moveToFirst()) {
            do {
                int classID = oc.getInt(0);
                String className = oc.getString(1);
                String classDes = oc.getString(2);
                String classdifficulty=oc.getString(4);
                String classinstructor = oc.getString(3);
                String classdate=oc.getString(5);
                String classtime=oc.getString(6);
                String classhours=oc.getString(7);
                String classcap=oc.getString(8);
                String classmember=oc.getString(9);
                if (classmember.contains(",")){
                    String[] membersname = classmember.split(", ",0);
                    for (int i =0; i<membersname.length;i++){membersname[i] = membersname[i].replaceAll("[^a-zA-Z0-9]", "" );}
                    ClassModel newclass = new ClassModel(classID, className, classDes,classinstructor,classdifficulty,classdate,classtime,classhours,classcap,membersname);
                    ownclass.add(newclass);
                }else{
                    String[] membersname = new String[1];
                    membersname[0] = classmember.replaceAll("[^a-zA-Z0-9]", "" );
                    ClassModel newclass = new ClassModel(classID, className, classDes,classinstructor,classdifficulty,classdate,classtime,classhours,classcap,membersname);
                    ownclass.add(newclass);
                }
            } while (oc.moveToNext());
        } else {

        }

        oc.close();
        sqldb.close();
        return ownclass;
    }

    public List<ClassModel> search(String classname){
        List<ClassModel> ownclass = new ArrayList<>();
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor oc = sqldb.rawQuery("SELECT * FROM CLASS_TABLE where CLASS_NAME = ? ", new String[]{classname});
        if (oc.moveToFirst()) {
            do {
                int classID = oc.getInt(0);
                String className = oc.getString(1);
                String classDes = oc.getString(2);
                String classdifficulty=oc.getString(4);
                String classinstructor = oc.getString(3);
                String classdate=oc.getString(5);
                String classtime=oc.getString(6);
                String classhours=oc.getString(7);
                String classcap=oc.getString(8);
                String classmember=oc.getString(9);
                if (classmember.contains(",")){
                    String[] membersname = classmember.split(", ",0);
                    for (int i =0; i<membersname.length;i++){membersname[i] = membersname[i].replaceAll("[^a-zA-Z0-9]", "" );}
                    ClassModel newclass = new ClassModel(classID, className, classDes,classinstructor,classdifficulty,classdate,classtime,classhours,classcap,membersname);
                    ownclass.add(newclass);
                }else{
                    String[] membersname = new String[1];
                    membersname[0] = classmember.replaceAll("[^a-zA-Z0-9]", "" );
                    ClassModel newclass = new ClassModel(classID, className, classDes,classinstructor,classdifficulty,classdate,classtime,classhours,classcap,membersname);
                    ownclass.add(newclass);
                }

            } while (oc.moveToNext());
        } else {

        }

        oc.close();
        sqldb.close();
        return ownclass;
    }
    public List<ClassModel> searchdate(String date){
        List<ClassModel> ownclass = new ArrayList<>();
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor oc = sqldb.rawQuery("SELECT * FROM CLASS_TABLE where DATE = ? ", new String[]{date});
        if (oc.moveToFirst()) {
            do {
                int classID = oc.getInt(0);
                String className = oc.getString(1);
                String classDes = oc.getString(2);
                String classdifficulty=oc.getString(4);
                String classinstructor = oc.getString(3);
                String classdate=oc.getString(5);
                String classtime=oc.getString(6);
                String classhours=oc.getString(7);
                String classcap=oc.getString(8);
                String classmember=oc.getString(9);
                if (classmember.contains(",")){
                    String[] membersname = classmember.split(", ",0);
                    for (int i =0; i<membersname.length;i++){membersname[i] = membersname[i].replaceAll("[^a-zA-Z0-9]", "" );}
                    ClassModel newclass = new ClassModel(classID, className, classDes,classinstructor,classdifficulty,classdate,classtime,classhours,classcap,membersname);
                    ownclass.add(newclass);
                }else{
                    String[] membersname = new String[1];
                    membersname[0] = classmember.replaceAll("[^a-zA-Z0-9]", "" );
                    ClassModel newclass = new ClassModel(classID, className, classDes,classinstructor,classdifficulty,classdate,classtime,classhours,classcap,membersname);
                    ownclass.add(newclass);
                }

            } while (oc.moveToNext());
        } else {

        }

        oc.close();
        sqldb.close();
        return ownclass;
    }

    /**
     * adding data to column user&storing to database
     * @param userModel
     * @return boolean
     */
    public boolean addUser(UserModel userModel) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COLUMN_USER, userModel.getUsername());
        value.put(COLUMN_PASS, userModel.getPassword());
        value.put(COLUMN_IDENTITY, userModel.getIdentity());
        long result = sqldb.insert(USER_TABLE, null, value);
        return result != -1;

    }

    /**
     * verify account when log in
     * @param username
     * @return boolean
     */
    public boolean Verify_Account(String username) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor member_Account = sqldb.rawQuery("SELECT * FROM USER_TABLE where USER = ?", new String[]{username});
        if (member_Account.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * verify password when log in
     * @param username
     * @param password
     * @param identity
     * @return boolean
     */
    public boolean Verify_password(String username, String password, String identity){
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor Accountinfo = sqldb.rawQuery("SELECT* FROM USER_TABLE where USER = ? and PASS = ? and IDENTITY = ?", new String[]{username, password, identity});
        if (Accountinfo.getCount()>0){
            return true;
        }else{
            return false;

        }
    }

    /**
     * verify classname
     * @param classname
     * @param instructor
     * @return boolean
     */
    public boolean Verify_Classname(String classname, String instructor) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor classn = sqldb.rawQuery("SELECT * FROM CLASS_TABLE where CLASS_NAME = ? and INSTRUCTOR = ?", new String[]{classname,instructor});
        if (classn.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * updating class when insturctor modifying class
     * @param id
     * @param instructor
     * @param difficult
     * @param date
     * @param time
     * @param hours
     * @param capacity
     * @return null
     */
    public void UpdateClass(int id, String instructor,String difficult,String date,String time,String hours,String capacity){
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String query = "UPDATE " + CLASS_TABLE + " SET " + COLUMN_INSTRUCTOR +
            " = '" + instructor + "' , " + COLUMN_DIFFICULT + " = '" + difficult + "' , " +
                COLUMN_DATE + " = '" + date + "' , " +COLUMN_TIME + " = '" + time + "' , " + COLUMN_HOUR + " = '" + hours + "' , " + COLUMN_CAP+ " = '" + capacity + "' WHERE " +  COLUMN_ID + "= '" + id +"'";
        sqldb.execSQL(query);
    }
    public void UpdateMemberList(int id, String[] member){
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String query = "UPDATE " + CLASS_TABLE + " SET " + COLUMN_MEMBERS+ " = '" + Arrays.toString(member) + "' WHERE " +  COLUMN_ID + "= '" + id +"'";
        sqldb.execSQL(query);
    }
    public List<ClassModel> enroledClass(String username){
        List<ClassModel> enroledclass = new ArrayList<>();
        String queryString = "SELECT * FROM " + CLASS_TABLE;
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor oc = sqldb.rawQuery(queryString, null);
        if (oc.moveToFirst()) {
            do {
                int classID = oc.getInt(0);
                String className = oc.getString(1);
                String classDes = oc.getString(2);
                String classdifficulty=oc.getString(4);
                String classinstructor = oc.getString(3);
                String classdate=oc.getString(5);
                String classtime=oc.getString(6);
                String classhours=oc.getString(7);
                String classcap=oc.getString(8);
                String classmember=oc.getString(9);
                if (classmember.contains(",")){
                    if (classmember.contains(username)){
                        String[] membersname = classmember.split(", ",0);
                        for (int i =0; i<membersname.length;i++){membersname[i] = membersname[i].replaceAll("[^a-zA-Z0-9]", "" );}
                        ClassModel newclass = new ClassModel(classID, className, classDes,classinstructor,classdifficulty,classdate,classtime,classhours,classcap,membersname);
                        enroledclass.add(newclass);
                    }
                }else{
                    if (classmember.contains(username)){
                        String[] membersname = new String[1];
                        membersname[0] = classmember.replaceAll("[^a-zA-Z0-9]", "" );
                        ClassModel newclass = new ClassModel(classID, className, classDes,classinstructor,classdifficulty,classdate,classtime,classhours,classcap,membersname);
                        enroledclass.add(newclass);
                    }
                }
            } while (oc.moveToNext());
        } else {

        }

        oc.close();
        sqldb.close();
        return enroledclass;
    }


}
