package fr.gamecreep.streamlabsdonations.entities.donations.scoreboard;

import fr.gamecreep.streamlabsdonations.StreamLabsDonations;
import fr.gamecreep.streamlabsdonations.entities.donations.utils.Donor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;

public class ScoreBoardUtils {
    private final StreamLabsDonations plugin;

    public ScoreBoardUtils(StreamLabsDonations plugin) {
        this.plugin = plugin;
    }

    public void createScoreboard(Player player) {
        List<Donor> topDonors = plugin.getDonorCache().getTopDonors(5);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("Top 5 Donateurs", "donortop");
        objective.setDisplayName("Top 5 Donateurs");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (Donor donor : topDonors) {
            Score score = objective.getScore(donor.getDonorName());
            score.setScore(donor.getDonationAmount().intValue());
        }

        player.setScoreboard(board);
    }

    public void updateScoreboard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            createScoreboard(player);
        }
    }
}
