package com.example.novigrad;

/**
 * Classe Succursale
 * */

public class Succursale {
    private String name;
    private String[] serviceRefs;
    // goes from monday to sunday
    // e.g. times[0] is monday start time, times[1] is monday end time
    private int[] times;

    public Succursale(String username) {
        this.name = username;
        this.serviceRefs = new String[0];
        this.times = new int[14];
        for (int i = 0; i < times.length; i++) {
            times[i] = (i % 2 == 0 ? 9 * 60 : 17 * 60); //set all the times to be 9-5 by default
        }
    }

    public String getName() {
        return name;
    }


    public String[] getServiceRefs() {
        return serviceRefs;
    }

    public int[] getTimes() {
        return times;
    }

    private String timesToString() {
        String[] days = new String[]{"\"lunA\"","\"lunB\"","\"marA\"","\"marB\"","\"merA\"","\"merB\"","\"jeuA\"","\"jeuB\"","\"venA\"","\"venB\"","\"samA\"","\"samB\"","\"dimA\"","\"dimB\""};
        String timesString = "{";
        for (int i = 0; i < times.length; i++) {
            timesString += days[i] + ": " + times[i] + (i==times.length-1 ? "}" : ",");
        }
        return timesString;
    }

    private String serviceRefsToString() {
        String serviceRefsString = "{";
        if (serviceRefs.length == 0) {
            serviceRefsString += "}";
        }
        for (int i = 0; i < serviceRefs.length; i++) {
            serviceRefsString += "\""+ i + "\": \"" + serviceRefs[i] + (i==serviceRefs.length-1 ? "\"}" : "\",");
        }
        return serviceRefsString;
    }

    //returns a string in JSON format
    public String toString()
    {
        return "{"+
                "\"name\": \"" + getName()+ "\"," +
                "\"serviceRefs\":" + serviceRefsToString() + ", " +
                "\"times\":" + timesToString() + "}";
    }
}
