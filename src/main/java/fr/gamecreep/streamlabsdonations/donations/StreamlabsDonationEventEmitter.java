package fr.gamecreep.streamlabsdonations.donations;

import fr.gamecreep.streamlabsdonations.entities.donations.utils.Donation;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class StreamlabsDonationEventEmitter {
    public StreamlabsDonationEventEmitter() {}

    public void onDonation(Donation donation) {
        Player player = Bukkit.getPlayer("GameCreep35");

        String titleInfo = String.format("%s à donné %s !", donation.getDonorName(), donation.getFormattedAmount());
        String lastDonation = String.format("Dernier don: %s > %s", donation.getDonorName(), donation.getFormattedAmount());

        player.sendTitle(titleInfo, donation.getMessage());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(lastDonation));

        Location location = player.getLocation();
        player.getWorld().spawnEntity(location, EntityType.CREEPER);
    }
}
