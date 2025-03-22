package fr.gamecreep.fundraiserfusion.donations.events;

import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.config.DonationEventData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Slf4j(topic = "Spawn Entity Donation")
public class SpawnEntityDonation {

    private final FundraiserFusion plugin;

    public void handleDonation(final DonationEventData.DonationEventAction action) {
        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            try {
                final EntityType entity = EntityType.valueOf(action.getData());

                player.getWorld().spawnEntity(player.getLocation(), entity);
            } catch (final IllegalArgumentException e) {
                log.warn("Unable to find entity: {}", action.getData());
            }
        }
    }
}
