package com.grup22.model;

/**
 * Created by hp-pc on 18.11.2018.
 */

public class Data {
    String question;
    String optionsA;
    String optionsB;
    String optionsC;
    String optionsD;

    public Data(String question, String optionsA, String optionsB, String optionsC, String optionsD) {
        this.question = question;
        this.optionsA = optionsA;
        this.optionsB = optionsB;
        this.optionsC = optionsC;
        this.optionsD = optionsD;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionsA() {
        return optionsA;
    }

    public String getOptionsB() {
        return optionsB;
    }

    public String getOptionsC() {
        return optionsC;
    }

    public String getOptionsD() {
        return optionsD;
    }
}
