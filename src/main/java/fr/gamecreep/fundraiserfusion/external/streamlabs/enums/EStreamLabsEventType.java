package fr.gamecreep.fundraiserfusion.external.streamlabs.enums;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

@AllArgsConstructor
@Getter
public enum EStreamLabsEventType {

    DONATION("donation"),
    FOLLOW("follow"),
    SUBSCRIPTION("subscription"),
    HOST("host"),
    BITS("bits"),
    RAID("raid"),
    TWITCH_CHARITY_DONATION("twitchcharitydonation"),
    SUPERCHAT("superchat"),
    MEMBERSHIP_GIFT("membershipGift");

    private final String stringValue;

    @Nullable
    public static EStreamLabsEventType from(final String value) {
        for (EStreamLabsEventType eventType : EStreamLabsEventType.values()) {
            if (eventType.getStringValue().equals(value)) return eventType;
        }
        return null;
    }

    public static class EStreamLabsEventTypeDeserializer implements JsonDeserializer<EStreamLabsEventType> {
        @Override
        public EStreamLabsEventType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            return EStreamLabsEventType.from(json.getAsString());
        }
    }
}
