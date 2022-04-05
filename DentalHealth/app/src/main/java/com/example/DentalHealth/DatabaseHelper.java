package com.example.DentalHealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * app database
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String APP_TABLE = "APP_TABLE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PATIENT = "PATIENTNAME";
    public static final String COLUMN_BRANCH = "BRANCH";
    public static final String COLUMN_DENTIST = "DENTIST";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_HOUR = "HOUR";
    public static final String COLUMN_TYPE = "TYPE";
    public static final String COLUMN_PRICE = "PRICE";

    public static final String COLUMN_ID2 = "ID2";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_SSN = "SSN";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_AGE = "AGE";
    public static final String COLUMN_GENDER = "GENDER";
    public static final String COLUMN_BIRTH = "BIRTH";
    public static final String COLUMN_INSURANCE = "INSURANCE";
    public static final String COLUMN_IDENTITY = "IDENTITY";


    public DatabaseHelper(@Nullable Context context) {

        super(context, "Database.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        String classtable = "CREATE TABLE " + APP_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PATIENT + " TEXT, " + COLUMN_BRANCH + " TEXT, " + COLUMN_STATUS + " TEXT, " + COLUMN_DENTIST + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_TIME + " TEXT, " + COLUMN_HOUR + " TEXT, " + COLUMN_TYPE + " TEXT, "+ COLUMN_PRICE + " TEXT)";
        String usertable = "CREATE TABLE " + USER_TABLE + "(" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_SSN + " TEXT, " + COLUMN_ADDRESS + " TEXT, " + COLUMN_GENDER + " TEXT, " + COLUMN_AGE + " TEXT, " + COLUMN_BIRTH + " TEXT, " + COLUMN_INSURANCE + " TEXT, " + COLUMN_IDENTITY + " TEXT)";
        sqldb.execSQL(usertable);
        sqldb.execSQL(classtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int i, int i1) {
        sqldb.execSQL("DROP TABLE if exists " + APP_TABLE);
        sqldb.execSQL("DROP TABLE if exists " + USER_TABLE);
        onCreate(sqldb);


    }

    /**
     * adding data into database
     *
     * @param appointment
     * @return boolean
     */

    public boolean addOne(Appointment appointment) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues cvalue = new ContentValues();
        cvalue.put(COLUMN_PATIENT, appointment.getPatientname());
        cvalue.put(COLUMN_BRANCH, appointment.getBranch());
        cvalue.put(COLUMN_STATUS, appointment.getStatus());
        cvalue.put(COLUMN_DENTIST, appointment.getDentistid());
        cvalue.put(COLUMN_DATE, appointment.getDate());
        cvalue.put(COLUMN_TIME, appointment.getTime());
        cvalue.put(COLUMN_HOUR, appointment.getHours());
        cvalue.put(COLUMN_TYPE, appointment.getType());
        cvalue.put(COLUMN_PRICE, appointment.getPrice());

        long insert = sqldb.insert(APP_TABLE, null, cvalue);
        return insert != -1;

    }

    /**
     * Getting name and store to database
     * @param SSN
     * @return
     */
    public String getName(String SSN) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor oc = sqldb.rawQuery("SELECT * FROM USER_TABLE where SSN = ?", new String[]{SSN});
        String name = "";
        if (oc.moveToFirst()) {
            do {
                name = oc.getString(1);
            } while (oc.moveToNext());
        } else {

        }
        oc.close();
        sqldb.close();
        return name;
    }

    /**
     * Deleting data from database
     *
     * @param appointment
     * @return boolean
     */
    public boolean deleteone(Appointment appointment) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String queryString = "DELETE FROM " + APP_TABLE + " WHERE " + COLUMN_ID + " = " + appointment.getAppid();
        Cursor cursor = sqldb.rawQuery(queryString, null);
        return cursor.moveToFirst();

    }

    /**
     * Deleting data from database
     *
     * @param userinfo
     * @return boolean
     */
    public boolean deleteoneUser(UserInfo userinfo) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String queryString = "DELETE FROM " + USER_TABLE + " WHERE " + COLUMN_ID2 + " = " + userinfo.getId();
        Cursor cursor = sqldb.rawQuery(queryString, null);
        return cursor.moveToFirst();

    }

    /**
     * Instructor class Cancel function
     *
     * @param id
     * @return null
     */
    public void cancelAppointment(int id) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String queryString = "DELETE FROM " + APP_TABLE + " WHERE " + COLUMN_ID + " = " + id;
        sqldb.execSQL(queryString);
    }

    /**
     * list view getting data to database&
     *
     * @return list
     */
    public List<Appointment> getAll() {
        List<Appointment> allList = new ArrayList<>();
        String queryString = "SELECT * FROM " + APP_TABLE;
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor cursor = sqldb.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int APPID = cursor.getInt(0);
                String patientname = cursor.getString(1);
                String branch = cursor.getString(2);
                String status = cursor.getString(3);
                String dentistname = cursor.getString(4);
                String appdate = cursor.getString(5);
                String apptime = cursor.getString(6);
                String apphours = cursor.getString(7);
                String apptype = cursor.getString(8);
                String appPrice = cursor.getString(9);

                Appointment newapp = new Appointment(APPID, patientname, branch, status, dentistname, appdate, apptime, apphours, apptype,appPrice);
                allList.add(newapp);

            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        sqldb.close();
        return allList;
    }

    /**
     * viewing the database and display
     * @return
     */
    public List<UserInfo> getAllUser() {
        List<UserInfo> allList = new ArrayList<>();
        String queryString = "SELECT * FROM " + USER_TABLE;
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor cursor = sqldb.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {

                int userid = cursor.getInt(0);
                String name = cursor.getString(1);
                String ssn = cursor.getString(2);
                String address = cursor.getString(3);
                String age = cursor.getString(4);
                String gender = cursor.getString(5);
                String birth = cursor.getString(6);
                String phone = cursor.getString(7);
                String identity = cursor.getString(8);

                UserInfo newuser = new UserInfo(userid, name, ssn, address, age, gender, birth, phone, identity);
                allList.add(newuser);

            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        sqldb.close();
        return allList;
    }

    public List<Appointment> getRegisterApp(String patient) {
        List<Appointment> registerApps = new ArrayList<>();
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor oc = sqldb.rawQuery("SELECT * FROM APP_TABLE where PATIENTNAME = ?", new String[]{patient});
        if (oc.moveToFirst()) {
            do {
                int APPID = oc.getInt(0);
                String patientname = oc.getString(1);
                String branch = oc.getString(2);
                String status = oc.getString(4);
                String dentistname = oc.getString(3);
                String appdate = oc.getString(5);
                String apptime = oc.getString(6);
                String apphours = oc.getString(7);
                String apptype = oc.getString(8);
                String appPrice = oc.getString(9);
                Appointment newapp = new Appointment(APPID, patientname, branch, status, dentistname, appdate, apptime, apphours, apptype,appPrice);
                registerApps.add(newapp);
            } while (oc.moveToNext());
        } else {

        }

        oc.close();
        sqldb.close();
        return registerApps;
    }

    /**
     * adding data to column user&storing to database
     *
     * @param userModel
     * @return boolean
     */
    public boolean addUser(UserInfo userModel) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COLUMN_NAME, userModel.getname());
        value.put(COLUMN_SSN, userModel.getSSN());
        value.put(COLUMN_ADDRESS, userModel.getAddress());
        value.put(COLUMN_AGE, userModel.getAge());
        value.put(COLUMN_GENDER, userModel.getGender());
        value.put(COLUMN_BIRTH, userModel.getBirth());
        value.put(COLUMN_INSURANCE, userModel.getInsurance());
        value.put(COLUMN_IDENTITY, userModel.getIdentity());
        long result = sqldb.insert(USER_TABLE, null, value);
        return result != -1;

    }

    /**
     * verify account when log in
     *
     * @param SSN
     * @return boolean
     */
    public boolean Verify_Account(String SSN) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor member_Account = sqldb.rawQuery("SELECT * FROM USER_TABLE where SSN = ?", new String[]{SSN});
        if (member_Account.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * verify identity IF and ONLY IF when user try to log in as employee
     *
     * @param identity
     * @return boolean
     */
    public boolean Verify_identity(String SSN, String identity) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor Accountinfo = sqldb.rawQuery("SELECT* FROM USER_TABLE where SSN = ? and IDENTITY = ?", new String[]{SSN, identity});
        if (Accountinfo.getCount() > 0) {
            return true;
        } else {
            return false;

        }
    }

    /**
     * verify Insurance number
     *
     * @param SSN
     * @param insurance
     * @return boolean
     */
    public boolean Verify_Insurance(String SSN, String insurance) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor Accountinfo = sqldb.rawQuery("SELECT* FROM USER_TABLE where SSN = ? and INSURANCE = ?", new String[]{SSN, insurance});
        if (Accountinfo.getCount() > 0) {
            return true;
        } else {
            return false;

        }
    }

    /**
     * verify appointment
     *
     * @param dentist
     * @return boolean
     */
    public boolean Verify_Appname(String dentist) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor classn = sqldb.rawQuery("SELECT * FROM APP_TABLE where DENTIST = ?", new String[]{dentist});
        if (classn.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void UpdatePatient(int appid, String patient) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String query = "UPDATE " + APP_TABLE + " SET " + COLUMN_PATIENT + " = '" + patient + "' WHERE " + COLUMN_ID + "= '" + appid + "'";
        sqldb.execSQL(query);
    }

    /**
     * updating appointment when employee modifying appointment
     *
     * @param appid
     * @param branch
     * @param status
     * @param dentistid
     * @param date
     * @param time
     * @param hours
     * @param type
     * @return null
     */
    public void UpdateApp(int appid, String branch, String status, String dentistid, String date, String time, String hours, String type, String price ) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String query = "UPDATE " + APP_TABLE + " SET " + COLUMN_BRANCH + " = '" + branch + "' , " + COLUMN_STATUS + " = '" + status + "' , " + COLUMN_DENTIST +
                " = '" + dentistid + "' , " + COLUMN_DATE + " = '" + date + "' , " + COLUMN_TIME + " = '" + time + "' , " + COLUMN_HOUR
                + " = '" + hours + "' , " + COLUMN_TYPE + " = '" + type + "' , " + COLUMN_PRICE + " = '" + price + "' WHERE " + COLUMN_ID + "= '" + appid + "'";
        sqldb.execSQL(query);
    }

}