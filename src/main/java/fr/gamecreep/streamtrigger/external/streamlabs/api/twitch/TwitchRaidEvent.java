package fr.gamecreep.streamtrigger.external.streamlabs.api.twitch;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ACommonEvent;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TwitchRaidEvent extends ACommonEvent {
    private final int raiders;
    private final int viewers;

    public TwitchRaidEvent(String username, int raiders, int viewers) {
        super(username);
        this.raiders = raiders;
        this.viewers = viewers;
    }

    @Override
    public Map<String, String> getExportedData() {
        Map<String, String> map = new HashMap<>(Map.of(
                "raiders", String.valueOf(this.raiders),
                "viewers", String.valueOf(this.viewers)
        ));

        map.putAll(super.getExportedData());
        return map;
    }
}
