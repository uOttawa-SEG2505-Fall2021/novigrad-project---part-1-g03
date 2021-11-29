package com.example.novigrad;

/**
 * Classe Helpers qui nous aide pour les horaires de travail
 * */
public class Helpers {

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
}
