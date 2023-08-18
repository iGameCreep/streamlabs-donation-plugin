package fr.gamecreep.streamlabsdonations.donations;

import fr.gamecreep.streamlabsdonations.entities.donations.Donation;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class StreamlabsDonationEventEmitter {
    public StreamlabsDonationEventEmitter() {}

    public void onDonation(Donation donation) {
        Player player = Bukkit.getPlayer("GameCreep35");

        BaseComponent component = new TextComponent(String.format("%s à donné %s !", donation.getDonorName(), donation.getFormattedAmount().toString()));
        BaseComponent donationMessage = new TextComponent(donation.getMessage());

        player.showTitle(donationMessage);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);

        Location location = player.getLocation();
        player.getWorld().spawnEntity(location, EntityType.CREEPER);
    }
}
