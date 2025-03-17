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
        private int id;
        private String name;
        private double amount;
        private String formattedAmount;
        private String message;
        private String currency;
        private To to;
        private String from;
        @SerializedName("from_user_id")
        private String fromUserId;
        @SerializedName("_id")
        private String idString;

        @AllArgsConstructor
        @Getter
        public static class To {
            private String name;
        }
    }
}
