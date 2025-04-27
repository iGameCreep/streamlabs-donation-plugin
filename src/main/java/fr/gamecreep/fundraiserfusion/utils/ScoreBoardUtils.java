package fr.gamecreep.fundraiserfusion.utils;

import fr.gamecreep.fundraiserfusion.FundraiserFusion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreBoardUtils {
    private final Object scoreboardLock = new Object(); // Lock object for synchronization

    private final FundraiserFusion plugin;

    public ScoreBoardUtils(final FundraiserFusion plugin) {
        this.plugin = plugin;
    }

    public void createScoreboard(final Player player) {
        final ScoreboardManager manager = this.plugin.getServer().getScoreboardManager();
        if (manager == null) return;

        final Scoreboard board = manager.getNewScoreboard();

        final Objective objective = board.registerNewObjective("latestevents", Criteria.create("latestevents"), "5 Last Events");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

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
