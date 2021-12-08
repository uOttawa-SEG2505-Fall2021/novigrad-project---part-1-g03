package com.example.novigrad;

public class Time {

    private int time;
    private int day;

    public Time(){
        time = -1;
        day = 7;
    }

    public Time(int times, int day) {
        this.time = time;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public int getTime() {
        return time;
    }

    public void setDay(int day) {this.day = day;}

    public void setTime(int time) {this.time = time;}

    public boolean withinTimes(int[] times) {
       return (times[2*day] <= time && time <= times[2*day + 1]);
    }

}
