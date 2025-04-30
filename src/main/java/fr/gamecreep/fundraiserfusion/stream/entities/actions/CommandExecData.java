package fr.gamecreep.fundraiserfusion.stream.entities.actions;

import fr.gamecreep.fundraiserfusion.stream.entities.actions.core.ACommonActionData;
import lombok.Getter;

@Getter
public class CommandExecData extends ACommonActionData {

    private final String command;

    public CommandExecData(String command) {
        this.command = command;
    }
}
