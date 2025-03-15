package fr.gamecreep.streamlabsdonations.donations;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.gamecreep.streamlabsdonations.donations.entities.Donation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DonationsFetcher {
    private static final String DONATIONS_ENDPOINT = "https://streamlabs.com/api/v2.0/donations";
    private final String accessToken;

    public DonationsFetcher(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<Donation> fetchDonations() {
        List<Donation> donations = new ArrayList<>();

        try {
            final URL url = new URI(DONATIONS_ENDPOINT).toURL();
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + this.accessToken);
            connection.setRequestProperty("Accept", "application/json");

            final int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                final StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                final JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                final JsonArray donationArray = jsonResponse.getAsJsonArray("data");

                JsonObject donationData;
                Donation donation;
                for (int i = 0; i < donationArray.size(); i++) {
                    donationData = donationArray.get(i).getAsJsonObject();
                    donation = new Donation(
                            donationData.get("name").getAsString(),
                            donationData.get("amount").getAsDouble()
                    );
                    donations.add(donation);
                }
            } else {
                //TODO: Proper logging
                System.err.println("Failed to fetch donations: HTTP error code " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Could not retrieve donations");
            e.printStackTrace();
        }

        return donations;
    }
}
