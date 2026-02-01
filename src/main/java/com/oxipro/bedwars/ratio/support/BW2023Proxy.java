package com.oxipro.bedwars.ratio.support;

import com.oxipro.bedwars.ratio.papi.BW2023ProxyPlaceholders;
import com.oxipro.bedwars.ratio.support.AddonClass.BW2023ProxyAddon;
import com.tomkeuper.bedwars.proxy.BedWarsProxy;
import me.leoo.utils.bukkit.config.ConfigManager;
import org.bukkit.plugin.Plugin;


public class BW2023Proxy {
    private final Plugin plugin;
    private final ConfigManager config;

    public BW2023Proxy(Plugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.config = configManager;
        start();
    }

    private void start() {
        com.tomkeuper.bedwars.proxy.api.BedWars bwProxyAPI = BedWarsProxy.getAPI();
        bwProxyAPI.getAddonsUtil().registerAddon(new BW2023ProxyAddon(plugin));
        new BW2023ProxyPlaceholders(plugin, bwProxyAPI, config).register();
    }
}
