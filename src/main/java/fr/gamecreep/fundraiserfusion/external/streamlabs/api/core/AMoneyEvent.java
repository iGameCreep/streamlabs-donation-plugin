package fr.gamecreep.fundraiserfusion.external.streamlabs.api.core;

import lombok.Getter;

@Getter
public class AMoneyEvent extends AMessageEvent {
    private final String from;
    private final int amount;
    private final String formattedAmount;
    private final String currency;

    public AMoneyEvent(String name,
                          String message,
                          String from,
                          int amount,
                          String formattedAmount,
                          String currency) {
        super(name, message);
        this.from = from;
        this.amount = amount;
        this.formattedAmount = formattedAmount;
        this.currency = currency;
    }
}
