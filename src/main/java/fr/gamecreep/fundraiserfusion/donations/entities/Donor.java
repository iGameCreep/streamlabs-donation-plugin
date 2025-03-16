package fr.gamecreep.fundraiserfusion.donations.entities;

import lombok.Getter;

@Getter
public class Donor implements Comparable<Donor> {
    private final String donorName;
    private final double donationAmount;

    public Donor(String donorName, double donationAmount) {
        this.donorName = donorName;
        this.donationAmount = donationAmount;
    }

    @Override
    public int compareTo(Donor other) {
        return Double.compare(donationAmount, other.donationAmount);
    }
}
