package fr.gamecreep.fundraiserfusion.stream;

import com.google.gson.Gson;
import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.external.streamlabs.api.core.ACommonEvent;
import fr.gamecreep.fundraiserfusion.external.streamlabs.api.core.ADonationEvent;
import fr.gamecreep.fundraiserfusion.external.streamlabs.enums.EStreamLabsEvent;
import fr.gamecreep.fundraiserfusion.stream.entities.StreamEvent;
import fr.gamecreep.fundraiserfusion.stream.entities.actions.CommandExecData;
import fr.gamecreep.fundraiserfusion.stream.entities.actions.core.ACommonActionData;
import fr.gamecreep.fundraiserfusion.stream.entities.enums.Action;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.util.Arrays;
import java.util.Comparator;

public class StreamEventHandler {

    private final Gson gson = new Gson();

    private final FundraiserFusion plugin;
    private final StreamEvent[] streamEvents;

    public StreamEventHandler(final FundraiserFusion plugin, final StreamEvent[] events) {
        this.plugin = plugin;
        this.streamEvents = events;

        Arrays.sort(this.streamEvents, Comparator
                .comparing(StreamEvent::getDonationThreshold, Comparator.nullsLast(Comparator.naturalOrder()))
                .reversed()
        );
    }

    public void handleStreamEvent(final EStreamLabsEvent event, final ACommonEvent eventData) {
        StreamEvent.StreamEventData data;
        for (final StreamEvent streamEvent : this.streamEvents) {
            data = streamEvent.getEventData();

            if (event.getEventFor().equals(data.getEventFor()) &&
                event.getEventType().equals(data.getEventType())) {

                if (eventData instanceof final ADonationEvent donationEvent &&
                    streamEvent.getDonationThreshold() != null &&
                    streamEvent.getDonationThreshold() > donationEvent.getAmount()) {
                    return;
                }

                for (final StreamEvent.StreamEventAction action : streamEvent.getActions()) {
                    final ACommonActionData actionData = this.gson.fromJson(action.getData(), action.getAction().getDataClass());

                    if (action.getAction().equals(Action.COMMAND_EXEC)) {
                        this.handleCommandExec(actionData);
                    }
                }
            }
        }
    }

    private void handleCommandExec(final ACommonActionData actionData) {
        if (actionData instanceof final CommandExecData commandExecData) {
            final Server server = this.plugin.getServer();
            final String command = this.stripLeadingSlash(commandExecData.getCommand());

            System.out.println(command);

            Bukkit.getScheduler().runTask(this.plugin, () ->
                    server.dispatchCommand(server.getConsoleSender(), command)
            );
        }
    }

    private String stripLeadingSlash(final String command) {
        return command != null && command.startsWith("/") ? command.substring(1) : command;
    }
}
