package fr.gamecreep.fundraiserfusion.donations.scoreboard;

import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.donations.entities.Donor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;

public class ScoreBoardUtils {
    private final FundraiserFusion plugin;
    private final Object scoreboardLock = new Object(); // Lock object for synchronization

    public ScoreBoardUtils(FundraiserFusion plugin) {
        this.plugin = plugin;
    }

    public void createScoreboard(Player player) {
        List<Donor> topDonors = plugin.getDonorCache().getTopDonors(5);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        //TODO: fix this
        Objective objective = board.registerNewObjective("Top 5 Donateurs", "donortop");
        objective.setDisplayName("Top 5 Donateurs");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (Donor donor : topDonors) {
            Score score = objective.getScore(donor.getDonorName());
            score.setScore((int) donor.getDonationAmount());
        }

        synchronized (scoreboardLock) {
            player.setScoreboard(board);
        }

        player.setScoreboard(board);
    }

    public void updateScoreboard() {
        synchronized (scoreboardLock) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                createScoreboard(player);
            }
        }
    }
}
