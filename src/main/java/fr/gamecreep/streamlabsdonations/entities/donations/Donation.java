package fr.gamecreep.streamlabsdonations.entities.donations;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Donation {
    private String donorName;
    private BigDecimal donationAmount;
    private String formattedAmount;
    private String message;
}
