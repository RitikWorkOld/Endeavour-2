package com.ritik.ecell.Team;

public class Teamcard_model {

    String name;
    String desig;
    String domain;
    String imguri;
    String google_profile;
    String linkedin_profile;
    String facebook_profile;

    public Teamcard_model() {
    }

    public Teamcard_model(String name, String desig, String domain, String imguri, String google_profile, String linkedin_profile, String facebook_profile) {
        this.name = name;
        this.desig = desig;
        this.domain = domain;
        this.imguri = imguri;
        this.google_profile = google_profile;
        this.linkedin_profile = linkedin_profile;
        this.facebook_profile = facebook_profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesig() {
        return desig;
    }

    public void setDesig(String desig) {
        this.desig = desig;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getImguri() {
        return imguri;
    }

    public void setImguri(String imguri) {
        this.imguri = imguri;
    }

    public String getGoogle_profile() {
        return google_profile;
    }

    public void setGoogle_profile(String google_profile) {
        this.google_profile = google_profile;
    }

    public String getLinkedin_profile() {
        return linkedin_profile;
    }

    public void setLinkedin_profile(String linkedin_profile) {
        this.linkedin_profile = linkedin_profile;
    }

    public String getFacebook_profile() {
        return facebook_profile;
    }

    public void setFacebook_profile(String facebook_profile) {
        this.facebook_profile = facebook_profile;
    }
}
