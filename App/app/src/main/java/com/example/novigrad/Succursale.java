package com.example.novigrad;

/**
 * Classe Succursale
 * */
public class Succursale {
    private String name;
    // goes from monday to sunday
    // e.g. times[0] is monday start time, times[1] is monday end time
    private int[] times;

    public Succursale(String username) {
        this.name = username;
        this.times = new int[14];
        for (int i = 0; i < times.length; i++) {
            times[i] = (i % 2 == 0 ? 9 * 60 : 17 * 60); //set all the times to be 9-5 by default
        }
    }

    private String timesToString() {
        String[] days = new String[]{"\"lunA\"","\"lunB\"","\"marA\"","\"marB\"","\"merA\"","\"merB\"","\"jeuA\"","\"jeuB\"","\"venA\"","\"venB\"","\"samA\"","\"samB\"","\"dimA\"","\"dimB\""};
        String timesString = "{";
        for (int i = 0; i < times.length; i++) {
            timesString += days[i] + ": " + times[i] + (i==times.length-1 ? "}" : ",");
        }
        return timesString;
    }

    //returns a string in JSON format so it can be sent to firebase
    public String toString() {
        return "{"+
                "\"name\": \"" + this.name + "\"," +
                "\"times\":" + timesToString() + "}";
    }
}
