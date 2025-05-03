package fr.gamecreep.fundraiserfusion.commands;

import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.exceptions.FundraiserFusionException;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TokenCommand implements TabExecutor {

    private final FundraiserFusion plugin;

    public TokenCommand(@NonNull final FundraiserFusion plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull final CommandSender commandSender,
                             @NonNull final Command command,
                             @NonNull final String label,
                             @NonNull final String @NonNull[] args
    ) {
        if (args.length > 0) {
            final String token = args[0];

            this.sendMessage(commandSender, "Trying to load WebSocket...");

            try {
                this.plugin.loadStreamlabsFromToken(token);
                this.plugin.saveToken(token);
            } catch (FundraiserFusionException e) {
                this.sendMessage(commandSender, e.getMessage());
            }
        } else {
            this.sendMessage(commandSender, "You need to provide the token to use. /info for more information on this error");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull final CommandSender commandSender,
                             @NonNull final Command command,
                             @NonNull final String label,
                             @NonNull final String @NonNull[] args
    ) {
        if (args.length == 0) {
            return Collections.singletonList("[token]");
        } else {
            return Collections.emptyList();
        }
    }

    private void sendMessage(final CommandSender target, final String message) {
        if (target instanceof final Player player) {
            player.sendMessage(message);
        }
    }
}
