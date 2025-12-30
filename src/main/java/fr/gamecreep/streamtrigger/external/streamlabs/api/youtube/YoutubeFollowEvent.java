package fr.gamecreep.streamtrigger.external.streamlabs.api.youtube;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.AFollowEvent;
import lombok.Getter;

@Getter
public class YoutubeFollowEvent extends AFollowEvent {
    public YoutubeFollowEvent(String username) {
        super(username);
    }
}
