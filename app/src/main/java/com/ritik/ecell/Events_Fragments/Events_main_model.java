package com.ritik.ecell.Events_Fragments;

public class Events_main_model {

    public String mimguri;
    public String title;
    public String descp;
    public String register_uri;
    public String desc1;
    public String desc2;
    public String faqid;
    public String amount;

    public Events_main_model() {
    }

    public Events_main_model(String mimguri, String title, String descp, String register_uri, String desc1, String desc2,String faqid,String amount) {
        this.mimguri = mimguri;
        this.title = title;
        this.descp = descp;
        this.register_uri = register_uri;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.faqid = faqid;
        this.amount = amount;
    }

    public String getMimguri() {
        return mimguri;
    }

    public void setMimguri(String mimguri) {
        this.mimguri = mimguri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public String getRegister_uri() {
        return register_uri;
    }

    public void setRegister_uri(String register_uri) {
        this.register_uri = register_uri;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getFaqid() {
        return faqid;
    }

    public void setFaqid(String faqid) {
        this.faqid = faqid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
