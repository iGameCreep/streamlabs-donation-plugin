package fr.gamecreep.fundraiserfusion.websocket;

import com.google.gson.Gson;
import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.config.SecretsFile;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StreamlabsSocketTokenLoader {

    private static final String TOKEN_ENDPOINT = "https://streamlabs.com/api/v2.0/socket/token";
    private final Gson gson = new Gson();

    public StreamlabsSocketTokenLoader(final FundraiserFusion plugin) {
        final StreamlabsWebSocketClient webSocketClient = new StreamlabsWebSocketClient(plugin);
        try {
            final SecretsFile secrets = this.getSecrets();

            if (secrets.getAccessToken() != null) {
                webSocketClient.load(this.getSocketToken(secrets.getAccessToken()));
            }
        } catch (FileNotFoundException e) {
            Bukkit.getLogger().warning("Could not get websocket token");
        }
    }

    private SecretsFile getSecrets() throws FileNotFoundException {
        // TODO: Rename plugin to FundraiserFusion bc else this wont work lol
        final String fileName = "plugins" + File.separator + "FundraiserFusion" + File.separator + "secrets.json";
        return this.gson.fromJson(new FileReader(fileName), SecretsFile.class);
    }

    private String getSocketToken(final String accessToken) {
        try (final HttpClient client = HttpClient.newHttpClient()) {
            final HttpRequest request = HttpRequest.newBuilder(URI.create(TOKEN_ENDPOINT))
                    .GET()
                    .setHeader("Accept", "application/json")
                    .setHeader("Authorization", "Bearer " + accessToken)
                    .build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final JSONObject jsonObject = new JSONObject(response.body());
            return jsonObject.getString("socket_token");
        } catch (final Exception e) {
            Thread.currentThread().interrupt();
            Bukkit.getLogger().warning("Could not get websocket token");
            e.printStackTrace();
        }

        return null;
    }
}
