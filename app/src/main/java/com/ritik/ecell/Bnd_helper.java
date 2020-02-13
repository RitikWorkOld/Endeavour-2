package com.ritik.ecell;

public class Bnd_helper {

    public String refrelid;
    public String name;
    public String contactn;
    public String email;

    public Bnd_helper() {
    }


    public Bnd_helper(String refrelid, String name, String contactn,String email) {
        this.refrelid = refrelid;
        this.name = name;
        this.contactn = contactn;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefrelid() {
        return refrelid;
    }

    public void setRefrelid(String refrelid) {
        this.refrelid = refrelid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactn() {
        return contactn;
    }

    public void setContactn(String contactn) {
        this.contactn = contactn;
    }
}
