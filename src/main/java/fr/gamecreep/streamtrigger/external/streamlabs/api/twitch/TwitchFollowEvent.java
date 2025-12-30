package fr.gamecreep.streamtrigger.external.streamlabs.api.twitch;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.AFollowEvent;
import lombok.Getter;

@Getter
public class TwitchFollowEvent extends AFollowEvent {
    public TwitchFollowEvent(String username) {
        super(username);
    }
}
