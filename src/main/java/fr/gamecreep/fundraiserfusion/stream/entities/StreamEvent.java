package fr.gamecreep.fundraiserfusion.stream.entities;

import fr.gamecreep.fundraiserfusion.donations.entities.enums.Action;
import fr.gamecreep.fundraiserfusion.external.streamlabs.enums.EStreamLabsEventFor;
import fr.gamecreep.fundraiserfusion.external.streamlabs.enums.EStreamLabsEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class StreamEvent {
    private final UUID id;
    @Nullable
    private final Double donationThreshold;
    private final StreamEventData eventData;
    private final StreamEventAction[] actions;

    @AllArgsConstructor
    @Getter
    public static class StreamEventData {
        private final EStreamLabsEventType eventType;
        private final EStreamLabsEventFor eventFor;
    }

    @AllArgsConstructor
    @Getter
    public static class StreamEventAction {
        private final Action action;
        private final String data;
    }
}
