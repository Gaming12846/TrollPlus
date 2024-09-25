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
import de.gaming12846.trollplus.constants.ConfigConstants;
import de.gaming12846.trollplus.constants.LangConstants;
import de.gaming12846.trollplus.listener.*;
import de.gaming12846.trollplus.utils.ConfigHelper;
import de.gaming12846.trollplus.utils.TabCompleter;
import de.gaming12846.trollplus.utils.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.logging.Level;

// The main class for the TrollPlus plugin
public class TrollPlus extends JavaPlugin {
    private double serverVersion;
    public boolean updateAvailable = false;

    // ConfigHelper instances
    private ConfigHelper configHelper;
    private ConfigHelper configHelperBlocklist;
    private ConfigHelper configHelperLangCustom;
    private ConfigHelper configHelperLangGerman;
    private ConfigHelper configHelperLangEnglish;
    private ConfigHelper configHelperLangSpanish;
    private ConfigHelper configHelperLangFrench;
    private ConfigHelper configHelperLangDutch;
    private ConfigHelper configHelperLangSimplifiedChinese;
    private ConfigHelper configHelperLangTraditionalChinese;

    // Command and Listener instances
    private TrollBowsCommand trollBowsCommand;
    private TrollCommand trollCommand;
    private TrollPlusCommand trollPlusCommand;
    private InventoryClickListener inventoryClickListener;

    // Called when the plugin is first enabled
    @Override
    public void onEnable() {
        loadConfigs();
        // Set log level from config
        getLogger().setLevel(Level.parse(getConfigHelper().getString(ConfigConstants.LOG_LEVEL)));
        checkServerVersion();
        registerListeners(getServer().getPluginManager());
        registerCommands();
        registerTabCompleters(new TabCompleter());
        initializeMetrics();
        try {
            checkForUpdates();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    // Loads the plugin's configuration files and checks their versions
    private void loadConfigs() {
        configHelper = new ConfigHelper(this, "config.yml");
        configHelperBlocklist = new ConfigHelper(this, "blocklist.yml");
        configHelperLangCustom = new ConfigHelper(this, "languages/lang_custom.yml");
        configHelperLangGerman = new ConfigHelper(this, "languages/lang_de.yml");
        configHelperLangEnglish = new ConfigHelper(this, "languages/lang_en.yml");
        configHelperLangSpanish = new ConfigHelper(this, "languages/lang_es.yml");
        configHelperLangFrench = new ConfigHelper(this, "languages/lang_fr.yml");
        configHelperLangDutch = new ConfigHelper(this, "languages/lang_nl.yml");
        configHelperLangSimplifiedChinese = new ConfigHelper(this, "languages/lang_zh-cn.yml");
        configHelperLangTraditionalChinese = new ConfigHelper(this, "languages/lang_zh-tw.yml");

        // Check config versions
        String version = getDescription().getVersion();
        if (!version.equalsIgnoreCase(getConfigHelper().getString(ConfigConstants.CONFIG_VERSION)))
            getLogger().warning(getConfigHelperLanguage().getString(LangConstants.CONFIG_OUTDATED));

        if (!version.equalsIgnoreCase(getConfigHelperLanguage().getString(LangConstants.LANGUAGE_CONFIG_VERSION)))
            getLogger().warning(getConfigHelperLanguage().getString(LangConstants.LANGUAGE_CONFIG_OUTDATED));
    }

    // Retrieves the plugin configuration
    public ConfigHelper getConfigHelper() {
        return configHelper;
    }

    // Retrieves the blocklist configuration
    public ConfigHelper getConfigHelperBlocklist() {
        return configHelperBlocklist;
    }

    // Retrieves the appropriate language configuration based on the plugin's config setting
    public ConfigHelper getConfigHelperLanguage() {
        String language = getConfigHelper().getString(ConfigConstants.LANGUAGE);

        switch (language.toLowerCase()) {
            case "custom" -> {
                return configHelperLangCustom;
            }
            case "de" -> {
                return configHelperLangGerman;
            }
            case "es" -> {
                return configHelperLangSpanish;
            }
            case "fr" -> {
                return configHelperLangFrench;
            }
            case "nl" -> {
                return configHelperLangDutch;
            }
            case "zh-cn" -> {
                return configHelperLangSimplifiedChinese;
            }
            case "zh-tw" -> {
                return configHelperLangTraditionalChinese;
            }
            default -> {
                return configHelperLangEnglish;
            }
        }
    }

    // Checks the server version for incompatibility
    private void checkServerVersion() {
        String bukkitVersion = getServer().getBukkitVersion().split("-")[0].split("\\.")[0] + "." + getServer().getBukkitVersion().split("-")[0].split("\\.")[1];
        serverVersion = Double.parseDouble(bukkitVersion);

        if (serverVersion < 1.20)
            getLogger().info(getConfigHelperLanguage().getString(LangConstants.SERVER_VERSION_ONLY_PARTLY_SUPPORTED));
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
        if (getConfig().getBoolean(ConfigConstants.METRICS_ENABLED, true)) {
            getLogger().info(getConfigHelperLanguage().getString(LangConstants.METRICS_ENABLED));
            new Metrics(this, 11761);
        }
    }

    // Checks for updates to the plugin and logs the result
    private void checkForUpdates() throws URISyntaxException, MalformedURLException {
        if (getConfig().getBoolean(ConfigConstants.CHECK_FOR_UPDATES, true)) {
            getLogger().info(getConfigHelperLanguage().getString(LangConstants.CHECKING_FOR_UPDATES));
            String updateChecker = new UpdateChecker(this, new URI("https://api.github.com/repos/Gaming12846/TrollPlus/releases/latest").toURL()).checkForUpdates();
            getLogger().info(updateChecker);
            if (updateChecker.equals(LangConstants.UPDATE_AVAILABLE)) updateAvailable = true;
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