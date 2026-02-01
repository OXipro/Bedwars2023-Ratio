package com.oxipro.bedwars.ratio.support;

import com.oxipro.bedwars.ratio.papi.BW2023Placeholders;
import com.oxipro.bedwars.ratio.support.AddonClass.BW2023Addon;
import com.tomkeuper.bedwars.api.BedWars;
import me.leoo.utils.bukkit.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BW2023 {


    private final Plugin plugin;
    private final ConfigManager config;

    public BW2023(Plugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.config = configManager;
        start();
    }

    private void start() {
        BedWars bw2023Api = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        bw2023Api.getAddonsUtil().registerAddon(new BW2023Addon(plugin));
        new BW2023Placeholders(plugin, bw2023Api, config).register();
    }
}
