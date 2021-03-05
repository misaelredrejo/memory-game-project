package com.misael_redrejo.memorymisaelredrejo.models;

public class Score {

    private String name;
    private int score;
    private String time;
    private String difficulty;

    public Score() {}

    public Score(String name, int score, String time, String difficulty) {
        this.name = name;
        this.score = score;
        this.time = time;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
