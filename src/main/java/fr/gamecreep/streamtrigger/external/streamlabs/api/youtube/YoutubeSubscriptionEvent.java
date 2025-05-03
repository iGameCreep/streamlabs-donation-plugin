package fr.gamecreep.streamtrigger.external.streamlabs.api.youtube;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ASubscriptionEvent;
import lombok.Getter;

@Getter
public class YoutubeSubscriptionEvent extends ASubscriptionEvent {
    private final int level;
    private final String levelName;
    private final int membershipLevel;
    private final String membershipLevelName;

    public YoutubeSubscriptionEvent(String name,
                                    String message,
                                    int months,
                                    int level,
                                    String levelName,
                                    int membershipLevel,
                                    String membershipLevelName) {
        super(name, message, months);
        this.level = level;
        this.levelName = levelName;
        this.membershipLevel = membershipLevel;
        this.membershipLevelName = membershipLevelName;
    }
}
