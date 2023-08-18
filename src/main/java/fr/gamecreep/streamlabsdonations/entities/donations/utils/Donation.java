package fr.gamecreep.streamlabsdonations.entities.donations.utils;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Donation implements Comparable<Donation> {
    private String donorName;
    private BigDecimal donationAmount;
    private String formattedAmount;
    private String message;

    public Donation(String donorName, BigDecimal donationAmount, String formattedAmount, String message) {
        this.donorName = donorName;
        this.donationAmount = donationAmount;
        this.formattedAmount = formattedAmount;
        this.message = message;
    }

    public Donation(String donorName, BigDecimal donationAmount) {
        this.donorName = donorName;
        this.donationAmount = donationAmount;
    }

    @Override
    public int compareTo(Donation other) {
        return donationAmount.compareTo(other.donationAmount);
    }
}
