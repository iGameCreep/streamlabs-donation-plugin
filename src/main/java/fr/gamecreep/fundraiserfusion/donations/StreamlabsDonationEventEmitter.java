package fr.gamecreep.fundraiserfusion.donations;

import fr.gamecreep.fundraiserfusion.donations.events.*;
import fr.gamecreep.fundraiserfusion.donations.entities.Donation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StreamlabsDonationEventEmitter {
    public void onDonation(final Donation donation) {
        final String titleInfo = String.format("%s gave %s !", donation.getDonorName(), donation.getFormattedAmount());
        final Title title = Title.title(Component.text(titleInfo), Component.text(donation.getMessage()));

        for (final Player player : Bukkit.getOnlinePlayers()) {
            player.showTitle(title);

            double amount = donation.getDonationAmount();

            // TODO: Implement actual config for donations according to website => https://github.com/iGameCreep/FundraiserFusion-Website

            if (amount > 50) new Donation50(player);
            //TODO: figure out why was this class removed; anyway wont be used in future
            //else if (amount > 40) new Donation40(player);
            else if (amount > 30) new Donation30(player);
            else if (amount > 20) new Donation20(player);
            else if (amount > 10) new Donation10(player);
            else new DefaultDonation(player);
        }
    }
}
