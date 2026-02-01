package com.oxipro.bedwars.ratio;

import com.oxipro.bedwars.ratio.papi.BW2023Placeholders;
import com.oxipro.bedwars.ratio.papi.BW2023ProxyPlaceholders;
import com.oxipro.bedwars.ratio.support.AddonClass.BW2023Addon;
import com.oxipro.bedwars.ratio.support.AddonClass.BW2023ProxyAddon;
import com.oxipro.bedwars.ratio.support.BW2023;
import com.oxipro.bedwars.ratio.support.BW2023Proxy;
import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.proxy.BedWarsProxy;
import lombok.Getter;
import com.oxipro.bedwars.ratio.config.MainConfig;
import com.oxipro.bedwars.ratio.utils.BedwarsMode;
import me.leoo.utils.bukkit.Utils;
import me.leoo.utils.bukkit.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getPluginManager;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main plugin;

    private BedwarsMode bedwarsMode;
    private ConfigManager mainConfig;

    @Override
    public void onEnable() {
        plugin = this;

        Utils.initialize(this);

        for (BedwarsMode mode : BedwarsMode.values()) {
            if (getPluginManager().isPluginEnabled(mode.getName())) {
                bedwarsMode = mode;
                getLogger().info("Hooked into " + mode.getName());
            }
        }

        if (bedwarsMode == null) {
            getLogger().info("Bedwars2023/BWProxy2023 not found. Disabling...");

            getPluginManager().disablePlugin(this);

            return;
        }

        if (!getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getLogger().severe("PlaceholderAPI plugin was not found. Disabling...");
            getPluginManager().disablePlugin(this);
            return;
        }

        mainConfig = new MainConfig("config", bedwarsMode.getPath());


        if (bedwarsMode == BedwarsMode.BEDWARS) {
            new BW2023(this, mainConfig);
        }

        if (bedwarsMode == BedwarsMode.PROXY) {
            new BW2023Proxy(this, mainConfig);
        }

        getLogger().info(getDescription().getName() + " plugin by itz_leoo/OXipro has been successfully enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info(getDescription().getName() + " plugin by itz_leoo/OXipro has been successfully disabled.");
    }
}
