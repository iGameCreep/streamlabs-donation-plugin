package fr.gamecreep.streamtrigger.api;

import com.google.gson.Gson;
import fr.gamecreep.streamtrigger.Constants;
import fr.gamecreep.streamtrigger.api.core.HttpApiClient;
import fr.gamecreep.streamtrigger.api.entities.ConfigToGenerate;
import fr.gamecreep.streamtrigger.api.entities.GeneratedConfig;
import fr.gamecreep.streamtrigger.api.entities.Version;
import fr.gamecreep.streamtrigger.exceptions.ApiException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class API {
    private final Gson gson = new Gson();

    public CompletableFuture<Version> getLatestVersion() {
        String path = getFullUrl("/version");
        return HttpApiClient.get(path, Collections.emptyMap())
                .thenApply(stringHttpResponse -> {
                    if (stringHttpResponse.statusCode() != 200) {
                        throw new ApiException("API Request failed: " + stringHttpResponse.body());
                    }

                    return this.gson.fromJson(stringHttpResponse.body(), Version.class);
                }).exceptionally(ex -> {
                    throw new ApiException("Unable to fetch version", ex);
                });
    }

    public CompletableFuture<GeneratedConfig> getConfigFromId(String configId) {
        String path = getFullUrl("/events/" + configId);
        return HttpApiClient.get(path, Collections.emptyMap())
                .thenApply(stringHttpResponse -> {
                    if (stringHttpResponse.statusCode() != 200) {
                        throw new ApiException("API Request failed: " + stringHttpResponse.body());
                    }

                    return this.gson.fromJson(stringHttpResponse.body(), GeneratedConfig.class);
                }).exceptionally(ex -> {
                    throw new ApiException("Unable to fetch version", ex);
                });
    }

    public CompletableFuture<GeneratedConfig> generatedConfig(String b64Config) {
        String path = getFullUrl("/events/generate");
        ConfigToGenerate data = new ConfigToGenerate(b64Config);
        return HttpApiClient.postJson(path, this.gson.toJson(data), Collections.emptyMap())
                .thenApply(stringHttpResponse -> {
                    if (stringHttpResponse.statusCode() != 200) {
                        throw new ApiException("API Request failed: " + stringHttpResponse.body());
                    }

                    return this.gson.fromJson(stringHttpResponse.body(), GeneratedConfig.class);
                }).exceptionally(ex -> {
                    throw new ApiException("Unable to generate a config", ex);
                });
    }

    private static String getFullUrl(String path) {
        return Constants.API_URL + path;
    }
}
