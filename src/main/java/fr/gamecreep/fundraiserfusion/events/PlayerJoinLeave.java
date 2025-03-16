package fr.gamecreep.fundraiserfusion.events;

import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinLeave implements Listener {
    private final FundraiserFusion plugin;

    public PlayerJoinLeave(FundraiserFusion plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.plugin.getScoreBoardUtils().createScoreboard(event.getPlayer());
    }
}
