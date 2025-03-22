package fr.gamecreep.fundraiserfusion.websocket;

import com.google.gson.Gson;
import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.donations.entities.Donation;
import fr.gamecreep.fundraiserfusion.donations.entities.api.DonationEvent;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;

@Slf4j(topic = "StreamLabs-WS")
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

            socket.on(Socket.EVENT_CONNECT, args -> log.info("Loaded websocket"));

            socket.on("event", args -> {
                if (args.length > 0 && args[0] != null) {
                    final DonationEvent event = this.gson.fromJson(args[0].toString(), DonationEvent.class);
                    this.handleDonationEvent(event);
                }
            });

            socket.connect();
        } catch (URISyntaxException e) {
            log.warn("Unable to load the websocket.");
        }
    }

    public void endWebSocket() {
        if (this.socket != null && this.socket.isActive()) this.socket.close();
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

            this.plugin.getDonationGoalsExecutor().handleDonation(donation);
            this.plugin.addDonation(donation);
        }
    }
}
