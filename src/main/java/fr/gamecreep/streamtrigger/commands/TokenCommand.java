package fr.gamecreep.streamtrigger.commands;

import fr.gamecreep.streamtrigger.StreamTrigger;
import fr.gamecreep.streamtrigger.exceptions.StreamTriggerException;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TokenCommand implements TabExecutor {

    private final StreamTrigger plugin;

    public TokenCommand(StreamTrigger plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender commandSender,
                             @NonNull Command command,
                             @NonNull String label,
                             @NonNull String @NonNull[] args
    ) {
        if (args.length > 0) {
            String token = args[0];

            this.sendMessage(commandSender, "Trying to load WebSocket...");

            try {
                this.plugin.loadStreamlabsFromToken(token);
                this.plugin.saveToken(token);
            } catch (StreamTriggerException e) {
                this.sendMessage(commandSender, e.getMessage());
            }
        } else {
            this.sendMessage(commandSender, "You need to provide the token to use. /info for more information on this error");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender commandSender,
                                      @NonNull Command command,
                                      @NonNull String label,
                                      @NonNull String @NonNull[] args
    ) {
        if (args.length == 0) {
            return Collections.singletonList("[token]");
        } else {
            return Collections.emptyList();
        }
    }

    private void sendMessage(CommandSender target, String message) {
        if (target instanceof Player player) {
            player.sendMessage(message);
        }
    }
}
