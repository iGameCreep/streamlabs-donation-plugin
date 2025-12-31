package fr.gamecreep.streamtrigger.external.streamlabs.api.twitch;

import fr.gamecreep.streamtrigger.external.streamlabs.api.core.AMoneyEvent;
import lombok.Getter;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
public class TwitchBitsEvent extends AMoneyEvent {
    private final String emotes;

    public TwitchBitsEvent(String username,
                           String message,
                           String emotes,
                           double amount,
                           String currency) {
        super(username, message, amount, getFormattedBitsAmount(amount, currency), currency);
        this.emotes = emotes;
    }

     static String getFormattedBitsAmount(double amount, String currency) {
         if (amount < 0) {
             amount = 0;
         }

         // Example: 500 bits => $5.00 (if 1 bit = 0.01 USD)
         double dollarAmount = amount * 0.01;

         NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
         currencyFormatter.setCurrency(Currency.getInstance(currency));

         return currencyFormatter.format(dollarAmount);
    }

    @Override
    public Map<String, String> getExportedData() {
        Map<String, String> map = new HashMap<>();

        map.put("emotes", this.emotes);
        map.putAll(super.getExportedData());

        return map;
    }
}
