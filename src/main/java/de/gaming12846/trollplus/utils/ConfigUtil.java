/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import com.google.common.base.Charsets;
import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

// Utility class for managing configuration files
public class ConfigUtil {
    private final TrollPlus plugin;
    private final File file;
    private final String path;
    private FileConfiguration configuration;

    // Constructor for the ConfigUtil
    public ConfigUtil(TrollPlus plugin, String path) {
        this.plugin = plugin;
        this.path = path;
        this.file = new File(plugin.getDataFolder(), path);

        // Check if the configuration file exists
        if (!file.exists()) plugin.saveResource(path, false);

        // Load the configuration
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    // Saves the current configuration to the file
    public void save() {
        try {
            this.configuration.save(file);
        } catch (Exception e) {
            plugin.getLogger().warning(plugin.getLanguageConfig().getString("failed-to-safe-config") + " " + e.getMessage());
        }
    }

    // Retrieves the FileConfiguration
    public FileConfiguration getConfig() {
        return this.configuration;
    }

    // Reloads the configuration from the file
    public void reload() {
        // Ensure the default config is saved
        plugin.saveDefaultConfig();
        // Check if the configuration file exists
        if (!file.exists()) plugin.saveResource(path, false);

        this.configuration = YamlConfiguration.loadConfiguration(file);

        // Load default values from the resource
        InputStream defaultConfigStream = plugin.getResource(path);
        if (defaultConfigStream != null)
            this.configuration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream, Charsets.UTF_8)));
    }

    // Retrieves a string value from the configuration
    public String getString(String string) {
        return getConfig().getString(string);
    }

    // Retrieves a list of strings from the configuration
    public List<String> getStringList(String string) {
        return getConfig().getStringList(string);
    }
}