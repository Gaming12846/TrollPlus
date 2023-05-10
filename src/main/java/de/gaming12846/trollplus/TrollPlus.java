/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
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
import de.gaming12846.trollplus.metrics.Metrics;
import de.gaming12846.trollplus.utils.ConfigUtil;
import de.gaming12846.trollplus.utils.Constants;
import de.gaming12846.trollplus.utils.TabCompleter;
import de.gaming12846.trollplus.utils.UpdateChecker;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public class TrollPlus extends JavaPlugin {
    public static final Logger BUKKIT_LOGGER = Logger.getLogger("TrollPlus");
    public boolean updateAvailable = false;

    // Create ConfigUtils
    private ConfigUtil blocklistConfig;
    private ConfigUtil langCustomConfig;
    private ConfigUtil langEnglishConfig;

    private InventoryClickListener inventoryClickListener;
    private TrollCommand trollCommand;
    private TrollBowsCommand trollBowsCommand;

    @Override
    public void onEnable() {
        // Load configs
        loadConfigs();

        // Register listeners
        registerListener(getServer().getPluginManager());

        // Register commands
        registerCommands();

        // Register tabcompleter
        registerTabCompleter();

        // Check for updates
        checkUpdate();

        // Metrics bStats
        if (getConfig().getBoolean("metrics-enabled", true)) {
            BUKKIT_LOGGER.info(Constants.PLUGIN_CONSOLE_PREFIX + getLanguageConfig().getConfig().getString("metrics-enabled"));

            Metrics metrics = new Metrics(this, 11761);
        }
    }

    // Load configs
    private void loadConfigs() {
        this.saveDefaultConfig();

        blocklistConfig = new ConfigUtil(this, "blocklist.yml");
        langCustomConfig = new ConfigUtil(this, "lang_custom.yml");
        langEnglishConfig = new ConfigUtil(this, "lang_en.yml");

        Constants constants = new Constants(this);
    }

    public ConfigUtil getLanguageConfig() {
        if (Objects.equals(getConfig().getString("language"), "custom")) {
            return langCustomConfig;
        } else return langEnglishConfig;
    }

    public ConfigUtil getBlocklistConfig() {
        return blocklistConfig;
    }

    // Register listeners
    private void registerListener(PluginManager pm) {
        inventoryClickListener = new InventoryClickListener(this);

        pm.registerEvents(new AsyncPlayerChatListener(this), this);
        pm.registerEvents(new BlockIgniteListener(this), this);
        pm.registerEvents(new EntityDamageByEntityListener(), this);
        pm.registerEvents(new EntityDamageListener(this), this);
        pm.registerEvents(new EntityExplodeListener(this), this);
        pm.registerEvents(new EntityPickupItemEvent(), this);
        pm.registerEvents(inventoryClickListener, this);
        pm.registerEvents(new PlayerDeathListener(this), this);
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerItemHeldListener(), this);
        pm.registerEvents(new PlayerJoinListener(this), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerQuitListener(this), this);
        pm.registerEvents(new ProjectileHitListener(this), this);
        pm.registerEvents(new ProjectileLaunchListener(this), this);
    }

    // Register commands
    private void registerCommands() {
        trollCommand = new TrollCommand(this);
        trollBowsCommand = new TrollBowsCommand(this);

        Objects.requireNonNull(this.getCommand("trollplus")).setExecutor(new TrollPlusCommand(this));
        Objects.requireNonNull(this.getCommand("troll")).setExecutor(trollCommand);
        Objects.requireNonNull(this.getCommand("trollbows")).setExecutor(trollBowsCommand);
    }

    // Register tabcompleter
    private void registerTabCompleter() {
        Objects.requireNonNull(this.getCommand("trollplus")).setTabCompleter(new TabCompleter());
        Objects.requireNonNull(this.getCommand("troll")).setTabCompleter(new TabCompleter());
        Objects.requireNonNull(this.getCommand("trollbows")).setTabCompleter(new TabCompleter());
    }

    // Check for Update
    private void checkUpdate() {
        if (getConfig().getBoolean("check-for-updates", true)) {
            BUKKIT_LOGGER.info(Constants.PLUGIN_CONSOLE_PREFIX + getLanguageConfig().getConfig().getString("checking-updates"));

            new UpdateChecker(this, 81193).getVersion(version -> {
                if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                    BUKKIT_LOGGER.info(Constants.PLUGIN_CONSOLE_PREFIX + getLanguageConfig().getConfig().getString("no-update-available"));
                } else {
                    BUKKIT_LOGGER.info(Constants.PLUGIN_CONSOLE_PREFIX + getLanguageConfig().getConfig().getString("update-available") + " https://www.spigotmc.org/resources/81193");

                    updateAvailable = true;
                }
            });
        }
    }

    // Get InventoryClickListener
    public InventoryClickListener getInventoryClickListener() {
        return inventoryClickListener;
    }

    // Get TrollCommand
    public TrollCommand getTrollCommand() {
        return trollCommand;
    }

    // Get TrollBowsCommand
    public TrollBowsCommand getTrollBowsCommand() {
        return trollBowsCommand;
    }
}