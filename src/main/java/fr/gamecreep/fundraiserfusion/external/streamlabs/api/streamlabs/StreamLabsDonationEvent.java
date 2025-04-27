package fr.gamecreep.fundraiserfusion.external.streamlabs.api.streamlabs;

import fr.gamecreep.fundraiserfusion.external.streamlabs.api.core.ADonationEvent;
import lombok.Getter;

@Getter
public class StreamLabsDonationEvent extends ADonationEvent {
    public StreamLabsDonationEvent(String name, String message, String from, int amount, String formattedAmount, String currency) {
        super(name, message, from, amount, formattedAmount, currency);
    }
}
