package fr.gamecreep.fundraiserfusion.external.streamlabs.api.youtube;

import fr.gamecreep.fundraiserfusion.external.streamlabs.api.core.AMessageEvent;
import lombok.Getter;

@Getter
public class YoutubeMembershipGift extends AMessageEvent {
    private final int amout;
    private final int level;
    private final String levelName;
    private final String giftMembershipsLevelName;
    private final int giftMembershipsCount;

    public YoutubeMembershipGift(String name,
                                 String message,
                                 int amount,
                                 int level,
                                 String levelName,
                                 String giftMembershipsLevelName,
                                 int giftMembershipsCount) {
        super(name, message);
        this.amout = amount;
        this.level = level;
        this.levelName = levelName;
        this.giftMembershipsLevelName = giftMembershipsLevelName;
        this.giftMembershipsCount = giftMembershipsCount;
    }
}
