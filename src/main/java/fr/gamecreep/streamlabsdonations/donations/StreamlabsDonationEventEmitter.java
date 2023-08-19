package fr.gamecreep.streamlabsdonations.donations;

import fr.gamecreep.streamlabsdonations.entities.donations.utils.Donation;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class StreamlabsDonationEventEmitter {
    public StreamlabsDonationEventEmitter() {}

    public void onDonation(Donation donation) {
        String titleInfo = String.format("%s à donné %s !", donation.getDonorName(), donation.getFormattedAmount());

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(titleInfo, donation.getMessage());

            player.getWorld().spawnEntity(player.getLocation(), EntityType.CREEPER);
        }
    }
}
