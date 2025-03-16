package fr.gamecreep.fundraiserfusion.donations.entities.api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DonationEvent {
    private String type;
    private List<DonationMessage> message;

    @SerializedName("for")
    private String forAccount;

    @AllArgsConstructor
    @Getter
    public static class DonationMessage {
        private String name;
        private int months;
        private String message;
        private String emotes;
        @SerializedName("sub_plan")
        private String subPlan;
        @SerializedName("sub_plan_name")
        private String subPlanName;
        @SerializedName("sub_type")
        private String subType;

        @SerializedName("_id")
        private String id;
    }
}
