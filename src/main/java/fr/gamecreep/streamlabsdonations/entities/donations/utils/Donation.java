package fr.gamecreep.streamlabsdonations.entities.donations.utils;

import lombok.Getter;

@Getter
public class Donation {
    private final String donorName;
    private final double donationAmount;
    private String formattedAmount;
    private String message;

    public Donation(String donorName, double donationAmount, String formattedAmount, String message) {
        this.donorName = donorName;
        this.donationAmount = donationAmount;
        this.formattedAmount = formattedAmount;
        this.message = message;
    }

    public Donation(String donorName, double donationAmount) {
        this.donorName = donorName;
        this.donationAmount = donationAmount;
    }
}
