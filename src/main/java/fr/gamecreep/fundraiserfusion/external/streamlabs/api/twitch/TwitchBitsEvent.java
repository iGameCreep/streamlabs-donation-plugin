package fr.gamecreep.fundraiserfusion.external.streamlabs.api.twitch;

import fr.gamecreep.fundraiserfusion.external.streamlabs.api.core.AMoneyEvent;
import lombok.Getter;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Getter
public class TwitchBitsEvent extends AMoneyEvent {

    public TwitchBitsEvent(String name,
                           String message,
                           String from,
                           int amount,
                           String currency) {
        super(name, message, from, amount, getFormattedBitsAmount(amount, currency), currency);
    }

     static String getFormattedBitsAmount(int amount, String currency) {
         if (amount < 0) {
             amount = 0;
         }

         // Example: 500 bits => $5.00 (if 1 bit = 0.01 USD)
         final double dollarAmount = amount * 0.01;

         final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
         currencyFormatter.setCurrency(Currency.getInstance(currency));

         return currencyFormatter.format(dollarAmount);
    }
}
