package fr.gamecreep.streamtrigger.config;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SecretsFile {
    @SerializedName("socket_token")
    private final String socketToken;
}
