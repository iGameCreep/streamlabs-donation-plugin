package fr.gamecreep.fundraiserfusion.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.external.streamlabs.api.core.ACommonEvent;
import fr.gamecreep.fundraiserfusion.external.streamlabs.enums.EStreamLabsEvent;
import fr.gamecreep.fundraiserfusion.external.streamlabs.enums.EStreamLabsEventFor;
import fr.gamecreep.fundraiserfusion.external.streamlabs.enums.EStreamLabsEventType;
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
                if (!rawEvent.has("for") || !rawEvent.has("type")) {
                    return;
                }

                final EStreamLabsEventType eventType = EStreamLabsEventType.valueOf(rawEvent.get("type").getAsString());
                final EStreamLabsEventFor eventFor = EStreamLabsEventFor.valueOf(rawEvent.get("for").getAsString());
                final EStreamLabsEvent event = EStreamLabsEvent.from(eventFor, eventType);

                if (event == null) {
                    return;
                }

                final JsonArray messageArray = rawEvent.getAsJsonArray("message");
                if (messageArray != null && !messageArray.isEmpty()) {
                    final ACommonEvent eventData = this.gson.fromJson(messageArray.get(0).getAsJsonObject(), event.getEventDataClass());

                    this.plugin.getStreamEventHandler().handleStreamEvent(event, eventData);
                }
            }
        } catch (final Exception e) {
            this.plugin.getLogger().severe("Unable to parse event from StreamLabs WS: " + e.getMessage());
            this.endWebSocket();
        }
    }
}
