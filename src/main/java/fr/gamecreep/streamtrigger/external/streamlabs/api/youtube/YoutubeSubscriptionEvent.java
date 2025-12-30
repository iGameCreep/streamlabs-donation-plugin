package fr.gamecreep.streamtrigger.external.streamlabs.api.youtube;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ASubscriptionEvent;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class YoutubeSubscriptionEvent extends ASubscriptionEvent {
    private final int level;
    private final String levelName;
    private final int membershipLevel;
    private final String membershipLevelName;

    public YoutubeSubscriptionEvent(String username,
                                    String message,
                                    int months,
                                    int level,
                                    String levelName,
                                    int membershipLevel,
                                    String membershipLevelName) {
        super(username, message, months);
        this.level = level;
        this.levelName = levelName;
        this.membershipLevel = membershipLevel;
        this.membershipLevelName = membershipLevelName;
    }

    @Override
    public Map<String, String> getExportedData() {
        Map<String, String> map = new HashMap<>(Map.of(
                "level", String.valueOf(this.level),
                "level_name", this.levelName,
                "membership_level", String.valueOf(this.membershipLevel),
                "membership_level_name", this.membershipLevelName
        ));

        map.putAll(super.getExportedData());
        return map;
    }
}
