package fr.gamecreep.fundraiserfusion;

import fr.gamecreep.fundraiserfusion.commands.TestCommand;
import fr.gamecreep.fundraiserfusion.donations.DonorCache;
import fr.gamecreep.fundraiserfusion.donations.entities.Donation;
import fr.gamecreep.fundraiserfusion.donations.DonationCache;
import fr.gamecreep.fundraiserfusion.donations.DonationsFetcher;
import fr.gamecreep.fundraiserfusion.donations.scoreboard.ScoreBoardUtils;
import fr.gamecreep.fundraiserfusion.events.PlayerJoinLeave;
import fr.gamecreep.fundraiserfusion.websocket.StreamlabsSocketTokenLoader;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Getter
public final class FundraiserFusion extends JavaPlugin {
    private final StreamlabsSocketTokenLoader socketTokenLoader = new StreamlabsSocketTokenLoader(this);
    private DonationCache donationCache;
    private DonorCache donorCache;
    private ScoreBoardUtils scoreBoardUtils;

    @Override
    public void onEnable() {
        loadCommands();
        loadEvents();
    }

    private void loadCommands() {
        //TODO: fix this
        getCommand("test").setExecutor(new TestCommand(this));
    }
    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinLeave(this), this);
    }

    //TODO: figure this out
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
