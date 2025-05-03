package fr.gamecreep.streamtrigger.external.streamlabs.api.twitch;

import com.google.gson.annotations.SerializedName;
import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ASubscriptionEvent;
import lombok.Getter;

@Getter
public class TwitchSubscriptionEvent extends ASubscriptionEvent {
    @SerializedName("streak_months")
    private final int streakMonths;
    @SerializedName("sub_type")
    private final String subType;
    @SerializedName("sub_plan")
    private final String subPlan;
    @SerializedName("sub_plan_name")
    private final String subPlanName;

    public TwitchSubscriptionEvent(String name,
                                   String message,
                                   int months,
                                   int streakMonths,
                                   String subType,
                                   String subPlan,
                                   String subPlanName) {
        super(name, message, months);
        this.streakMonths = streakMonths;
        this.subType = subType;
        this.subPlan = subPlan;
        this.subPlanName = subPlanName;
    }
}
