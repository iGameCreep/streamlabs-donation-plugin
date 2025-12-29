package fr.gamecreep.streamtrigger.commands;

import fr.gamecreep.streamtrigger.Constants;
import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender commandSender,
                             @NonNull Command command,
                             @NonNull String label,
                             @NonNull String @NonNull[] args
    ) {
        if (commandSender instanceof Player player) {
            // Header Component
            BaseComponent header = new ComponentBuilder()
                    .append(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------")
                    .append(ChatColor.RESET + " " + ChatColor.AQUA + "[Plugin] ")
                    .append(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------\n")
                    .build();

            // Main clickable message Component
            BaseComponent message = new ComponentBuilder()
                    .append(ChatColor.GREEN + "[Click here] ")
                    .append(ChatColor.GRAY + "for more info about the plugin!")
                    .event(new ClickEvent(ClickEvent.Action.OPEN_URL, Constants.WEBSITE_URL))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.YELLOW + "Go to plugin website")))
                    .build();

            // Footer Component
            BaseComponent footer = new ComponentBuilder()
                    .append("\n")
                    .append(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------")
                    .build();

            // Combine all parts into a single component
            TextComponent combinedMessage = new TextComponent();
            combinedMessage.addExtra(header);
            combinedMessage.addExtra(message);
            combinedMessage.addExtra(footer);

            player.spigot().sendMessage(combinedMessage);
        }

        return false;
    }
}
