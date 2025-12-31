package fr.gamecreep.streamtrigger.stream;

import com.google.gson.Gson;
import fr.gamecreep.streamtrigger.StreamTrigger;
import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ACommonEvent;
import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ADonationEvent;
import fr.gamecreep.streamtrigger.external.streamlabs.enums.EStreamLabsEvent;
import fr.gamecreep.streamtrigger.stream.entities.StreamEvent;
import fr.gamecreep.streamtrigger.stream.entities.actions.CommandExecData;
import fr.gamecreep.streamtrigger.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.util.Arrays;
import java.util.Comparator;

public class StreamEventHandler {

    private final Gson gson = new Gson();

    private final StreamTrigger plugin;
    private final StreamEvent[] streamEvents;

    public StreamEventHandler(StreamTrigger plugin, StreamEvent[] events) {
        this.plugin = plugin;
        this.streamEvents = events;

        Arrays.sort(this.streamEvents, Comparator
                .comparing(StreamEvent::getDonationThreshold, Comparator.nullsLast(Comparator.naturalOrder()))
                .reversed()
        );
    }

    // TODO: Refactor with new configs
    public void handleStreamEvent(EStreamLabsEvent event, ACommonEvent eventData) {
        StreamEvent.StreamEventData data;
        for (StreamEvent streamEvent : this.streamEvents) {
            data = streamEvent.getEventData();

            if (event.getEventFor().equals(data.getEventFor()) &&
                event.getEventType().equals(data.getEventType())) {

                if (eventData instanceof ADonationEvent donationEvent &&
                    streamEvent.getDonationThreshold() != null &&
                    streamEvent.getDonationThreshold() > donationEvent.getAmount()) {
                    return;
                }

                for (StreamEvent.StreamEventAction action : streamEvent.getActions()) {
                    CommandExecData actionData = this.gson.fromJson(action.getData(), CommandExecData.class);

                    this.handleCommandExec(actionData);
                }
            }
        }
    }

    private void handleCommandExec(CommandExecData actionData) {
        Server server = this.plugin.getServer();
        String command = StringUtils.stripLeadingSlash(actionData.getCommand());

        Bukkit.getScheduler().runTask(this.plugin, () ->
                server.dispatchCommand(server.getConsoleSender(), command)
        );
    }
}
