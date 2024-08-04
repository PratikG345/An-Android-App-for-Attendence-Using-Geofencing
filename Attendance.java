package com.example.student_attendance_app;

public class Attendance {

    String date,time,name,number,dept,shift;


    public Attendance() {
    }

    public Attendance(String date, String time, String name, String number, String dept, String shift) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.number = number;
        this.dept = dept;
        this.shift = shift;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
