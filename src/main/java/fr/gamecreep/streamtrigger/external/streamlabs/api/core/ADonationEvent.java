package fr.gamecreep.streamtrigger.external.streamlabs.api.core;

import lombok.Getter;

@Getter
public abstract class ADonationEvent extends AMoneyEvent {

    protected ADonationEvent(String name, String message, String from, int amount, String formattedAmount, String currency) {
        super(name, message, from, amount, formattedAmount, currency);
    }
}
