package com.oxipro.bedwars.ratio.papi;

import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.stats.IStatsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.leoo.utils.bukkit.config.ConfigManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class BW2023Placeholders extends PlaceholderExpansion {
    private final Plugin plugin;
    private final BedWars bedWars;
    private final ConfigManager config;

    public BW2023Placeholders(Plugin plugin, BedWars bedWars, ConfigManager config) {
        this.plugin = plugin;
        this.bedWars = bedWars;
        this.config = config;
    }

    @Override
    @NotNull
    public String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "bw2023stats";
    }

    @Override
    @NotNull
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        DecimalFormat deltaFormat = new DecimalFormat("+#####.###;-#####.###");
        DecimalFormat decimalFormat = new DecimalFormat("#####.###");
        IStatsManager statsManager = bedWars.getStatsManager();


        int totalKills = statsManager.get(player.getUniqueId()).getKills();
        int totalDeaths = statsManager.get(player.getUniqueId()).getDeaths();

        int totalFinalDeaths = statsManager.get(player.getUniqueId()).getFinalDeaths();
        int totalFinalKills = statsManager.get(player.getUniqueId()).getFinalKills();

        int totalLose = statsManager.get(player.getUniqueId()).getLosses();
        int totalWins = statsManager.get(player.getUniqueId()).getWins();

        switch (params) {
            case "kdr":
                if (totalDeaths != 0) {
                    return decimalFormat.format(totalKills / totalDeaths);
                }
                break;
            case "fkdr":
                if (totalFinalDeaths != 0) {
                    return decimalFormat.format(totalFinalKills / totalFinalDeaths);
                }
                break;
            case "wlr":
                if (totalLose != 0) {
                    return decimalFormat.format(totalWins / totalLose);
                }
                break;
        }

        if (player instanceof Player) {
            Player OnlinePlayer = (Player) player;
            IArena arena = bedWars.getArenaUtil().getArenaByPlayer(OnlinePlayer);

            if (arena != null) {

                int arenaKills = arena.getPlayerKills(OnlinePlayer, false);
                int arenaDeaths = arena.getPlayerDeaths(OnlinePlayer, false);
                int arenaFinalKills = arena.getPlayerKills(OnlinePlayer, true);
                int arenaFinalDeaths = arena.getPlayerDeaths(OnlinePlayer, true);


                double kdBefore = totalDeaths == 0 ? totalKills : (double) totalKills / totalDeaths;
                double kdAfter = (double) (totalKills + arenaKills) / Math.max(1, totalDeaths + arenaDeaths);
                double adeltaKD = kdAfter - kdBefore;
                String akdrdiff = deltaFormat.format(kdAfter - kdBefore);

                double fkdBefore = totalFinalDeaths == 0 ? totalKills : (double) totalKills / totalFinalDeaths;
                double fkdAfter = (double) (totalFinalKills + arenaFinalKills) / Math.max(1, totalFinalDeaths + arenaFinalDeaths);
                double afdeltaKD = fkdAfter - fkdBefore;
                String afkdrdiff = deltaFormat.format(afdeltaKD);


                switch (params) {
                    case "akdrdiff":
                        return akdrdiff;
                    case "afkdrdiff":
                        return afkdrdiff;
                    case "akdrdiffcolor":
                        if (adeltaKD >= 0) {
                            return config.getString("ratio.placeholders.diff-positive-colorcode") + akdrdiff;
                        } else {
                            return config.getString("ratio.placeholders.diff-negative-colorcode") + akdrdiff;
                        }
                    case "afkdrdiffcolor":
                        if (afdeltaKD >= 0) {
                            return config.getString("ratio.placeholders.diff-positive-colorcode") + afkdrdiff;
                        } else {
                            return config.getString("ratio.placeholders.diff-negative-colorcode") + afkdrdiff;
                        }
                    case "akdr":
                        if (arenaDeaths != 0) {
                            return decimalFormat.format(arenaKills / arenaDeaths);
                        }
                        break;
                    case "afkdr":
                        if (arenaFinalDeaths != 0) {
                            return decimalFormat.format(arenaFinalKills / arenaFinalDeaths);
                        }
                        break;
                }
            }
        }
        return config.getString("ratio.placeholders.null-text");
    }


}
