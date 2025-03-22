package fr.gamecreep.fundraiserfusion.utils;

import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import fr.gamecreep.fundraiserfusion.donations.entities.Donor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;

public class ScoreBoardUtils {
    private final Object scoreboardLock = new Object(); // Lock object for synchronization

    private final FundraiserFusion plugin;

    public ScoreBoardUtils(final FundraiserFusion plugin) {
        this.plugin = plugin;
    }

    public void createScoreboard(final Player player) {
        final List<Donor> topDonors = this.plugin.getTopDonors(5);
        final ScoreboardManager manager = this.plugin.getServer().getScoreboardManager();
        if (manager == null) return;

        final Scoreboard board = manager.getNewScoreboard();

        final Objective objective = board.registerNewObjective("donortop", Criteria.create("donortop"), "Top 5 Donateurs");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score;
        for (final Donor donor : topDonors) {
            score = objective.getScore(donor.getDonorName());
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
