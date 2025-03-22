package fr.gamecreep.fundraiserfusion.donations;

import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.donations.entities.DonationEventData;
import fr.gamecreep.fundraiserfusion.donations.events.*;
import fr.gamecreep.fundraiserfusion.donations.entities.Donation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Comparator;

public class DonationGoalsExecutor {

    private final FundraiserFusion plugin;
    private final DonationEventData[] donationEventData;

    private final WeatherDonation weatherDonation;
    private final SpawnEntityDonation spawnEntityDonation;

    public DonationGoalsExecutor(final FundraiserFusion plugin, final DonationEventData[] donationEventData) {
        this.plugin = plugin;
        this.donationEventData = donationEventData;
        Arrays.sort(donationEventData, Comparator.comparingDouble(DonationEventData::getThreshold).reversed());

        this.weatherDonation = new WeatherDonation(this.plugin);
        this.spawnEntityDonation = new SpawnEntityDonation(plugin);
    }

    public void handleDonation(final Donation donation) {
        final String titleInfo = String.format("%s gave %s !", donation.getDonorName(), donation.getFormattedAmount());

        for (final Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(titleInfo, donation.getMessage(), 10, 70, 20);
        }

        double amount = donation.getDonationAmount();

        for (final DonationEventData data : this.donationEventData) {
            if (amount > data.getThreshold()) {
                this.executeDonationGoal(data);
                return;
            }
        }
    }

    private void executeDonationGoal(final DonationEventData data) {
        for (final DonationEventData.DonationEventAction action : data.getActions()) {
            switch (action.getAction()) {
                case SET_WEATHER: {
                    this.weatherDonation.handleDonation(action);
                    break;
                }
                case SPAWN_ENTITY: {
                    this.spawnEntityDonation.handleDonation(action);
                    break;
                }
                case COMMAND_EXEC: {
                    this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), action.getData());
                    break;
                }
            }
        }
    }
}
