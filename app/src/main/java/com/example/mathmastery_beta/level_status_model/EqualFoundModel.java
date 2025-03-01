package com.example.mathmastery_beta.level_status_model;

public class EqualFoundModel implements LevelModel{
    private int level;
    private String status;
    private int exp;
    private String record;
    private int rangeMin;
    private int rangeMax;
    private int count;

    public int getLevel() {
        return level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { this.status = status; }

    public int getExp() {
        return exp;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) { this.record = record; }

    public int getRangeMin() {
        return rangeMin;
    }

    public int getRangeMax() {
        return rangeMax;
    }

    public int getCount() {
        return count;
    }
}
