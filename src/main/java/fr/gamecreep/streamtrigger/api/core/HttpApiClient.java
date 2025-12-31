package fr.gamecreep.streamtrigger.api.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpApiClient {
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(CONNECT_TIMEOUT)
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static CompletableFuture<HttpResponse<String>> get(
            String url,
            Map<String, String> headers
    ) {
        HttpRequest.Builder builder = baseRequest(url).GET();
        applyHeaders(builder, headers);

        return CLIENT.sendAsync(
                builder.build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    public static CompletableFuture<HttpResponse<String>> postJson(
            String url,
            String jsonBody,
            Map<String, String> headers
    ) {
        HttpRequest.Builder builder = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json");

        applyHeaders(builder, headers);

        return CLIENT.sendAsync(
                builder.build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    public static CompletableFuture<HttpResponse<String>> putJson(
            String url,
            String jsonBody,
            Map<String, String> headers
    ) {
        HttpRequest.Builder builder = baseRequest(url)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json");

        applyHeaders(builder, headers);

        return CLIENT.sendAsync(
                builder.build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    public static CompletableFuture<HttpResponse<String>> delete(
            String url,
            Map<String, String> headers
    ) {
        HttpRequest.Builder builder = baseRequest(url).DELETE();
        applyHeaders(builder, headers);

        return CLIENT.sendAsync(
                builder.build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    private static HttpRequest.Builder baseRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(REQUEST_TIMEOUT);
    }

    private static void applyHeaders(
            HttpRequest.Builder builder,
            Map<String, String> headers
    ) {
        if (headers == null) return;

        headers.forEach(builder::header);
    }
}
