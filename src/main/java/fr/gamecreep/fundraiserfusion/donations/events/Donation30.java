package fr.gamecreep.fundraiserfusion.donations.events;

import org.bukkit.entity.Player;

public class Donation30 {
    public Donation30(Player player) {
        player.getInventory().clear();
    }
}
