package fr.gamecreep.streamtrigger.external.streamlabs.api.core;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class AMoneyEvent extends AMessageEvent {
    private final int amount;
    private final String formattedAmount;
    private final String currency;

    protected AMoneyEvent(String username,
                       String message,
                       int amount,
                       String formattedAmount,
                       String currency
    ) {
        super(username, message);
        this.amount = amount;
        this.formattedAmount = formattedAmount;
        this.currency = currency;
    }

    @Override
    public Map<String, String> getExportedData() {
        Map<String, String> map = new java.util.HashMap<>(Map.of(
                "amount", String.valueOf(this.amount),
                "formattedAmount", this.formattedAmount,
                "currency", this.currency
        ));

        map.putAll(super.getExportedData());
        return map;
    }
}
