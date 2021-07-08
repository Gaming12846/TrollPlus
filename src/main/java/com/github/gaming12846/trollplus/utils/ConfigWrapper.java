package com.github.gaming12846.trollplus.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * TrollPlus com.github.gaming12846.trollplus.utils ConfigWrapper.java
 *
 * @author Gaming12846
 * @credits 2008Choco
 */
public final class ConfigWrapper {

    private final JavaPlugin plugin;
    private final String rawPath;

    private final File file;
    private FileConfiguration config;

    public ConfigWrapper(JavaPlugin plugin, File directory, String path) {
        this(plugin, directory.getPath().concat(path));
    }

    // ConfigWrapper
    public ConfigWrapper(JavaPlugin plugin, String path) {
        Preconditions.checkArgument(plugin != null, "Cannot provide null plugin");
        Preconditions.checkArgument(!StringUtils.isEmpty(path), "File path must not be null");

        this.plugin = plugin;
        this.rawPath = path;
        this.file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {
            plugin.saveResource(path, false);
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    // ConfigWrapper asRawConfig
    public FileConfiguration asRawConfig() {
        return config;
    }

    // ConfigWrapper saveExceptionally
    public void saveExceptionally() throws IOException {
        this.config.save(file);
    }

    // ConfigWrapper save
    public void save() {
        try {
            this.saveExceptionally();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ConfigWrapper reload
    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(file);

        // Loading defaults if necessary
        final InputStream defaultConfigStream = plugin.getResource(rawPath);
        if (defaultConfigStream == null) {
            return;
        }

        this.config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream, Charsets.UTF_8)));
    }

}