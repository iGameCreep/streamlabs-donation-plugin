package fr.gamecreep.streamtrigger.external.streamlabs.api.core;

import lombok.Getter;

@Getter
public abstract class ASubscriptionEvent extends AMessageEvent {
    private final int months;

    protected ASubscriptionEvent(String name,
                              String message,
                              int months) {
        super(name, message);
        this.months = months;
    }
}
