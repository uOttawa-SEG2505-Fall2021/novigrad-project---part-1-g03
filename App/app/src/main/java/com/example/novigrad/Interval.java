package com.example.novigrad;
// class Interval  consiste a donner un intervalle de temps
public class Interval {
    int start;
    int end;
    String name;

    public Interval(int start, int end, String name) {
        this.start = start;
        this.end = end;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

}
