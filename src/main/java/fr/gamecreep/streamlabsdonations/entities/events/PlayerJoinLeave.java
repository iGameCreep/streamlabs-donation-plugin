package fr.gamecreep.streamlabsdonations.entities.events;

import fr.gamecreep.streamlabsdonations.StreamLabsDonations;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinLeave implements Listener {
    private final StreamLabsDonations plugin;

    public PlayerJoinLeave(StreamLabsDonations plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.plugin.getScoreBoardUtils().createScoreboard(event.getPlayer());
    }
}
