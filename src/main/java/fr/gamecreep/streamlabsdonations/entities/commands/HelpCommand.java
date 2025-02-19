package fr.gamecreep.streamlabsdonations.entities.commands;

import lombok.NonNull;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.TextComponent;

public class HelpCommand  implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String label, String[] strings) {
        if (commandSender instanceof Player) {
            Player playerSender = (Player) commandSender;

            ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://streamlabs-donations.netlify.app");
            TextComponent component = new TextComponent();
            component.setClickEvent(clickEvent);
            component.addExtra("&6-=-+-=- &3&n&lStreamLabs Donations - Help &6-=-+-=-");
            component.addExtra("&cManage donations in-game by your own !");
            component.addExtra("&eOur Website:");
            component.addExtra("&6&nhttps://streamlabs-donations.netlify.app");
            component.addExtra("&eTutorials:");
            component.addExtra("&6&nhttps://streamlabs-donations.netlify.app/tutorials");
            component.addExtra("&6-=-+-=- &3&n&lStreamLabs Donations - Enjoy &6-=-+-=-");

            playerSender.spigot().sendMessage(component);
            return true;
        }
        return false;
    }
}
