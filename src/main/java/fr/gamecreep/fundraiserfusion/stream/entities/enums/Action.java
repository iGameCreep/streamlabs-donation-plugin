package fr.gamecreep.fundraiserfusion.stream.entities.enums;

import fr.gamecreep.fundraiserfusion.stream.entities.actions.CommandExecData;
import fr.gamecreep.fundraiserfusion.stream.entities.actions.core.ACommonActionData;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Action {
    COMMAND_EXEC("COMMAND_EXEC", CommandExecData.class);

    private final String name;
    private final Class<? extends ACommonActionData> dataClass;
}
