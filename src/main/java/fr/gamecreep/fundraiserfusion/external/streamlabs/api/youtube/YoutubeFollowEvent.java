package fr.gamecreep.fundraiserfusion.external.streamlabs.api.youtube;

import fr.gamecreep.fundraiserfusion.external.streamlabs.api.core.ACommonEvent;
import lombok.Getter;

@Getter
public class YoutubeFollowEvent extends ACommonEvent {
    public YoutubeFollowEvent(String name) {
        super(name);
    }
}
