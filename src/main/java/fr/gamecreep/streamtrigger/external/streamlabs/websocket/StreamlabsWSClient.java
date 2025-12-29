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
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class StreamlabsWSClient {

    private final Gson gson = new Gson();
    private final StreamTrigger plugin;
    private WebSocketClient socket = null;

    public StreamlabsWSClient(StreamTrigger plugin, String wsToken) throws WebSocketException {
        this.plugin = plugin;

        this.loadWebSocket(wsToken);
    }

    private void loadWebSocket(String wsToken) throws WebSocketException {
        try {
            this.endWebSocket(false);

            URI uri = new URI(Constants.WEBSOCKET_ENDPOINT + wsToken);
            this.socket = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    plugin.getLogger().info("Loaded websocket");
                }

                @Override
                public void onMessage(String message) {
                    onSocketEvent(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    plugin.getLogger().warning("Websocket disconnected. Code: " + code + ". Reason: " + reason);
                }

                @Override
                public void onError(Exception e) {
                    plugin.getLogger().severe("WebSocket error: " + e.getMessage());
                }
            };

            socket.connect();
        } catch (Exception e) {
            throw new WebSocketException("Unable to load WebSocket client", e);
        }
    }

    public void endWebSocket(boolean log) {
        if (this.socket != null && this.socket.isOpen()) {
            this.socket.close();
            this.plugin.getLogger().info("WebSocket closed successfully !");
        } else {
            if (log) {
                this.plugin.getLogger().warning("Unable to close WebSocket.");
            }
        }
    }

    private void onSocketEvent(String message) {
        try {
            // API docs: https://dev.streamlabs.com/docs/socket-api
            JsonObject rawEvent = JsonParser.parseString(message).getAsJsonObject();
            if (!rawEvent.has("for") || !rawEvent.has("type")) {
                return;
            }

            EStreamLabsEventType eventType = EStreamLabsEventType.from(rawEvent.get("type").getAsString());
            EStreamLabsEventFor eventFor = EStreamLabsEventFor.from(rawEvent.get("for").getAsString());
            if (eventType == null || eventFor == null) {
                return;
            }

            EStreamLabsEvent event = EStreamLabsEvent.from(eventFor, eventType);

            if (event == null) {
                return;
            }

            JsonArray messageArray = rawEvent.getAsJsonArray("message");
            if (messageArray != null && !messageArray.isEmpty()) {
                ACommonEvent eventData = this.gson.fromJson(messageArray.get(0).getAsJsonObject(), event.getEventDataClass());

                this.plugin.getStreamEventHandler().handleStreamEvent(event, eventData);
            }
        } catch (Exception e) {
            this.plugin.getLogger().severe("Unable to parse event from StreamLabs WS: " + e.getMessage());
            this.endWebSocket(true);
        }
    }
}
