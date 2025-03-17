package fr.gamecreep.fundraiserfusion.commands;

import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.donations.StreamlabsDonationEventEmitter;
import fr.gamecreep.fundraiserfusion.donations.entities.Donation;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TestCommand implements CommandExecutor {
    private final FundraiserFusion plugin;

    public TestCommand(FundraiserFusion plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final @NonNull CommandSender commandSender,
                             final @NonNull Command command,
                             final @NonNull String commandLabel,
                             final @NonNull String @NonNull[] args) {
        if (commandSender instanceof Player) {
            if (args.length < 2) return false;

            String message = args.length > 2 ? String.join(" ", Arrays.copyOfRange(args, 2, args.length)) : "";

            final Donation donation = new Donation(args[0], Double.parseDouble(args[1]), "EUR", message);
            new StreamlabsDonationEventEmitter().onDonation(donation);

            this.plugin.addDonation(donation);
            return true;
        }

        return false;
    }
}
