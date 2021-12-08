package com.example.novigrad;
/**
 * Classe  qui permet de noter les  services
 * */
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
