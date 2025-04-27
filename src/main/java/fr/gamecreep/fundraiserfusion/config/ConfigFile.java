package fr.gamecreep.fundraiserfusion.config;

import fr.gamecreep.fundraiserfusion.stream.entities.StreamEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConfigFile {
    private final int fileVersion;
    private final ConfigSettings settings;
    private final StreamEvent[] events;

    @AllArgsConstructor
    @Getter
    public static class ConfigSettings {
        private boolean cumulateDonationEvents;
    }
}
