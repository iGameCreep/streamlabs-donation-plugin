package fr.gamecreep.streamlabsdonations.websocket;

import fr.gamecreep.streamlabsdonations.StreamLabsDonations;
import fr.gamecreep.streamlabsdonations.donations.StreamlabsDonationEventEmitter;
import fr.gamecreep.streamlabsdonations.entities.donations.utils.Donation;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.bukkit.Bukkit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class StreamlabsWebSocketClient {
    private final StreamlabsDonationEventEmitter donationEventEmitter = new StreamlabsDonationEventEmitter();
    private Socket socket = null;
    private final StreamLabsDonations plugin;
    public StreamlabsWebSocketClient(StreamLabsDonations plugin) {
        this.plugin = plugin;
    }

    public void load(String socketToken) {
        this.endWebSocket();
        this.loadWebSocket(socketToken);
    }

    private void loadWebSocket(String socketToken) {
        try {
            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket"};
            options.query = "token=" + socketToken;

            this.socket = IO.socket("https://sockets.streamlabs.com", options);

            socket.on(Socket.EVENT_CONNECT, args -> Bukkit.getLogger().info("Loaded websocket"));

            socket.on("event", args -> {
                JSONObject eventData = (JSONObject) args[0];

                if (eventData.getString("type").equals("donation")) handleDonationEvent(eventData);
            });

            socket.connect();

            Thread.sleep(Long.MAX_VALUE);
        } catch (URISyntaxException | InterruptedException e) {
            Bukkit.getLogger().warning("Could not load the websocket");
            e.printStackTrace();
        }
    }

    public void endWebSocket() {
        if (this.socket != null && this.socket.isActive()) this.socket.close();
    }

    private void handleDonationEvent(JSONObject event) {
        JSONArray message = event.getJSONArray("message");
        for (int i = 0; i < message.length(); i++) {
            JSONObject donationData = message.getJSONObject(i);

            String donorName = donationData.getString("name");
            double donationAmount = donationData.getDouble("amount");
            String formattedAmount = donationData.getString("formatted_amount");
            String donationMessage = donationData.getString("message");

            Donation donation = new Donation(donorName, donationAmount, formattedAmount, donationMessage);
            Bukkit.getScheduler().runTask(this.plugin, () -> this.donationEventEmitter.onDonation(donation));
            this.plugin.getDonationCache().updateCache(donation);
        }
        Bukkit.getScheduler().runTask(this.plugin, () -> this.plugin.getScoreBoardUtils().updateScoreboard());
    }
}
