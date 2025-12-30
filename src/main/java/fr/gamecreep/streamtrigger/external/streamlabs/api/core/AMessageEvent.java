package fr.gamecreep.streamtrigger.external.streamlabs.api.core;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class AMessageEvent extends ACommonEvent {
    private final String message;

    protected AMessageEvent(String username, String message) {
        super(username);
        this.message = message;
    }

    @Override
    public Map<String, String> getExportedData() {
        Map<String, String> map = new HashMap<>(Map.of("message", this.message));

        map.putAll(super.getExportedData());
        return map;
    }
}
