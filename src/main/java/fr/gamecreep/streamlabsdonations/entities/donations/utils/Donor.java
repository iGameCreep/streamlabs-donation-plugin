package fr.gamecreep.streamlabsdonations.entities.donations.utils;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Donor implements Comparable<Donor> {
    private String donorName;
    private BigDecimal donationAmount;

    public Donor(String donorName, BigDecimal donationAmount) {
        this.donorName = donorName;
        this.donationAmount = donationAmount;
    }

    @Override
    public int compareTo(Donor other) {
        return donationAmount.compareTo(other.donationAmount);
    }
}
