package com.ritik.ecell.Voting;

public class Voting_model {

    public String teamname;
    public String memeimg;
    public int totalvotes;
    public String voteid;

    public Voting_model() {
    }

    public Voting_model(String teamname, String memeimg,int totalvotes,String voteid) {
        this.teamname = teamname;
        this.memeimg = memeimg;
        this.totalvotes = totalvotes;
        this.voteid = voteid;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getMemeimg() {
        return memeimg;
    }

    public void setMemeimg(String memeimg) {
        this.memeimg = memeimg;
    }

    public int getTotalvotes() {
        return totalvotes;
    }

    public void setTotalvotes(int totalvotes) {
        this.totalvotes = totalvotes;
    }

    public String getVoteid() {
        return voteid;
    }

    public void setVoteid(String voteid) {
        this.voteid = voteid;
    }
}
