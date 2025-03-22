package fr.gamecreep.fundraiserfusion.donations.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Action {
    SPAWN_ENTITY("SPAWN_ENTITY"),
    SET_WEATHER("SET_WEATHER"),
    COMMAND_EXEC("COMMAND_EXEC");

    private final String name;
}
