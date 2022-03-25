package com.example.yolofitness;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * class nodel setting up all variable
 */
public class ClassModel {
    private int id;
    private String name;
    private String description;
    private String employee;    /**修改*/
    private String date;
    private String time;
    private String hours;
    private String difficulty;
    private String capacity;
    private String[] patientname;   /**修改*/


    /** 需要修改部分
     *
     * @param id
     * @param name
     * @param description
     * @param
     * @param difficulty
     * @param date
     * @param time
     * @param hours
     * @param capacity
     * @param
     */

    public ClassModel(int id, String name, String description, String employee, String difficulty, String date,String time, String hours, String capacity, String[] patientname) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.employee = employee;      /**修改*/
        this.difficulty=difficulty;
        this.date=date;
        this.time = time;
        this.hours=hours;
        this.capacity = capacity;
        this.patientname = patientname;   /**修改*/
    }


    @Override
    public String toString() {
        return
                "Class = " + name  +
                ", description = " + description  +
                ", instructor = " + employee +
                ", difficulty = " + difficulty  +
                ", date = " + date +
                ", time = " + time +
                ", hours = " + hours;
    }

    /**
     * get capacity
     * @return void
     */
    public String getCapacity() {
        return capacity;
    }    /**修改*/

    /**
     *  setting capacity
     * @param capacity
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     * getting id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * get name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * get description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * getdifficulty
     * @return
     */
    public String getDifficulty(){return difficulty;}

    /**
     * set difficulty
     * @param difficulty
     */

    public void setDifficulty(String difficulty){this.difficulty=difficulty;}

    /**
     * getdata
     * @return
     */
    public String getDate(){return date;}

    /**
     * set date
     * @param date
     */
    public void setDate(String date){this.date=date;}

    /**
     * get hours
     * @return
     */
    public String getHours(){return hours;}

    public void setHours(String hours){this.hours=hours;}

    /**
     * set id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * setname
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setdescription
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get insturctor
     * @return
     */
    public String getInstructor() {
        return employee;
    }    /**修改*/

    /**
     * setinsturctor
     * @param instructor
     */
    public void setInstructor(String instructor) {
        this.employee = instructor;
    }   /**修改*/

    /**
     * getStudentname
     * @return
     */
    public String[] getStudentname() {
        return patientname;
    }   /**修改*/

    /**
     * setStudentname
     * @param studentname
     */
    public void setStudentname(String[] studentname) {
        this.patientname = studentname;
    }   /**修改*/

    /**
     * get time
     * @return
     */
    public String getTime() {
        return time;
    }

    /**
     * settime
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }


}
