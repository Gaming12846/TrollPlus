/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 *
 * TrollPlus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TrollPlus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.gaming12846.trollplus;

import de.gaming12846.trollplus.commands.TrollBowsCommand;
import de.gaming12846.trollplus.commands.TrollCommand;
import de.gaming12846.trollplus.commands.TrollPlusCommand;
import de.gaming12846.trollplus.listener.*;
import de.gaming12846.trollplus.utils.ConfigUtil;
import de.gaming12846.trollplus.utils.TabCompleter;
import de.gaming12846.trollplus.utils.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

// The main class for the TrollPlus plugin
public class TrollPlus extends JavaPlugin {
    public final String configVersion = "1.4.8";
    public final String languageConfigVersion = "1.4.8";
    public ConfigUtil langCustomConfig;
    public ConfigUtil langGermanConfig;
    public ConfigUtil langEnglishConfig;
    public ConfigUtil langSpanishConfig;
    public ConfigUtil langFrenchConfig;
    public ConfigUtil langDutchConfig;
    public ConfigUtil langSimplifiedChineseConfig;
    public ConfigUtil langTraditionalChineseConfig;
    public String updateChecker;
    private double serverVersion;
    // ConfigUtil instances for various configuration files
    private ConfigUtil blocklistConfig;
    // Command and Listener instances
    private TrollBowsCommand trollBowsCommand;
    private TrollCommand trollCommand;
    private TrollPlusCommand trollPlusCommand;
    private InventoryClickListener inventoryClickListener;

    // Called when the plugin is first enabled
    @Override
    public void onEnable() {
        loadConfigs();
        checkServerVersion();
        registerListeners(getServer().getPluginManager());
        registerCommands();
        registerTabCompleters(new TabCompleter());
        initializeMetrics();
        checkForUpdates();
    }

    // Loads the plugin's configuration files and checks their versions
    private void loadConfigs() {
        this.saveDefaultConfig();

        blocklistConfig = new ConfigUtil(this, "blocklist.yml");
        langCustomConfig = new ConfigUtil(this, "languages/lang_custom.yml");
        langGermanConfig = new ConfigUtil(this, "languages/lang_de.yml");
        langEnglishConfig = new ConfigUtil(this, "languages/lang_en.yml");
        langSpanishConfig = new ConfigUtil(this, "languages/lang_es.yml");
        langFrenchConfig = new ConfigUtil(this, "languages/lang_fr.yml");
        langDutchConfig = new ConfigUtil(this, "languages/lang_nl.yml");
        langSimplifiedChineseConfig = new ConfigUtil(this, "languages/lang_zh-cn.yml");
        langTraditionalChineseConfig = new ConfigUtil(this, "languages/lang_zh-tw.yml");

        // Check config versions
        if (!configVersion.equalsIgnoreCase(getConfig().getString("version")))
            getLogger().warning(getLanguageConfig().getConfig().getString("config-outdated"));

        if (!languageConfigVersion.equalsIgnoreCase(getLanguageConfig().getConfig().getString("version")))
            getLogger().warning(getLanguageConfig().getConfig().getString("language-config-outdated"));
    }

    // Retrieves the blocklist configuration
    public ConfigUtil getBlocklistConfig() {
        return blocklistConfig;
    }

    // Retrieves the appropriate language configuration based on the plugin's config setting
    public ConfigUtil getLanguageConfig() {
        String language = getConfig().getString("language");
        if (language == null) language = "en";

        switch (language.toLowerCase()) {
            case "custom":
                return langCustomConfig;
            case "de":
                return langGermanConfig;
            case "es":
                return langSpanishConfig;
            case "fr":
                return langFrenchConfig;
            case "nl":
                return langDutchConfig;
            case "zh-cn":
                return langSimplifiedChineseConfig;
            case "zh-tw":
                return langTraditionalChineseConfig;
            default:
                return langEnglishConfig;
        }
    }

    // Checks the server version for incompatibility
    private void checkServerVersion() {
        String bukkitVersion = getServer().getBukkitVersion().split("-")[0].split("\\.")[0] + "." + getServer().getBukkitVersion().split("-")[0].split("\\.")[1];
        serverVersion = Double.parseDouble(bukkitVersion);

        if (serverVersion < 1.13) {
            getLogger().warning(getLanguageConfig().getString("server-version.unsupported"));
            if (getConfig().getBoolean("load-despite-unsupported-version", false))
                getServer().getPluginManager().disablePlugin(this);
        } else if (serverVersion < 1.20)
            getLogger().info(getLanguageConfig().getString("server-version.partly-supported"));
    }

    // Retrieves the server version
    public double getServerVersion() {
        return serverVersion;
    }

    // Registers events with the Bukkit plugin manager
    private void registerListeners(PluginManager pluginManager) {
        inventoryClickListener = new InventoryClickListener(this);

        pluginManager.registerEvents(new AsyncPlayerChatListener(this), this);
        pluginManager.registerEvents(new BlockIgniteListener(this), this);
        pluginManager.registerEvents(new EntityDamageByEntityListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(this), this);
        pluginManager.registerEvents(new EntityExplodeListener(this), this);
        pluginManager.registerEvents(new EntityPickupItemEvent(), this);
        pluginManager.registerEvents(inventoryClickListener, this);
        pluginManager.registerEvents(new PlayerDeathListener(this), this);
        pluginManager.registerEvents(new PlayerDropItemListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerItemHeldListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        pluginManager.registerEvents(new ProjectileHitListener(this), this);
        pluginManager.registerEvents(new ProjectileLaunchListener(this), this);
    }

    // Registers commands with their respective executors
    private void registerCommands() {
        trollBowsCommand = new TrollBowsCommand(this);
        trollCommand = new TrollCommand(this);
        trollPlusCommand = new TrollPlusCommand(this);

        Objects.requireNonNull(getCommand("trollbows")).setExecutor(trollBowsCommand);
        Objects.requireNonNull(getCommand("troll")).setExecutor(trollCommand);
        Objects.requireNonNull(getCommand("trollplus")).setExecutor(trollPlusCommand);
    }

    // Registers tab completers for the plugin's commands
    private void registerTabCompleters(TabCompleter tabCompleter) {
        Objects.requireNonNull(getCommand("trollplus")).setTabCompleter(tabCompleter);
        Objects.requireNonNull(getCommand("troll")).setTabCompleter(tabCompleter);
        Objects.requireNonNull(getCommand("trollbows")).setTabCompleter(tabCompleter);
    }

    // Initializes the bStats metrics for the plugin
    private void initializeMetrics() {
        if (getConfig().getBoolean("metrics-enabled", true)) {
            getLogger().info(getLanguageConfig().getString("metrics-enabled"));
            new Metrics(this, 11761);
        }
    }

    // Checks for updates to the plugin and logs the result
    private void checkForUpdates() {
        if (getConfig().getBoolean("check-for-updates", true)) {
            getLogger().info(getLanguageConfig().getString("checking-updates"));
            updateChecker = new UpdateChecker(this).checkForUpdates();
        }
    }

    // Retrieves the TrollBowsCommand instance
    public TrollBowsCommand getTrollBowsCommand() {
        return trollBowsCommand;
    }

    // Retrieves the TrollCommand instance
    public TrollCommand getTrollCommand() {
        return trollCommand;
    }

    // Retrieves the TrollPlusCommand instance
    public TrollPlusCommand getTrollPlusCommand() {
        return trollPlusCommand;
    }

    // Retrieves the InventoryClickListener instance
    public InventoryClickListener getInventoryClickListener() {
        return inventoryClickListener;
    }
}