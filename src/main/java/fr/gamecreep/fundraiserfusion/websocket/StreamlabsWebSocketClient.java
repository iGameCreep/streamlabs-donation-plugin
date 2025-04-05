package fr.gamecreep.fundraiserfusion.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.donations.entities.Donation;
import fr.gamecreep.fundraiserfusion.donations.entities.api.DonationEvent;
import org.bukkit.Bukkit;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;

import java.net.URISyntaxException;

public class StreamlabsWebSocketClient {

    private static final String WEBSOCKET_ENDPOINT = "https://sockets.streamlabs.com";

    private final Gson gson = new Gson();
    private final FundraiserFusion plugin;
    private Socket socket = null;

    public StreamlabsWebSocketClient(final FundraiserFusion plugin) {
        this.plugin = plugin;
    }

    public void load(final String socketToken) {
        this.endWebSocket();
        this.loadWebSocket(socketToken);
    }

    private void loadWebSocket(final String socketToken) {
        try {
            final IO.Options options = new IO.Options();
            options.transports = new String[]{WebSocket.NAME};
            options.query = "token=" + socketToken;

            this.socket = IO.socket(WEBSOCKET_ENDPOINT, options);

            socket.on(Socket.EVENT_CONNECT, args -> this.plugin.getLogger().info("Loaded websocket"));

            socket.on("event", this::onSocketEvent);

            socket.connect();
        } catch (URISyntaxException e) {
            this.plugin.getLogger().warning("Unable to load the websocket.");
        }
    }

    public void endWebSocket() {
        if (this.socket != null && this.socket.isActive()) {
            this.socket.close();
            this.plugin.getLogger().info("WebSocket closed successfully !");
        } else {
            this.plugin.getLogger().warning("Unable to close WebSocket.");
        }
    }

    private void onSocketEvent(final Object[] args) {
        try {
            if (args.length > 0 && args[0] != null) {
                // API docs: https://dev.streamlabs.com/docs/socket-api
                final JsonObject rawEvent = JsonParser.parseString(args[0].toString()).getAsJsonObject();
                final String type = rawEvent.get("type").getAsString();

                if (type == null) {
                    return;
                }

                if (type.equals("donation")) {
                    DonationEvent event = gson.fromJson(args[0].toString(), DonationEvent.class);
                    handleDonationEvent(event);
                } else {
                    this.plugin.getLogger().info("Ignoring event: " + type);
                }
            }
        } catch (final Exception e) {
            this.plugin.getLogger().severe("Unable to parse event from StreamLabs WS: " + e.getMessage());
            this.endWebSocket();
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
