/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import com.google.common.base.Charsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigUtil {
    private final JavaPlugin plugin;
    private final File file;
    private final String path;
    private FileConfiguration configuration;

    // Create a config
    public ConfigUtil(JavaPlugin plugin, String path) {
        this.plugin = plugin;
        this.path = path;
        this.file = new File(plugin.getDataFolder(), path);

        if (!file.exists()) {
            plugin.saveResource(path, false);
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    // Save the config
    public void save() {
        try {
            this.configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get the config
    public FileConfiguration getConfig() {
        return this.configuration;
    }

    // Reload the config
    public void reload() {
        plugin.saveDefaultConfig();
        if (!file.exists()) {
            plugin.saveResource(path, false);
        }

        this.configuration = YamlConfiguration.loadConfiguration(file);

        final InputStream defaultConfigStream = plugin.getResource(path);
        if (defaultConfigStream == null) {
            return;
        }

        this.configuration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream, Charsets.UTF_8)));
    }
}