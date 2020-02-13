package com.ritik.ecell.Voting;

public class Voting_helper {

    public String votingstatus;
    public String voteid;

    public Voting_helper() {
    }

    public Voting_helper(String votingstatus,String voteid) {
        this.votingstatus = votingstatus;
        this.voteid = voteid;
    }

    public String getVotingstatus() {
        return votingstatus;
    }

    public void setVotingstatus(String votingstatus) {
        this.votingstatus = votingstatus;
    }

    public String getVoteid() {
        return voteid;
    }

    public void setVoteid(String voteid) {
        this.voteid = voteid;
    }
}
