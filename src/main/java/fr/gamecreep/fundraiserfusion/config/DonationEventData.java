package fr.gamecreep.fundraiserfusion.config;

import fr.gamecreep.fundraiserfusion.donations.entities.enums.Action;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class DonationEventData {
    private final UUID id;
    private final double threshold;
    private final DonationEventAction[] actions;

    @AllArgsConstructor
    @Getter
    public static class DonationEventAction {
        private final Action action;
        private final String data;
    }
}
