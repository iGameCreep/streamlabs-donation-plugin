package fr.gamecreep.fundraiserfusion.donations.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Donation {
    @SerializedName("name")
    private final String donorName;
    @SerializedName("amount")
    private final double donationAmount;
    private String currency;
    private String message;

    public String getFormattedAmount() {
        return String.format("%s$%s", this.currency, this.donationAmount);
    }
}
