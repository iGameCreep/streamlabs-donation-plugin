package fr.gamecreep.streamlabsdonations.config;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class SecretsFile {
    @SerializedName("access_token")
    private final String accessToken;
    @SerializedName("refresh_token")
    private final String refreshToken;
    @SerializedName("expires_on")
    private final Date expiresOn;
}
