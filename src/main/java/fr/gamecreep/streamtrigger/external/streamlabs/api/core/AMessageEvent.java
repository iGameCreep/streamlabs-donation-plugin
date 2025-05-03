package fr.gamecreep.streamtrigger.external.streamlabs.api.core;

import lombok.Getter;

@Getter
public class AMessageEvent extends ACommonEvent {
    private final String message;

    public AMessageEvent(String name, String message) {
        super(name);
        this.message = message;
    }
}
