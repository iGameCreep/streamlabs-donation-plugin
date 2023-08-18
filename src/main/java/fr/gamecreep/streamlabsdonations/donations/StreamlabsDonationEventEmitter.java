package fr.gamecreep.streamlabsdonations.donations;

import fr.gamecreep.streamlabsdonations.entities.donations.utils.Donation;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class StreamlabsDonationEventEmitter {
    public StreamlabsDonationEventEmitter() {}

    public void onDonation(Donation donation) {
        String titleInfo = String.format("%s à donné %s !", donation.getDonorName(), donation.getFormattedAmount());

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(titleInfo, donation.getMessage());

            Location location = player.getLocation();
            player.getWorld().spawnEntity(location, EntityType.CREEPER);
        }
    }
}
