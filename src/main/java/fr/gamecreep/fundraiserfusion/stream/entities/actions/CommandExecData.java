package fr.gamecreep.fundraiserfusion.stream.entities.actions;

import fr.gamecreep.fundraiserfusion.stream.entities.actions.core.ACommonAction;
import lombok.Getter;

@Getter
public class CommandExecData extends ACommonAction {

    private final String command;

    public CommandExecData(String command) {
        this.command = command;
    }
}
