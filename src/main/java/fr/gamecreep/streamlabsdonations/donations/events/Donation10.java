package fr.gamecreep.streamlabsdonations.donations.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Donation10 {
    public Donation10(Player player) {
        for (int i = 0; i < 5; i++) {
            //TODO: fix
            player.getWorld().spawnEntity(player.getLocation(), EntityType.TNT);
        }
    }
}
