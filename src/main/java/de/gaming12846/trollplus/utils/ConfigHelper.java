/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

// Utility class for managing configuration files
public class ConfigHelper {
    private final TrollPlus plugin;
    private final String path;
    private FileConfiguration fileConfiguration;
    private File file;

    // Constructor for the ConfigHelper
    public ConfigHelper(TrollPlus plugin, String path) {
        this.plugin = plugin;
        this.path = path;
        saveDefaultConfig();
        loadConfig();
    }

    // Method to save the default config if it doesn't exist
    public void saveDefaultConfig() {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
        file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) plugin.saveResource(path, false);
    }

    // Method to load or reload the config file
    public void loadConfig() {
        file = new File(plugin.getDataFolder(), path);

        if (!file.exists()) saveDefaultConfig();

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    // Method to save the config back to the file
    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning(plugin.getConfigHelperLanguage().getString("failed-to-save-config") + " " + e);
        }
    }

    // Retrieves the FileConfiguration
    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

    // Retrieve a string from the config
    public String getString(String path) {
        // Get the value from the current language config
        String string = fileConfiguration.getString(path);

        // If the message is null or empty, replace it with the default value
        if (string == null || string.trim().isEmpty()) {
            // Load the default config from the plugin's jar
            FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), path));

            // Return the default message
            return defaultConfig.getString(path, "Message not found: " + path);
        }

        return string;
    }

    // Retrieve a boolean from the config
    public boolean getBoolean(String path) {
        return fileConfiguration.getBoolean(path);
    }

    // Retrieve an int from the config
    public int getInt(String path) {
        return fileConfiguration.getInt(path);
    }

    // Retrieve a long from the config
    public long getLong(String path) {
        return fileConfiguration.getLong(path);
    }

    // Retrieve a list of strings from the config
    public List<String> getStringList(String path) {
        // Get the list from the current language config
        List<String> list = fileConfiguration.getStringList(path);

        // If the list is empty, replace it with the default value
        if (list.isEmpty()) {
            // Load the default config from the plugin's jar
            FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), path));

            // Return the default list or a single-item list indicating the path wasn't found
            return defaultConfig.getStringList(path).isEmpty() ? Collections.singletonList("List not found: " + path) : defaultConfig.getStringList(path);
        }

        return list;
    }

    //
    public void set(String path, Object value) {
        fileConfiguration.set(path, value);
    }

    //
    public boolean contains(String path) {
        return fileConfiguration.contains(path);
    }
}