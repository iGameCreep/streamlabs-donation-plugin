package fr.gamecreep.fundraiserfusion.donations.events;

import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.config.DonationEventData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;

@AllArgsConstructor
@Slf4j(topic = "Weather Donation")
public class WeatherDonation {

    private final FundraiserFusion plugin;

    public void handleDonation(final DonationEventData.DonationEventAction action) {
        final HashSet<World> cache = new HashSet<>();
        World world;
        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            world = player.getWorld();
            if (cache.contains(world)) return;
            cache.add(world);

            switch (action.getData().toLowerCase()) {
                case "storm": {
                    world.setStorm(true);
                    break;
                }
                case "rain": {
                    world.setWeatherDuration(5 * 60 * 20);
                    break;
                }
                case "clear": {
                    world.setClearWeatherDuration(5 * 60 * 20);
                    break;
                }
                default: {
                    log.warn("Unable to determine weather type: {}", action.getData());
                }
            }
        }
    }
}
