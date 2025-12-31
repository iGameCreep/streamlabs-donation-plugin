package fr.gamecreep.streamtrigger.external.streamlabs.api.twitch;

import com.google.gson.annotations.SerializedName;
import fr.gamecreep.streamtrigger.external.streamlabs.api.core.ASubscriptionEvent;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public Map<String, String> getExportedData() {
        Map<String, String> map = new HashMap<>();

        map.put("streak_months", String.valueOf(this.streakMonths));
        map.put("sub_type", this.subType);
        map.put("sub_plan", this.subPlan);
        map.put("sub_plan_name", this.subPlanName);
        map.putAll(super.getExportedData());

        return map;
    }
}
