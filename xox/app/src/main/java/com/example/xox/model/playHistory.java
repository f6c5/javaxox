package com.example.xox.model;

public class playHistory {
    private int h_id;
    private String h_x_name;
    private String h_o_name;
    private String h_score;
    private String h_mod;

    public playHistory() {

    }

    public playHistory(String h_x_name, String h_o_name, String h_score, String h_mod) {
        this.h_x_name = h_x_name;
        this.h_o_name = h_o_name;
        this.h_score = h_score;
        this.h_mod = h_mod;
    }

    public void set_h_id(int h_id) {this.h_id = h_id;}

    public String get_x_name() {
        return h_x_name;
    }

    public void set_x_name(String h_x_name) {
        this.h_x_name = h_x_name;
    }

    public String get_o_name() {
        return h_o_name;
    }

    public void set_o_name(String h_o_name) {
        this.h_o_name = h_o_name;
    }

    public String get_score() {
        return h_score;
    }

    public void set_score(String h_score) {
        this.h_score = h_score;
    }

    public String get_mod() {
        return h_mod;
    }

    public void set_mod(String h_mod) {
        this.h_mod = h_mod;
    }

    public String get_history(){
        String dt= "X adı: "+h_x_name + "    O adı: " + h_o_name + "\n" + "Skor: " + h_score +"    mod: " + h_mod;
        return dt;
    }
}
