package com.example.saikishoreeppalagudem.csci3130;

/**
 * Created by saikishoreeppalagudem on 2018-02-22.
 *  Integrated by karthick parameswaran on 2018-03-16
 */

public class Course {
    String courseID;
    String courseDesc;
    String courseProf;
    String profEmail;
    String courseTimings;

    @Override
    public String toString() {

        return courseID + ","  + courseDesc + "," + courseTimings + "," + courseProf + "," + profEmail;
    }

    public String getCourseTimings() {
        return courseTimings;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public void setProfname(String courseProf) {
        this.courseProf = courseProf;
    }

    public void setProfmail(String profEmail) {
        this.profEmail = profEmail;

    public void setSeatsAvail(String seatsAvail) {
        this.seatsAvail = seatsAvail;
    }

    public Course(){

    }

    public Course(String courseID, String courseDesc, String courseTimings, String courseProf,String profEmail) {
        this.courseID = courseID;
        this.courseDesc = courseDesc;
        this.profEmail = profEmail;
        this.courseProf = courseProf;
        this.courseTimings = courseTimings;
    }

    public String getCourseProf() {
        return courseProf;
    }

    public String getProfEmail() {
        return profEmail;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public String getSeatsAvail() {
        return seatsAvail;
    }
}
