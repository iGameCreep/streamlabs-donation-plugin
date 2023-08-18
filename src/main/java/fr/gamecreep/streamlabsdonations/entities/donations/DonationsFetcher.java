package fr.gamecreep.streamlabsdonations.entities.donations;

import fr.gamecreep.streamlabsdonations.entities.donations.utils.Donation;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.*;

public class DonationsFetcher {
    private static final String DONATIONS_ENDPOINT = "https://streamlabs.com/api/v2.0/donations";
    private final String ACCESS_TOKEN;

    public DonationsFetcher(String accessToken) {
        this.ACCESS_TOKEN = accessToken;
    }

    public List<Donation> fetchDonations() {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(DONATIONS_ENDPOINT);

            httpGet.setHeader("Authorization", "Bearer " + this.ACCESS_TOKEN);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(responseBody);

                JSONArray donationArray = jsonResponse.getJSONArray("data");
                List<Donation> donations = new ArrayList<>();

                for (int i = 0; i < donationArray.length(); i++) {
                    JSONObject donationData = donationArray.getJSONObject(i);
                    Donation donation = new Donation(
                            donationData.getString("name"),
                            new BigDecimal(donationData.getString("amount"))
                    );
                    donations.add(donation);
                }

                return donations;
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("Could not retrieve donations");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
