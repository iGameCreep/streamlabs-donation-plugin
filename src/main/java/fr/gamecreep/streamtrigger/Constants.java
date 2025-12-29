package fr.gamecreep.streamtrigger;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String WEBSOCKET_ENDPOINT = "wss://sockets.streamlabs.com?token=";
    public static final String WEBSITE_URL = "https://streamtrigger.netlify.app";
}
