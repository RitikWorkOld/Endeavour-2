package com.ritik.ecell.Sponsors;

public class Sponsor_Model {

    String sponsorname;
    String sponsorcategory;
    String imagesponsor;

    public Sponsor_Model() {
    }

    public Sponsor_Model(String sponsorname, String sponsorcategory, String imagesponsor) {
        this.sponsorname = sponsorname;
        this.sponsorcategory = sponsorcategory;
        this.imagesponsor = imagesponsor;
    }

    public String getSponsorname() {
        return sponsorname;
    }

    public void setSponsorname(String sponsorname) {
        this.sponsorname = sponsorname;
    }

    public String getSponsorcategory() {
        return sponsorcategory;
    }

    public void setSponsorcategory(String sponsorcategory) {
        this.sponsorcategory = sponsorcategory;
    }

    public String getImagesponsor() {
        return imagesponsor;
    }

    public void setImagesponsor(String imagesponsor) {
        this.imagesponsor = imagesponsor;
    }
}
