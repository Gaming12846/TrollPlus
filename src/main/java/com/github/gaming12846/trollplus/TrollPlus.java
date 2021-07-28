package com.github.gaming12846.trollplus;

import com.github.gaming12846.trollplus.commands.TrollPlusCommand;
import com.github.gaming12846.trollplus.listener.*;
import com.github.gaming12846.trollplus.metrics.BStats;
import com.github.gaming12846.trollplus.utils.ConfigWrapper;
import com.github.gaming12846.trollplus.utils.UpdateChecker;
import com.github.gaming12846.trollplus.utils.VMConstants;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * TrollPlus main.java.com.github.gaming12846.trollplus TrollPlus.java
 *
 * @author Gaming12846
 */
public final class TrollPlus extends JavaPlugin {

    private ConfigWrapper blocklistConfig;

    @Override
    public void onEnable() {
        // Configs
        this.saveDefaultConfig();

        this.blocklistConfig = new ConfigWrapper(this, "blocklist.yml");

        // Register events
        this.getLogger().info("Registering events");
        registerEvents();

        // Register commands
        this.getLogger().info("Registering commands");
        registerCommands();

        // Metrics
        if (getConfig().getBoolean(VMConstants.CONFIG_METRICS_ENABLED, true)) {
            this.getLogger().info("Enabling plugin metrics");

            BStats metrics = new BStats(this, 11761);
        }

        // Update checker
        if (getConfig().getBoolean(VMConstants.CONFIG_CHECK_FOR_UPDATES, true)) {
            this.getLogger().info("Checking for updates ...");

            new UpdateChecker(this, 81193).getVersion(version -> {

                if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                    this.getLogger().info("There is no new update available.");
                } else {
                    this.getLogger().info("A new update is available! To download it visit SpigotMC: https://www.spigotmc.org/resources/81193/");
                }

            });
        }
    }

    // Register events
    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new AsyncPlayerChatListener(this), this);
        pluginManager.registerEvents(new EntityDamageByEntityListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(this), this);
        pluginManager.registerEvents(new EntityExplodeListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(this), this);
        pluginManager.registerEvents(new PlayerDropItemListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new PlayerPickupItemListener(), this);
    }

    // Register commands
    private void registerCommands() {
        this.getCommand("trollplus").setExecutor(new TrollPlusCommand(this));
    }

    // ConfigWrapper blocklist config
    public ConfigWrapper getBlocklistConfig() {
        return blocklistConfig;
    }

}