package fr.gamecreep.streamlabsdonations.entities.donations;

import fr.gamecreep.streamlabsdonations.entities.donations.utils.Donation;
import fr.gamecreep.streamlabsdonations.entities.donations.utils.Donor;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class DonorCache {
    private final Map<String, BigDecimal> donorTotalAmounts;

    public DonorCache() {
        donorTotalAmounts = new HashMap<>();
    }

    public void updateDonation(Donation donation) {
        String donorName = donation.getDonorName();
        BigDecimal donationAmount = donation.getDonationAmount();

        donorTotalAmounts.put(donorName, donorTotalAmounts.getOrDefault(donorName, BigDecimal.ZERO).add(donationAmount));
    }

    public Map<String, BigDecimal> getDonorTotalAmounts() {
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
