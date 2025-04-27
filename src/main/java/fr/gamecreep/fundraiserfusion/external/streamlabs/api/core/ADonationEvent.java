package fr.gamecreep.fundraiserfusion.external.streamlabs.api.core;

import lombok.Getter;

@Getter
public abstract class ADonationEvent extends AMoneyEvent {

    public ADonationEvent(String name, String message, String from, int amount, String formattedAmount, String currency) {
        super(name, message, from, amount, formattedAmount, currency);
    }
}
