package fr.gamecreep.streamlabsdonations.donations.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DefaultDonation {
    public DefaultDonation(Player player) {
        player.getWorld().spawnEntity(player.getLocation(), EntityType.CREEPER);
    }
}
