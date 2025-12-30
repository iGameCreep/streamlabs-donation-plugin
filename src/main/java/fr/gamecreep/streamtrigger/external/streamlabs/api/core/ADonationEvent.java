package fr.gamecreep.streamtrigger.external.streamlabs.api.core;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class ADonationEvent extends AMoneyEvent {
    private final String to;

    protected ADonationEvent(String username,
                             String message,
                             String to,
                             int amount,
                             String formattedAmount,
                             String currency
    ) {
        super(username, message, amount, formattedAmount, currency);
        this.to = to != null ? to : "null";
    }

    @Override
    public Map<String, String> getExportedData() {
        Map<String, String> map = new HashMap<>(Map.of(
                "to", this.to
        ));

        map.putAll(super.getExportedData());
        return map;
    }
}
