package fr.gamecreep.streamtrigger.external.streamlabs.enums;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ACommonEvent;
import fr.gamecreep.streamtrigger.external.streamlabs.api.streamlabs.StreamLabsDonationEvent;
import fr.gamecreep.streamtrigger.external.streamlabs.api.twitch.*;
import fr.gamecreep.streamtrigger.external.streamlabs.api.youtube.YoutubeFollowEvent;
import fr.gamecreep.streamtrigger.external.streamlabs.api.youtube.YoutubeMembershipGift;
import fr.gamecreep.streamtrigger.external.streamlabs.api.youtube.YoutubeSubscriptionEvent;
import fr.gamecreep.streamtrigger.external.streamlabs.api.youtube.YoutubeSuperChatEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
@Getter
public enum EStreamLabsEvent {

    STREAMLABS_DONATION(EStreamLabsEventFor.STREAMLABS, EStreamLabsEventType.DONATION, StreamLabsDonationEvent.class),
    TWITCH_FOLLOW(EStreamLabsEventFor.TWITCH_ACCOUNT, EStreamLabsEventType.FOLLOW, TwitchFollowEvent.class),
    TWITCH_SUBSCRIPTION(EStreamLabsEventFor.TWITCH_ACCOUNT, EStreamLabsEventType.SUBSCRIPTION, TwitchSubscriptionEvent.class),
    TWITCH_BITS(EStreamLabsEventFor.TWITCH_ACCOUNT, EStreamLabsEventType.BITS, TwitchBitsEvent.class),
    TWITCH_RAID(EStreamLabsEventFor.TWITCH_ACCOUNT, EStreamLabsEventType.RAID, TwitchRaidEvent.class),
    TWITCH_CHARITY_DONATION(EStreamLabsEventFor.TWITCH_ACCOUNT, EStreamLabsEventType.TWITCH_CHARITY_DONATION, TwitchCharityDonationEvent.class),

    YOUTUBE_FOLLOW(EStreamLabsEventFor.YOUTUBE_ACCOUNT, EStreamLabsEventType.FOLLOW, YoutubeFollowEvent.class),
    YOUTUBE_SUBSCRIPTION(EStreamLabsEventFor.YOUTUBE_ACCOUNT, EStreamLabsEventType.SUBSCRIPTION, YoutubeSubscriptionEvent.class),
    YOUTUBE_SUPERCHAT(EStreamLabsEventFor.YOUTUBE_ACCOUNT, EStreamLabsEventType.SUPERCHAT, YoutubeSuperChatEvent.class),
    YOUTUBE_MEMBERSHIP_GIFT(EStreamLabsEventFor.YOUTUBE_ACCOUNT, EStreamLabsEventType.MEMBERSHIP_GIFT, YoutubeMembershipGift.class);

    private final EStreamLabsEventFor eventFor;
    private final EStreamLabsEventType eventType;
    private final Class<? extends ACommonEvent> eventDataClass;

    @Nullable
    public static EStreamLabsEvent from(EStreamLabsEventFor eventFor, EStreamLabsEventType eventType) {
        for (final EStreamLabsEvent event : EStreamLabsEvent.values()) {
            if (event.getEventFor() == eventFor && event.getEventType() == eventType) {
                return event;
            }
        }
        return null;
    }
}
