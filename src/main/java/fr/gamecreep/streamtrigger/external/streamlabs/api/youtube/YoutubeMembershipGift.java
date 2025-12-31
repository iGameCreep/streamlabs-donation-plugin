package fr.gamecreep.streamtrigger.external.streamlabs.api.youtube;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.AMessageEvent;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class YoutubeMembershipGift extends AMessageEvent {
    private final int amout;
    private final int level;
    private final String levelName;
    private final String giftMembershipsLevelName;
    private final int giftMembershipsCount;

    public YoutubeMembershipGift(String username,
                                 String message,
                                 int amount,
                                 int level,
                                 String levelName,
                                 String giftMembershipsLevelName,
                                 int giftMembershipsCount) {
        super(username, message);
        this.amout = amount;
        this.level = level;
        this.levelName = levelName;
        this.giftMembershipsLevelName = giftMembershipsLevelName;
        this.giftMembershipsCount = giftMembershipsCount;
    }

    @Override
    public Map<String, String> getExportedData() {
        Map<String, String> map = new HashMap<>();

        map.put("amount", String.valueOf(this.amout));
        map.put("level", String.valueOf(this.level));
        map.put("level_name", this.levelName);
        map.put("gift_memberships_level_name", this.giftMembershipsLevelName);
        map.put("gift_memberships_count", String.valueOf(this.giftMembershipsCount));
        map.putAll(super.getExportedData());

        return map;
    }
}
