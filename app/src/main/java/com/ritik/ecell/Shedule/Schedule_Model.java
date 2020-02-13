package com.ritik.ecell.Shedule;

public class Schedule_Model {

    public String eventtitle;
    public String desc;
    public String time;
    public String venue;
    public String type;
    public String imguri;

    public Schedule_Model() {
    }

    public Schedule_Model(String eventtitle, String desc, String time, String venue, String type, String imguri) {
        this.eventtitle = eventtitle;
        this.desc = desc;
        this.time = time;
        this.venue = venue;
        this.type = type;
        this.imguri = imguri;
    }

    public String getEventtitle() {
        return eventtitle;
    }

    public void setEventtitle(String eventtitle) {
        this.eventtitle = eventtitle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImguri() {
        return imguri;
    }

    public void setImguri(String imguri) {
        this.imguri = imguri;
    }
}
