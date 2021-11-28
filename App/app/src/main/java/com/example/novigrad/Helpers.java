package com.example.novigrad;

import android.icu.text.SimpleDateFormat;

import java.util.HashMap;

public class Helpers {
    static SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");

    public static int approximateTime(int minute) {
        if (minute <= 7) {
            return 0;
        } else if (minute <= 22) {
            return 15;
        }else if (minute <= 37) {
            return 30;
        }else if (minute <= 52) {
            return 45;
        } else {
            return 60;
        }
    }

    public static int getHours(int timeInMinutes) {
        return timeInMinutes / 60; //integer division means the remainder is gone
    }

    public static int getMinutes(int timeInMinutes) {
        return timeInMinutes % 60; //if timeInMinutes > 60 returns amount in minutes
    }

    public static String formatAsHour(int timeInMinutes) {
        return (timeInMinutes/60 <10 ? "0" : "") + (timeInMinutes/60+":") + (timeInMinutes % 60 <10 ? "0" : "") + timeInMinutes%60;
    }

    public static Interval[] convertTimeHashMapToIntervals(HashMap<String, Integer> times) {
        String[] daysStartEnd = new String[]{"lunA","lunB","marA","marB","merA","merB","jeuA","jeuB","venA","venB","samA","samB","dimA","dimB"};
        String[] days = new String[]{"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        Interval[] intervals = new Interval[7];

        for (int i = 0; i < intervals.length; i++) {
            intervals[i] = new Interval(times.get(daysStartEnd[2*i]), times.get(daysStartEnd[2*i+1]), days[i]);
        }
        return intervals;
    }
}
