package fr.gamecreep.streamlabsdonations.donations;

import fr.gamecreep.streamlabsdonations.donations.events.*;
import fr.gamecreep.streamlabsdonations.entities.donations.utils.Donation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StreamlabsDonationEventEmitter {
    public StreamlabsDonationEventEmitter() {}

    public void onDonation(Donation donation) {
        String titleInfo = String.format("%s à donné %s !", donation.getDonorName(), donation.getFormattedAmount());

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(titleInfo, donation.getMessage());
            double amount = donation.getDonationAmount();

            if (amount > 50) new Donation50(player);
            else if (amount > 40) new Donation40(player);
            else if (amount > 30) new Donation30(player);
            else if (amount > 20) new Donation20(player);
            else if (amount > 10) new Donation10(player);
            else new DefaultDonation(player);
        }
    }
}
