package fr.gamecreep.streamlabsdonations.websocket;

import fr.gamecreep.streamlabsdonations.donations.StreamlabsDonationEventEmitter;
import fr.gamecreep.streamlabsdonations.entities.donations.Donation;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.bukkit.Bukkit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URISyntaxException;

public class StreamlabsWebSocketClient {
    private final StreamlabsDonationEventEmitter donationEventEmitter = new StreamlabsDonationEventEmitter();
    private Socket socket;
    public StreamlabsWebSocketClient() {}

    public void load(String socketToken) {
        this.socket.close();
        this.loadWebSocket(socketToken);
    }

    private void loadWebSocket(String socketToken) {
        try {
            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket"};
            options.query = "token=" + socketToken;

            this.socket = IO.socket("https://sockets.streamlabs.com", options);

            socket.on(Socket.EVENT_CONNECT, args1 -> {
                Bukkit.getLogger().info("Loaded websocket");
            });

            socket.on("event", args1 -> {
                JSONArray eventData = (JSONArray) args1[0];

                for (int i = 0; i < eventData.length(); i++) {
                    JSONObject event = eventData.getJSONObject(i);
                    String eventType = event.getString("type");

                    if (eventType.equals("donation")) {
                        handleDonationEvent(event);
                    }
                }
            });

            socket.connect();

            Thread.sleep(Long.MAX_VALUE);
        } catch (URISyntaxException | InterruptedException e) {
            Bukkit.getLogger().warning("Could not load teh websocket");
            e.printStackTrace();
        }
    }

    public void endWebSocket() {
        this.socket.close();
    }

    private void handleDonationEvent(JSONObject event) {
        JSONArray message = event.getJSONArray("message");
        JSONObject donationData = message.getJSONObject(0);

        String donorName = donationData.getString("name");
        String donationAmount = donationData.getString("amount");
        String formattedAmount = donationData.getString("formatted_amount");
        String donationMessage = donationData.getString("message");

        Donation donation = new Donation();
        donation.setDonorName(donorName);
        donation.setDonationAmount(new BigDecimal(donationAmount));
        donation.setFormattedAmount(formattedAmount);
        donation.setMessage(donationMessage);

        this.donationEventEmitter.onDonation(donation);
    }
}
