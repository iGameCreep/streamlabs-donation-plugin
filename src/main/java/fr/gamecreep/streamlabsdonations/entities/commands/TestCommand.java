package fr.gamecreep.streamlabsdonations.entities.commands;

import fr.gamecreep.streamlabsdonations.donations.StreamlabsDonationEventEmitter;
import fr.gamecreep.streamlabsdonations.entities.donations.Donation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandLabel, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Donation donation = new Donation();
            donation.setDonorName("GameCreep");
            donation.setDonationAmount(new BigDecimal("12.34"));
            donation.setFormattedAmount("12.34€  12.34$");
            donation.setMessage("Donation test drakka2Dodo drakka2Dodo");

            new StreamlabsDonationEventEmitter().onDonation(donation);
        }

        return false;
    }
}
