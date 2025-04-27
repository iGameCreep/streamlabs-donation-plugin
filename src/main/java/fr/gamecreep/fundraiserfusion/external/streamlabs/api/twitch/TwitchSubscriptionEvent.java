package fr.gamecreep.fundraiserfusion.external.streamlabs.api.twitch;

import fr.gamecreep.fundraiserfusion.external.streamlabs.api.core.ASubscriptionEvent;
import lombok.Getter;

@Getter
public class TwitchSubscriptionEvent extends ASubscriptionEvent {
    private final int streak_months;
    private final String sub_type;
    private final String sub_plan;
    private final String sub_plan_name;

    public TwitchSubscriptionEvent(String name,
                                   String message,
                                   int months,
                                   int streak_months,
                                   String sub_type,
                                   String sub_plan,
                                   String sub_plan_name) {
        super(name, message, months);
        this.streak_months = streak_months;
        this.sub_type = sub_type;
        this.sub_plan = sub_plan;
        this.sub_plan_name = sub_plan_name;
    }
}
