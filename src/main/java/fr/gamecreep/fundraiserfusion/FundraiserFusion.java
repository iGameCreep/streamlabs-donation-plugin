package fr.gamecreep.fundraiserfusion;

import fr.gamecreep.fundraiserfusion.commands.TestCommand;
import fr.gamecreep.fundraiserfusion.donations.entities.Donation;
import fr.gamecreep.fundraiserfusion.donations.entities.Donor;
import fr.gamecreep.fundraiserfusion.utils.ScoreBoardUtils;
import fr.gamecreep.fundraiserfusion.events.PlayerJoinLeave;
import fr.gamecreep.fundraiserfusion.websocket.StreamlabsSocketTokenLoader;
import fr.gamecreep.fundraiserfusion.websocket.StreamlabsWebSocketClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j(topic = "FundraiserFusion")
public final class FundraiserFusion extends JavaPlugin {

    @Getter
    private ScoreBoardUtils scoreBoardUtils;
    private final List<Donation> totalDonations = new ArrayList<>();
    private final Map<String, Double> donorCache = new HashMap<>();
    private StreamlabsSocketTokenLoader websocketLoader;
    private StreamlabsWebSocketClient webSocketClient;

    @Override
    public void onEnable() {
        this.loadCommands();
        this.loadEvents();
        this.loadStreamlabs();
        this.loadScoreboard();
    }

    @Override
    public void onDisable() {
        if (this.webSocketClient != null) {
            this.webSocketClient.endWebSocket();
        }
        log.info("Successfully stopped websocket and plugin !");
    }

    private void loadCommands() {
        Objects.requireNonNull(getCommand("test")).setExecutor(new TestCommand(this));
    }

    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinLeave(this), this);
    }

    private void loadStreamlabs() {
        this.websocketLoader = new StreamlabsSocketTokenLoader(this);
        this.webSocketClient = this.websocketLoader.loadSocket();

        this.scoreBoardUtils = new ScoreBoardUtils(this);
    }

    private void loadScoreboard() {
        final List<Donation> donationList = this.websocketLoader.fetchDonations();

        totalDonations.clear();
        totalDonations.addAll(donationList);
    }

    public void addDonation(final Donation donation) {
        final String donorName = donation.getDonorName();
        final double donationAmount = donation.getDonationAmount();

        this.donorCache.put(donorName, this.donorCache.getOrDefault(donorName, 0.0) + donationAmount);

        this.totalDonations.add(donation);
        this.scoreBoardUtils.updateScoreboard();
    }

    public List<Donor> getTopDonors(int count) {
        return this.donorCache.entrySet().stream()
                .map(entry -> new Donor(entry.getKey(), entry.getValue()))
                .sorted(Collections.reverseOrder())
                .limit(count)
                .collect(Collectors.toList());
    }
}
