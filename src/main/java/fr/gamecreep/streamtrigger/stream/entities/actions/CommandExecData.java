package fr.gamecreep.streamtrigger.stream.entities.actions;

import fr.gamecreep.streamtrigger.stream.entities.actions.core.ACommonActionData;
import lombok.Getter;

@Getter
public class CommandExecData implements ACommonActionData {

    private final String command;

    public CommandExecData(String command) {
        this.command = command;
    }
}
