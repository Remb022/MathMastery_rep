package com.example.mathmastery_beta.level_status_model;

public class OperandFoundModel implements LevelModel {
    private int level;
    private String status;
    private int exp;
    private String record;
    private int width;
    private int height;
    private int rangeMin;
    private int rangeMax;
    private String[] operationList;
    private int page;

    public int getLevel() {
        return level;
    }

    public String getStatus() {
        return status;
    }

    public int getExp() {
        return exp;
    }

    public String getRecord() {
        return record;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRangeMin() {
        return rangeMin;
    }

    public int getRangeMax() {
        return rangeMax;
    }

    public String[] getOperationList() {
        return operationList;
    }

    public int getPage() {
        return page;
    }
}
