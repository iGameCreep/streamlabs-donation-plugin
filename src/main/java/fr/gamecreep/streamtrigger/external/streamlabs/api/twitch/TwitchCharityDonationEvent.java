package fr.gamecreep.streamtrigger.external.streamlabs.api.twitch;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ADonationEvent;
import lombok.Getter;

@Getter
public class TwitchCharityDonationEvent extends ADonationEvent {
    public TwitchCharityDonationEvent(String name, String message, String from, int amount, String formattedAmount, String currency) {
        super(name, message, from, amount, formattedAmount, currency);
    }
}
