package fr.gamecreep.streamtrigger.external.streamlabs.api.streamlabs;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ADonationEvent;
import lombok.Getter;

@Getter
public class StreamLabsDonationEvent extends ADonationEvent {
    public StreamLabsDonationEvent(String username,
                                   String message,
                                   String to,
                                   int amount, String formattedAmount, String currency) {
        super(username, message, to, amount, formattedAmount, currency);
    }
}
