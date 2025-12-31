package fr.gamecreep.streamtrigger.utils;

import java.util.Base64;

public class EncodingUtils {
    public String encodeBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public String decodeBase64(String input) {
        return new String(Base64.getDecoder().decode(input));
    }
}
