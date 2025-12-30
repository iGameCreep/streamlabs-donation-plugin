package fr.gamecreep.streamtrigger.external.streamlabs.api.twitch;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ADonationEvent;
import lombok.Getter;

@Getter
public class TwitchCharityDonationEvent extends ADonationEvent {
    public TwitchCharityDonationEvent(String username,
                                      String message,
                                      int amount,
                                      String formattedAmount,
                                      String currency
    ) {
        super(username, message, null, amount, formattedAmount, currency);
    }
}
