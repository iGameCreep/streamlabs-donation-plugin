package fr.gamecreep.streamlabsdonations.websocket;

import com.google.gson.Gson;
import fr.gamecreep.streamlabsdonations.StreamLabsDonations;
import fr.gamecreep.streamlabsdonations.donations.StreamlabsDonationEventEmitter;
import fr.gamecreep.streamlabsdonations.donations.entities.Donation;
import fr.gamecreep.streamlabsdonations.donations.entities.api.DonationEvent;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import org.bukkit.Bukkit;

import java.net.URISyntaxException;

public class StreamlabsWebSocketClient {

    private final Gson gson = new Gson();

    private final StreamlabsDonationEventEmitter donationEventEmitter = new StreamlabsDonationEventEmitter();
    private final StreamLabsDonations plugin;
    private Socket socket = null;

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
            options.transports = new String[]{WebSocket.NAME};
            options.query = "token=" + socketToken;

            this.socket = IO.socket("https://sockets.streamlabs.com", options);

            socket.on(Socket.EVENT_CONNECT, args -> Bukkit.getLogger().info("Loaded websocket"));

            socket.on("event", args -> {
                if (args.length > 0 && args[0] != null) {
                    final DonationEvent event = this.gson.fromJson(args[0].toString(), DonationEvent.class);
                    this.handleDonationEvent(event);
                }
            });

            socket.connect();

            Thread.sleep(Long.MAX_VALUE);
        } catch (URISyntaxException | InterruptedException e) {
            Thread.currentThread().interrupt();
            Bukkit.getLogger().warning("Could not load the websocket");
            e.printStackTrace();
        }
    }

    public void endWebSocket() {
        if (this.socket != null && this.socket.isActive()) this.socket.close();
    }

    private void handleDonationEvent(DonationEvent event) {
        for (DonationEvent.DonationMessage donationData : event.getMessage()) {
            if (!event.getType().equals("donation")) continue;

            String donorName = donationData.getName();
            double donationAmount = 5.00;
            String formattedAmount = "$5.00";
            String donationMessage = donationData.getMessage();

            Donation donation = new Donation(donorName, donationAmount, formattedAmount, donationMessage);

            Bukkit.getScheduler().runTask(this.plugin, () -> this.donationEventEmitter.onDonation(donation));
            this.plugin.getDonationCache().updateCache(donation);
        }

        Bukkit.getScheduler().runTask(this.plugin, () -> this.plugin.getScoreBoardUtils().updateScoreboard());
    }
}
