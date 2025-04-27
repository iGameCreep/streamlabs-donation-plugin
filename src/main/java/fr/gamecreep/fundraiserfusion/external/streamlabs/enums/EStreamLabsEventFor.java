package fr.gamecreep.fundraiserfusion.external.streamlabs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EStreamLabsEventFor {

    STREAMLABS("streamlabs"),
    TWITCH_ACCOUNT("twitch_account"),
    YOUTUBE_ACCOUNT("youtube_account"),
    MIXER_ACCOUNT("mixer_account");

    private final String stringValue;
}
