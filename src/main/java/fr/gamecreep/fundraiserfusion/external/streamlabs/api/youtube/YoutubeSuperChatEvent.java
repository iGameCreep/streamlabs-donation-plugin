package fr.gamecreep.fundraiserfusion.external.streamlabs.api.youtube;

import fr.gamecreep.fundraiserfusion.external.streamlabs.api.core.AMoneyEvent;

public class YoutubeSuperChatEvent extends AMoneyEvent {
    public YoutubeSuperChatEvent(String name,
                                 String comment,
                                 String from,
                                 int amount,
                                 String displayString,
                                 String currency) {
        super(name, comment, from, amount, displayString, currency);
    }
}
