package fr.gamecreep.fundraiserfusion.donations.events;

import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.donations.entities.DonationEventData;
import lombok.AllArgsConstructor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class SpawnEntityDonation {

    private final FundraiserFusion plugin;

    public void handleDonation(final DonationEventData.DonationEventAction action) {
        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            try {
                final EntityType entity = EntityType.valueOf(action.getData());

                player.getWorld().spawnEntity(player.getLocation(), entity);
            } catch (final IllegalArgumentException e) {
                this.plugin.getLogger().warning("Unable to find entity: " + action.getData());
            }
        }
    }
}
