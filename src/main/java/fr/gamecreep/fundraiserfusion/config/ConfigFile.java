package fr.gamecreep.fundraiserfusion.config;

import fr.gamecreep.fundraiserfusion.donations.entities.DonationEventData;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConfigFile {
    private final DonationEventData[] events;
}
