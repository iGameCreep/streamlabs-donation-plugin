package fr.gamecreep.streamlabsdonations.donations.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Donation10 {
    public Donation10(Player player) {
        for (int i = 0; i < 5; i++) {
            player.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
        }
    }
}
