package fr.gamecreep.fundraiserfusion.external.streamlabs.api.twitch;

import fr.gamecreep.fundraiserfusion.external.streamlabs.api.core.ACommonEvent;
import lombok.Getter;

@Getter
public class TwitchRaidEvent extends ACommonEvent {
    private final int raiders;
    private final int viewers;

    public TwitchRaidEvent(String name, int raiders, int viewers) {
        super(name);
        this.raiders = raiders;
        this.viewers = viewers;
    }
}
