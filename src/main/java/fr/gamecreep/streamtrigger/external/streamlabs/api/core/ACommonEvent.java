package fr.gamecreep.streamtrigger.external.streamlabs.api.core;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public abstract class ACommonEvent {
    @SerializedName(value = "name", alternate = { "from" })
    private final String username;

    public Map<String, String> getExportedData() {
        return Map.of("username", this.username);
    }
}
