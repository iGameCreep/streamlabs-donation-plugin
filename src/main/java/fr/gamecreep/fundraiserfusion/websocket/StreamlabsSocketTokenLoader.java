package fr.gamecreep.fundraiserfusion.websocket;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.config.SecretsFile;
import fr.gamecreep.fundraiserfusion.donations.entities.Donation;
import lombok.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class StreamlabsSocketTokenLoader {

    private static final String DONATIONS_ENDPOINT = "https://streamlabs.com/api/v2.0/donations";
    private static final String SOCKET_TOKEN_ENDPOINT = "https://streamlabs.com/api/v2.0/socket/token";

    private final Gson gson = new Gson();
    private final FundraiserFusion plugin;

    public StreamlabsSocketTokenLoader(final FundraiserFusion plugin) {
        this.plugin = plugin;
    }

    public StreamlabsWebSocketClient loadSocket() {
        final StreamlabsWebSocketClient webSocketClient = new StreamlabsWebSocketClient(plugin);
        try {
            final SecretsFile secrets = this.getSecrets();

            if (secrets.getAccessToken() != null) {
                webSocketClient.load(this.getSocketToken(secrets.getAccessToken()));
            }
        } catch (FileNotFoundException e) {
            this.plugin.getLogger().warning("Could not load secrets file. Please make sure it has been generated correctly.");
        }

        return webSocketClient;
    }

    @NonNull
    public List<Donation> fetchDonations() {
        try (final HttpClient client = HttpClient.newHttpClient()) {
            final String accessToken = this.getSecrets().getAccessToken();

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(DONATIONS_ENDPOINT))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
                return gson.fromJson(jsonResponse.getAsJsonArray("data"), new TypeToken<List<Donation>>() {}.getType());
            } else {
                this.plugin.getLogger().warning("Failed to fetch donations: HTTP error code " + response.statusCode());
            }
        } catch (Exception e) {
            this.plugin.getLogger().warning("Could not retrieve donations.");
        }

        return List.of();
    }

    private SecretsFile getSecrets() throws FileNotFoundException {
        final String fileName = "plugins" + File.separator + "FundraiserFusion" + File.separator + "secrets.json";
        return this.gson.fromJson(new FileReader(fileName), SecretsFile.class);
    }

    private String getSocketToken(final String accessToken) {
        try (final HttpClient client = HttpClient.newHttpClient()) {
            final HttpRequest request = HttpRequest.newBuilder(URI.create(SOCKET_TOKEN_ENDPOINT))
                    .GET()
                    .setHeader("Accept", "application/json")
                    .setHeader("Authorization", "Bearer " + accessToken)
                    .build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                final JsonObject jsonObject = this.gson.fromJson(response.body(), JsonObject.class);
                return jsonObject.get("socket_token").getAsString();
                //TODO: Save token in file or idk
            } else {
                this.plugin.getLogger().warning("Unable to fetch socket token.");
            }
        } catch (final Exception e) {
            Thread.currentThread().interrupt();
            this.plugin.getLogger().warning("Could not get websocket token");
        }

        return null;
    }
}
