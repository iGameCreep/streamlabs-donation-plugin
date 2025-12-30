package fr.gamecreep.streamtrigger.external.streamlabs.api.youtube;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.AMoneyEvent;

import java.util.HashMap;
import java.util.Map;

public class YoutubeSuperChatEvent extends AMoneyEvent {
    private final int messageType;

    public YoutubeSuperChatEvent(String username,
                                 String comment,
                                 int amount,
                                 String displayString,
                                 String currency,
                                 int messageType) {
        super(username, comment, amount, displayString, currency);
        this.messageType = messageType;
    }

    @Override
    public Map<String, String> getExportedData() {
        Map<String, String> map = new HashMap<>(Map.of(
                "message_type", String.valueOf(messageType)
        ));

        map.putAll(super.getExportedData());
        return map;
    }
}
