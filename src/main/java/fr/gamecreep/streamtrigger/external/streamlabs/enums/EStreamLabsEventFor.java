package fr.gamecreep.streamtrigger.external.streamlabs.enums;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

@AllArgsConstructor
@Getter
public enum EStreamLabsEventFor {

    STREAMLABS("streamlabs"),
    TWITCH_ACCOUNT("twitch_account"),
    YOUTUBE_ACCOUNT("youtube_account"),
    MIXER_ACCOUNT("mixer_account");

    private final String stringValue;

    @Nullable
    public static EStreamLabsEventFor from(final String value) {
        for (EStreamLabsEventFor eventFor : EStreamLabsEventFor.values()) {
            if (eventFor.getStringValue().equals(value)) return eventFor;
        }
        return null;
    }


    public static class EStreamLabsEventForDeserializer implements JsonDeserializer<EStreamLabsEventFor> {
        @Override
        public EStreamLabsEventFor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            return EStreamLabsEventFor.from(json.getAsString());
        }
    }
}
