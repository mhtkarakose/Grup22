package com.grup22.model;

/**
 * Created by hp-pc on 18.11.2018.
 */

public class Data {
    String question;
    String trueAnswer;
    String falseAnswer1;
    String falseAnswer2;
    String falseAnswer3;

    public Data(String question, String trueAnswer, String falseAnswer1, String falseAnswer2, String falseAnswer3) {
        this.question = question;
        this.trueAnswer = trueAnswer;
        this.falseAnswer1 = falseAnswer1;
        this.falseAnswer2 = falseAnswer2;
        this.falseAnswer3 = falseAnswer3;
    }

    public String getQuestion() {
        return question;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public String getFalseAnswer1() {
        return falseAnswer1;
    }

    public String getFalseAnswer2() {
        return falseAnswer2;
    }

    public String getFalseAnswer3() {
        return falseAnswer3;
    }
}
