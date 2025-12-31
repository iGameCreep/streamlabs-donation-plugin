package fr.gamecreep.streamtrigger.external.streamlabs.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.gamecreep.streamtrigger.Constants;
import fr.gamecreep.streamtrigger.StreamTrigger;
import fr.gamecreep.streamtrigger.exceptions.WebSocketException;
import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ACommonEvent;
import fr.gamecreep.streamtrigger.external.streamlabs.enums.EStreamLabsEvent;
import fr.gamecreep.streamtrigger.external.streamlabs.enums.EStreamLabsEventFor;
import fr.gamecreep.streamtrigger.external.streamlabs.enums.EStreamLabsEventType;
import io.socket.client.IO;
import io.socket.client.Socket;

public class StreamlabsWSClient {

    private final Gson gson = new Gson();
    private final StreamTrigger plugin;
    private Socket socket = null;

    public StreamlabsWSClient(StreamTrigger plugin, String wsToken) throws WebSocketException {
        this.plugin = plugin;

        this.loadWebSocket(wsToken);
    }

    private void loadWebSocket(String wsToken) throws WebSocketException {
        try {
            this.endWebSocket();

            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket"};
            options.query = "token=" + wsToken;

            this.socket = IO.socket(Constants.WEBSOCKET_ENDPOINT, options);

            socket.on(Socket.EVENT_CONNECT, args -> this.plugin.getLogger().info("Loaded websocket"));

            socket.on("event", this::onSocketEvent);

            socket.connect();
        } catch (Exception e) {
            throw new WebSocketException("Unable to load WebSocket client", e);
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
                if (!rawEvent.has("for")
                        || !rawEvent.has("type")
                        || !rawEvent.has("event_id")
                ) {
                    return;
                }

                final EStreamLabsEventType eventType = EStreamLabsEventType.from(rawEvent.get("type").getAsString());
                final EStreamLabsEventFor eventFor = EStreamLabsEventFor.from(rawEvent.get("for").getAsString());
                if (eventType == null || eventFor == null) {
                    return;
                }

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
