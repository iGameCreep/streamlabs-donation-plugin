package fr.gamecreep.streamtrigger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.gamecreep.streamtrigger.commands.InfoCommand;
import fr.gamecreep.streamtrigger.commands.TokenCommand;
import fr.gamecreep.streamtrigger.config.ConfigFile;
import fr.gamecreep.streamtrigger.config.SecretsFile;
import fr.gamecreep.streamtrigger.exceptions.StreamTriggerException;
import fr.gamecreep.streamtrigger.exceptions.WebSocketException;
import fr.gamecreep.streamtrigger.external.streamlabs.enums.EStreamLabsEventFor;
import fr.gamecreep.streamtrigger.external.streamlabs.enums.EStreamLabsEventType;
import fr.gamecreep.streamtrigger.stream.StreamEventHandler;
import fr.gamecreep.streamtrigger.external.streamlabs.websocket.StreamlabsWSClient;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public final class StreamTrigger extends JavaPlugin {

    private final Path secretsFilePath = this.getFilePath(".secrets", "streamlabs.json");
    private final Path configFilePath = this.getFilePath("config.json");

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(EStreamLabsEventType.class, new EStreamLabsEventType.EStreamLabsEventTypeDeserializer())
            .registerTypeAdapter(EStreamLabsEventFor.class, new EStreamLabsEventFor.EStreamLabsEventForDeserializer())
            .create();

    @Getter
    private StreamEventHandler streamEventHandler;
    private StreamlabsWSClient webSocketClient;

    @Override
    public void onEnable() {
        if (!this.getDataFolder().exists() && !this.getDataFolder().mkdirs()) {
                this.getLogger().severe("Failed to create plugin data folder: " + this.getDataFolder().getAbsolutePath());
                this.getServer().getPluginManager().disablePlugin(this);
                return;
        }

        this.loadCommands();
        this.loadStreamlabs();
        this.loadConfig();
    }

    @Override
    public void onDisable() {
        if (this.webSocketClient != null) {
            this.webSocketClient.endWebSocket();
        }
        this.getLogger().info("Stopped WebSocket and plugin !");
    }

    public void saveToken(String wsToken) throws StreamTriggerException {
        try {
            Files.createDirectories(this.secretsFilePath.getParent());

            Files.writeString(secretsFilePath, this.gson.toJson(new SecretsFile(wsToken)));
        } catch (IOException e) {
            throw new StreamTriggerException("Unable to save secrets file", e);
        }
    }

    public void loadStreamlabsFromToken(@NonNull String token) throws WebSocketException {
        this.webSocketClient = new StreamlabsWSClient(this, token);
    }

    private void loadCommands() {
        try {
            Objects.requireNonNull(this.getCommand("info")).setExecutor(new InfoCommand());
            Objects.requireNonNull(this.getCommand("token")).setExecutor(new TokenCommand(this));
        } catch (NullPointerException e) {
            this.getLogger().warning("Unable to load commands");
        }
    }

    private void loadStreamlabs() {
        final SecretsFile secrets = this.loadSecrets();
        if (secrets == null) return;

        try {
            this.loadStreamlabsFromToken(secrets.getSocketToken());
        } catch (WebSocketException e) {
            this.getLogger().severe(e.getMessage());
            this.getServer().broadcastMessage(e.getMessage());
        }
    }

    private void loadConfig() {
        try (FileReader reader = new FileReader(this.configFilePath.toFile())) {
            ConfigFile config = this.gson.fromJson(reader, ConfigFile.class);
            this.streamEventHandler = new StreamEventHandler(this, config.getEvents());
        } catch (IOException e) {
            this.fileNotFound(e, "config", this.configFilePath.toAbsolutePath().toString());
        }
    }

    private SecretsFile loadSecrets() {
        try (FileReader reader = new FileReader(this.secretsFilePath.toFile())) {
            return this.gson.fromJson(reader, SecretsFile.class);
        } catch (IOException e) {
            this.fileNotFound(e, "secrets", this.secretsFilePath.toAbsolutePath().toString());
            return null;
        }
    }

    private void fileNotFound(Exception e, String fileName, String filePath) {
        String message = "Expected " + fileName + " file at: " + filePath;

        this.getLogger().severe(e.getMessage());

        this.getLogger().severe(message);
        this.getServer().broadcastMessage(message);
    }

    private Path getFilePath(String... paths) {
        Path pluginFolder = this.getDataFolder().toPath();
        return Paths.get(pluginFolder.toString(), paths);
    }
}
