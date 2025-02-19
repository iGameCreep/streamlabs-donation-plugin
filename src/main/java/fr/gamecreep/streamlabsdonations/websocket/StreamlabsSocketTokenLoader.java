package fr.gamecreep.streamlabsdonations.websocket;

import fr.gamecreep.streamlabsdonations.StreamLabsDonations;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StreamlabsSocketTokenLoader {
    private static final String TOKEN_ENDPOINT = "https://streamlabs.com/api/v2.0/socket/token";
    private final StreamlabsWebSocketClient webSocketClient;

    public StreamlabsSocketTokenLoader(String accessToken, StreamLabsDonations plugin) {
        this.webSocketClient = new StreamlabsWebSocketClient(plugin);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable tokenFetcher = () -> {
            String socketToken = getSocketToken(accessToken);
            if (socketToken != null) {
                this.loadWebSocket(socketToken);
            } else {
                Bukkit.getLogger().warning("Could not get websocket token");
            }
        };

        scheduler.scheduleAtFixedRate(tokenFetcher, 0, 5, TimeUnit.MINUTES);
    }

    private static String getSocketToken(String accessToken) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(TOKEN_ENDPOINT);

            httpGet.setHeader("Authorization", "Bearer " + accessToken);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(responseBody);
                return jsonObject.getString("socket_token");
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("Could not get websocket token");
            e.printStackTrace();
        }
        return null;
    }

    private void loadWebSocket(String socketToken) {
        this.webSocketClient.load(socketToken);
    }
}
