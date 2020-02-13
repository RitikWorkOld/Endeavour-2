package com.ritik.ecell.BQuiz;

public class Report {

    public String status;
    public String myanswer;
    public int score;

    public Report() {
    }

    public Report(String status, String myanswer,int score) {
        this.status = status;
        this.myanswer = myanswer;
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMyanswer() {
        return myanswer;
    }

    public void setMyanswer(String myanswer) {
        this.myanswer = myanswer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
