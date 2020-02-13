package com.ritik.ecell;


public class User {
    public String name;
    public String email;
    public String branch;
    public String year;
    public String campusid;
    public String contactn;
    public String collegename;
    public String uid;
    public String refrelid;
    public String password;
    public String ambdid;

    public User(){

    }

    public User(String name, String email, String branch, String year, String campusid, String contactn, String collegename, String uid, String refrelid, String password,String ambdid) {
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.year = year;
        this.campusid = campusid;
        this.contactn = contactn;
        this.collegename = collegename;
        this.uid = uid;
        this.refrelid = refrelid;
        this.password = password;
        this.ambdid = ambdid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCampusid() {
        return campusid;
    }

    public void setCampusid(String campusid) {
        this.campusid = campusid;
    }

    public String getContactn() {
        return contactn;
    }

    public void setContactn(String contactn) {
        this.contactn = contactn;
    }

    public String getCollegename() {
        return collegename;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRefrelid() {
        return refrelid;
    }

    public void setRefrelid(String refrelid) {
        this.refrelid = refrelid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAmbdid() {
        return ambdid;
    }

    public void setAmbdid(String ambdid) {
        this.ambdid = ambdid;
    }
}