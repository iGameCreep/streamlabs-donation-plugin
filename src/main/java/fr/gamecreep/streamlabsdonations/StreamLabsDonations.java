package fr.gamecreep.streamlabsdonations;

import fr.gamecreep.streamlabsdonations.entities.commands.HelpCommand;
import fr.gamecreep.streamlabsdonations.entities.donations.DonorCache;
import fr.gamecreep.streamlabsdonations.entities.donations.utils.Donation;
import fr.gamecreep.streamlabsdonations.entities.donations.DonationCache;
import fr.gamecreep.streamlabsdonations.entities.donations.DonationsFetcher;
import fr.gamecreep.streamlabsdonations.entities.donations.scoreboard.ScoreBoardUtils;
import fr.gamecreep.streamlabsdonations.entities.events.PlayerJoinLeave;
import fr.gamecreep.streamlabsdonations.websocket.StreamlabsSocketTokenLoader;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

@Getter
public final class StreamLabsDonations extends JavaPlugin {
    private StreamlabsSocketTokenLoader socketTokenLoader;
    private DonationCache donationCache;
    private DonorCache donorCache;
    private ScoreBoardUtils scoreBoardUtils;

    @Override
    public void onEnable() {
        loadStreamlabs();
        loadCommands();
        loadEvents();
    }

    private void loadStreamlabs() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        String accessToken = config.getString("streamlabs.accessToken");
        this.socketTokenLoader = new StreamlabsSocketTokenLoader(accessToken, this);
        loadScoreboard(accessToken);
        getLogger().info("Streamlabs integration loaded !");
    }

    private void loadCommands() {
        Objects.requireNonNull(getCommand("test")).setExecutor(new HelpCommand());
    }
    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinLeave(this), this);
    }

    private void loadScoreboard(String accessToken) {
        this.scoreBoardUtils = new ScoreBoardUtils(this);
        this.donorCache = new DonorCache();
        this.donationCache = new DonationCache(this);

        DonationsFetcher donationsFetcher = new DonationsFetcher(accessToken);
        List<Donation> donationList = donationsFetcher.fetchDonations();

        this.donationCache.fetchAndUpdateTopDonations(donationList);
    }

    @Override
    public void onDisable() {
        getLogger().info("Successfully stopped websocket and plugin !");
    }
}
