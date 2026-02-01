package com.oxipro.bedwars.ratio.papi;

import com.tomkeuper.bedwars.proxy.BedWarsProxy;
import com.tomkeuper.bedwars.proxy.api.BedWars;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.leoo.utils.bukkit.config.ConfigManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.UUID;

public class BW2023ProxyPlaceholders extends PlaceholderExpansion {

    private final Plugin plugin;
    private final BedWars bedWars;
    private final ConfigManager config;

    public BW2023ProxyPlaceholders(Plugin plugin, BedWars bedWars, ConfigManager config) {
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
        DecimalFormat decimalFormat = new DecimalFormat("#####.##");

        UUID playerUUID = player.getUniqueId();

        if (params.equalsIgnoreCase("kdr")) {
            double kills;
            double deaths;
            kills = BedWarsProxy.getStatsCache().getPlayerKills(playerUUID);
            deaths = BedWarsProxy.getStatsCache().getPlayerDeaths(playerUUID);


            if (deaths != 0) {
                return decimalFormat.format(kills / deaths);
            }
        }

        if (params.equalsIgnoreCase("fkdr")) {
            double finalDeaths;
            double finalKills;


            finalKills = BedWarsProxy.getStatsCache().getPlayerFinalKills(playerUUID);
            finalDeaths = BedWarsProxy.getStatsCache().getPlayerFinalDeaths(playerUUID);

            if (finalDeaths != 0) {
                return decimalFormat.format(finalKills / finalDeaths);
            }
        }

        if (params.equalsIgnoreCase("wlr")) {
            double lose;
            double wins;
            lose = BedWarsProxy.getStatsCache().getPlayerLoses(playerUUID);
            wins = BedWarsProxy.getStatsCache().getPlayerWins(playerUUID);


            if (lose != 0) {
                return decimalFormat.format(wins / lose);
            }
        }
        return config.getString("ratio.placeholders.null-text");
    }
}
