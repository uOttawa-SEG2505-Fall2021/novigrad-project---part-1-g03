package com.example.novigrad;

import java.util.ArrayList;
import java.util.List;

public class Rating {

    private double rating;
    private String forSucc;

    public static double getAverageRatings(Rating[] ratings) {
        double avg = 0;
        for (Rating r:
             ratings) {
            avg += r.getRating();
        }
        return avg/ratings.length;
    }

    public static String getAverageRatingsAsString(Rating[] ratings) {
        double avg = getAverageRatings(ratings);
        return String.format("%.1f",avg);
    }

    public static double getAverageRatings(List<Rating> ratings) {
        double avg = 0;
        for (Rating r:
             ratings) {
            avg += r.getRating();
        }
        return avg/ratings.size();
    }

    public static String getAverageRatingsAsString(List<Rating> ratings) {
        double avg = getAverageRatings(ratings);
        return String.format("%.1f",avg);
    }
    public Rating() {}

    public Rating(double rate, String succ) {
        this.forSucc = succ;
        this.rating = rate;
    }

    public double getRating() {
        return rating;
    }

    public String getForSucc() {
        return forSucc;
    }
}
