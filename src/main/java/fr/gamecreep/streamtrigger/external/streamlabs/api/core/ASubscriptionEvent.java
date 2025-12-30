package fr.gamecreep.streamtrigger.external.streamlabs.api.core;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class ASubscriptionEvent extends AMessageEvent {
    private final int months;

    protected ASubscriptionEvent(String username,
                                 String message,
                                 int months) {
        super(username, message);
        this.months = months;
    }

    @Override
    public Map<String, String> getExportedData() {
        Map<String, String> map = new HashMap<>(Map.of(
                "months", String.valueOf(this.months)
        ));

        map.putAll(super.getExportedData());
        return map;
    }
}
