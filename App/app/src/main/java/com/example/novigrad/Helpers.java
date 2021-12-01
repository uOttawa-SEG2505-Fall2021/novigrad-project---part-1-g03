package com.example.novigrad;

import java.util.HashMap;

/**
 * Classe Helpers implementé pour  les horaires de travail
 * */
public class Helpers {
    private static final String[] DAYS_START_END = new String[]{"lunA","lunB","marA","marB","merA","merB","jeuA","jeuB","venA","venB","samA","samB","dimA","dimB"};
    private static final String[] DAYS = new String[]{"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

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

    public static String formatHHmm(int timeInMinutes) {
        return (timeInMinutes/60 <10 ? "0" : "") + (timeInMinutes/60+":") + (timeInMinutes % 60 <10 ? "0" : "") + timeInMinutes%60;
    }

    public static Interval[] convertTimeHashMapToIntervals(HashMap<String, Integer> times) {

        Interval[] intervals = new Interval[7];

        for (int i = 0; i < intervals.length; i++) {
            intervals[i] = new Interval(times.get(DAYS_START_END[2*i]), times.get(DAYS_START_END[2*i+1]), DAYS[i]);
        }
        return intervals;
    }

    public static void setValueInTimeHashMap(HashMap<String, Integer> times, int newTime, int position) {
        times.replace(DAYS_START_END[position], newTime);
    }

    public static boolean verifyTimesMap(HashMap<String, Integer> times) {
        for (int i = 0; i < DAYS_START_END.length; i+=2) {
            //si succursale fermé
            if (times.get(DAYS_START_END[i]) == -1 && times.get(DAYS_START_END[i+1]) == -1) {
                continue;
            } else if (times.get(DAYS_START_END[i]) == -1 || times.get(DAYS_START_END[i+1]) == -1) {
                return false;
            }
            if (times.get(DAYS_START_END[i]) >= times.get(DAYS_START_END[i+1])) {
                return false;
            }
        }
        return true;
    }
}
