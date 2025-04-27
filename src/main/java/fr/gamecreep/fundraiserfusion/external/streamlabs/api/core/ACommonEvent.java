package fr.gamecreep.fundraiserfusion.external.streamlabs.api.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class ACommonEvent {
    private final String name;
}
