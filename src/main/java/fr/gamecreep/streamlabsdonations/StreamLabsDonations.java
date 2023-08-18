package fr.gamecreep.streamlabsdonations;

import fr.gamecreep.streamlabsdonations.entities.commands.TestCommand;
import fr.gamecreep.streamlabsdonations.websocket.StreamlabsSocketTokenLoader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class StreamLabsDonations extends JavaPlugin {
    private StreamlabsSocketTokenLoader socketTokenLoader;

    @Override
    public void onEnable() {
        loadStreamlabs();
        loadCommands();
    }

    private void loadStreamlabs() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        String accessToken = config.getString("streamlabs.accessToken");
        this.socketTokenLoader = new StreamlabsSocketTokenLoader(accessToken);
        getLogger().info("Streamlabs integration loaded !");
    }

    private void loadCommands() {
        getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
        this.socketTokenLoader.endWebSocket();
        getLogger().info("Successfully stopped websocket and plugin !");
    }
}
