package fr.gamecreep.fundraiserfusion.external.streamlabs.api.twitch;

import fr.gamecreep.fundraiserfusion.external.streamlabs.api.core.ADonationEvent;
import lombok.Getter;

@Getter
public class TwitchCharityDonationEvent extends ADonationEvent {
    public TwitchCharityDonationEvent(String name, String message, String from, int amount, String formattedAmount, String currency) {
        super(name, message, from, amount, formattedAmount, currency);
    }
}
