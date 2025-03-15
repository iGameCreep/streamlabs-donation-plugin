package fr.gamecreep.streamlabsdonations.donations;

import fr.gamecreep.streamlabsdonations.StreamLabsDonations;
import fr.gamecreep.streamlabsdonations.donations.entities.Donation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DonationCache {
    private final StreamLabsDonations plugin;
    private final List<Donation> totalDonations;

    public DonationCache(StreamLabsDonations plugin) {
        this.plugin = plugin;
        totalDonations = new ArrayList<>();
    }

    public List<Donation> getDonations() {
        return Collections.unmodifiableList(totalDonations);
    }

    public void fetchAndUpdateTopDonations(List<Donation> allDonations) {
        totalDonations.clear();
        totalDonations.addAll(allDonations);
    }

    public void updateCache(Donation newDonation) {
        this.plugin.getDonorCache().updateDonation(newDonation);
        totalDonations.add(newDonation);
    }
}
