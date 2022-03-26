package com.example.yolofitness;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Appointment part
 */
public class Appointment {
    private int appid;
    private String patientname;
    private String branch;
    private String status;
    private String dentistid;
    private String date;
    private String time;
    private String hours;
    private String type;

    /**
     *
     * @param appid
     * @param patientname
     * @param status
     * @param dentistid
     * @param type dentist's type
     * @param date
     * @param time
     * @param hours
     */

    public Appointment(int appid, String patientname,String branch, String status, String dentistid,String date,String time, String hours,String type) {
        this.appid = appid;
        this.patientname = patientname;
        this.branch = branch;
        this.status = status;
        this.dentistid = dentistid;
        this.type=type;
        this.date=date;
        this.time = time;
        this.hours=hours;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appid=" + appid +
                ", patientname='" + patientname + '\'' +
                ", status='" + status + '\'' +
                ", dentistid='" + dentistid + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", hours='" + hours + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDentistid() {
        return dentistid;
    }

    public void setDentistid(String dentistid) {
        this.dentistid = dentistid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
