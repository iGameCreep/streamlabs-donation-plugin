package fr.gamecreep.streamtrigger.stream.entities.actions;

import lombok.Getter;

@Getter
public class CommandExecData {

    private final String command;

    public CommandExecData(String command) {
        this.command = command;
    }
}
