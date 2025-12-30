package fr.gamecreep.streamtrigger.external.streamlabs.api.core;

import lombok.Getter;

@Getter
public abstract class AFollowEvent extends ACommonEvent {
    protected AFollowEvent(String username) {
        super(username);
    }
}
