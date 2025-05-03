package fr.gamecreep.streamtrigger.external.streamlabs.api.twitch;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ACommonEvent;
import lombok.Getter;

@Getter
public class TwitchFollowEvent extends ACommonEvent {
    public TwitchFollowEvent(String name) {
        super(name);
    }
}
