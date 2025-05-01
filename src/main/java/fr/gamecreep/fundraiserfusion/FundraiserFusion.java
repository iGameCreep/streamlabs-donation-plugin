package fr.gamecreep.fundraiserfusion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.gamecreep.fundraiserfusion.commands.InfoCommand;
import fr.gamecreep.fundraiserfusion.config.ConfigFile;
import fr.gamecreep.fundraiserfusion.external.streamlabs.enums.EStreamLabsEventFor;
import fr.gamecreep.fundraiserfusion.external.streamlabs.enums.EStreamLabsEventType;
import fr.gamecreep.fundraiserfusion.stream.StreamEventHandler;
import fr.gamecreep.fundraiserfusion.websocket.StreamlabsSocketTokenLoader;
import fr.gamecreep.fundraiserfusion.websocket.StreamlabsWebSocketClient;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public final class FundraiserFusion extends JavaPlugin {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(EStreamLabsEventType.class, new EStreamLabsEventType.EStreamLabsEventTypeDeserializer())
            .registerTypeAdapter(EStreamLabsEventFor.class, new EStreamLabsEventFor.EStreamLabsEventForDeserializer())
            .create();

    @Getter
    private StreamEventHandler streamEventHandler;
    private StreamlabsWebSocketClient webSocketClient;

    @Override
    public void onEnable() {
        this.loadCommands();
        this.loadStreamlabs();
        this.loadConfig();
    }

    @Override
    public void onDisable() {
        if (this.webSocketClient != null) {
            this.webSocketClient.endWebSocket();
        }
        this.getLogger().info("Successfully stopped websocket and plugin !");
    }

    private void loadCommands() {
        try {
            Objects.requireNonNull(getCommand("info")).setExecutor(new InfoCommand());
        } catch (NullPointerException e) {
         getLogger().warning("Unable to load commands");
        }
    }

    private void loadStreamlabs() {
        final StreamlabsSocketTokenLoader websocketLoader = new StreamlabsSocketTokenLoader(this);
        this.webSocketClient = websocketLoader.loadSocket();
    }

    private void loadConfig() {
        final String fileName = "plugins" + File.separator + "FundraiserFusion" + File.separator + "config.json";
        try {
            final ConfigFile config = this.gson.fromJson(new FileReader(fileName), ConfigFile.class);

            this.streamEventHandler = new StreamEventHandler(this, config.getEvents());
        } catch (FileNotFoundException e) {
            final String message = "Unable to load config file, stopping plugin...";

            this.getLogger().severe(message);
            this.getServer().broadcastMessage(message);

            this.getServer().getPluginManager().disablePlugin(this);
        }
    }
}
