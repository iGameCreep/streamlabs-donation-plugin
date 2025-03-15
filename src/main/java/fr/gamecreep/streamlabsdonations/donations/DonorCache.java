package fr.gamecreep.streamlabsdonations.donations;

import fr.gamecreep.streamlabsdonations.donations.entities.Donation;
import fr.gamecreep.streamlabsdonations.donations.entities.Donor;

import java.util.*;
import java.util.stream.Collectors;

public class DonorCache {
    private final Map<String, Double> donorTotalAmounts;

    public DonorCache() {
        donorTotalAmounts = new HashMap<>();
    }

    public void updateDonation(Donation donation) {
        String donorName = donation.getDonorName();
        double donationAmount = donation.getDonationAmount();

        donorTotalAmounts.put(donorName, donorTotalAmounts.getOrDefault(donorName, 0.0) + donationAmount);
    }

    public Map<String, Double> getDonorTotalAmounts() {
        return donorTotalAmounts;
    }

    public List<Donor> getTopDonors(int count) {
        return donorTotalAmounts.entrySet().stream()
                .map(entry -> new Donor(entry.getKey(), entry.getValue()))
                .sorted(Collections.reverseOrder())
                .limit(count)
                .collect(Collectors.toList());
    }
}
