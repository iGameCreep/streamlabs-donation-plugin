package fr.gamecreep.streamlabsdonations.entities.commands;

import fr.gamecreep.streamlabsdonations.StreamLabsDonations;
import fr.gamecreep.streamlabsdonations.donations.StreamlabsDonationEventEmitter;
import fr.gamecreep.streamlabsdonations.entities.donations.utils.Donation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class TestCommand implements CommandExecutor {
    private final StreamLabsDonations plugin;

    public TestCommand(StreamLabsDonations plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandLabel, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            if (args.length == 0) return false;
            StringBuilder message = new StringBuilder(args[2]);
            for (int arg = 3; arg < args.length; arg++) {
                message.append(" ").append(args[arg]);
            }
            Donation donation = new Donation(args[0], new BigDecimal(args[1]), args[1] + "€", message.toString());
            new StreamlabsDonationEventEmitter().onDonation(donation);
            plugin.getDonationCache().updateCache(donation);
            plugin.getScoreBoardUtils().updateScoreboard();
            return true;
        }

        return false;
    }
}
