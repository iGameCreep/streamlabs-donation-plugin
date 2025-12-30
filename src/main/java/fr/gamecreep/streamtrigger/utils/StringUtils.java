package fr.gamecreep.streamtrigger.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    public static String stripLeadingSlash(String command) {
        return command != null && command.startsWith("/") ? command.substring(1) : command;
    }

    public static String replaceCommandArguments(String command, Map<String, String> args) {
        for (Map.Entry<String, String> entry : args.entrySet()) {
            String literal = "{" + entry.getKey() + "}";
            if (command.contains(literal)) {
                command = command.replaceAll(literal, entry.getValue());
            }
        }
        return command;
    }
}
