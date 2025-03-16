package fr.gamecreep.fundraiserfusion.commands;

import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.donations.StreamlabsDonationEventEmitter;
import fr.gamecreep.fundraiserfusion.donations.entities.Donation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    private final FundraiserFusion plugin;

    public TestCommand(FundraiserFusion plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandLabel, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            if (args.length < 3) return false;
            StringBuilder message = new StringBuilder(args[2]);
            for (int arg = 3; arg < args.length; arg++) {
                message.append(" ").append(args[arg]);
            }
            Donation donation = new Donation(args[0], Double.parseDouble(args[1]), args[1] + "€", message.toString());
            new StreamlabsDonationEventEmitter().onDonation(donation);
            plugin.getDonationCache().updateCache(donation);
            plugin.getScoreBoardUtils().updateScoreboard();
            return true;
        }

        return false;
    }
}
