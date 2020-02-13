package com.ritik.ecell.BQuiz;

public class Questions {
    public String questions,option1,option2,option3,option4,answer,imguri;

    public Questions(String question, String option1, String option2, String option3, String option4, String answer,String imguri) {
        this.questions = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.imguri = imguri;
    }

    public Questions(){}

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String question) {
        this.questions = question;
    }

    public String getoption1() {
        return option1;
    }

    public void setoption1(String option1) {
        this.option1 = option1;
    }

    public String getoption2() {
        return option2;
    }

    public void setoption2(String option2) {
        this.option2 = option2;
    }

    public String getoption3() {
        return option3;
    }

    public void set0ption3(String option3) {
        this.option3 = option3;
    }

    public String getoption4() {
        return option4;
    }

    public void setoption4(String option4) {
        this.option4 = option4;
    }

    public String getanswer() {
        return answer;
    }

    public void setanswer(String answer) {
        this.answer = answer;
    }

    public String getImguri() {
        return imguri;
    }

    public void setImguri(String imguri) {
        this.imguri = imguri;
    }
}