package fr.gamecreep.fundraiserfusion.websocket;

import com.google.gson.Gson;
import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.donations.entities.Donation;
import fr.gamecreep.fundraiserfusion.donations.entities.api.DonationEvent;
import org.bukkit.Bukkit;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class StreamlabsWebSocketClient {

    //TODO: USE PROD ENDPOINT
    //private static final String WEBSOCKET_ENDPOINT = "wss://sockets.streamlabs.com";
    private static final String WEBSOCKET_ENDPOINT = "ws://localhost:8080";

    private final Gson gson = new Gson();
    private final FundraiserFusion plugin;
    private WebSocketClient webSocketClient;

    public StreamlabsWebSocketClient(final FundraiserFusion plugin) {
        this.plugin = plugin;
    }

    public void load(final String socketToken) {
        this.endWebSocket();
        this.loadWebSocket(socketToken);
    }

    private void loadWebSocket(final String socketToken) {
        try {
            final URI uri = new URI(WEBSOCKET_ENDPOINT + "?token=" + socketToken);

            this.webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(final ServerHandshake handshakedata) {
                    plugin.getLogger().info("Loaded websocket");
                }

                @Override
                public void onMessage(final String message) {
                    final DonationEvent event = gson.fromJson(message, DonationEvent.class);
                    handleDonationEvent(event);
                }

                @Override
                public void onClose(final int code, final String reason, final boolean remote) {
                    plugin.getLogger().info("WebSocket closed: " + reason);
                }

                @Override
                public void onError(final Exception ex) {
                    plugin.getLogger().warning("WebSocket error: " + ex.getMessage());
                }
            };

            this.webSocketClient.connect();
        } catch (URISyntaxException e) {
            this.plugin.getLogger().warning("Unable to load the websocket.");
        }
    }

    public void endWebSocket() {
        if (this.webSocketClient != null && this.webSocketClient.isOpen()) {
            this.webSocketClient.close();
        }
    }

    private void handleDonationEvent(final DonationEvent event) {
        for (final DonationEvent.DonationMessage donationData : event.getMessage()) {
            if (!event.getType().equals("donation")) continue;

            final Donation donation = new Donation(
                    donationData.getName(),
                    donationData.getAmount(),
                    donationData.getFormattedAmount(),
                    donationData.getMessage()
            );

            Bukkit.getScheduler().runTask(this.plugin, () -> {
                this.plugin.getDonationGoalsExecutor().handleDonation(donation);
                this.plugin.addDonation(donation);
            });
        }
    }
}
