package fr.gamecreep.fundraiserfusion.external.streamlabs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EStreamLabsEventType {

    DONATION("donation"),
    FOLLOW("follow"),
    SUBSCRIPTION("subscription"),
    HOST("host"),
    BITS("bits"),
    RAID("raid"),
    TWITCH_CHARITY_DONATION("twitchcharitydonation"),
    SUPERCHAT("superchat"),
    MEMBERSHIP_GIFT("membershipGift");

    private final String stringValue;
}
