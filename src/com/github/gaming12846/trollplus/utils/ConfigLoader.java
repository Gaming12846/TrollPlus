package com.github.gaming12846.trollplus.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.gaming12846.trollplus.TrollPlus;

/**
 * TrollPlus com.github.gaming12846.trollplus.utils ConfigLoader.java
 *
 * @author Gaming12846
 */
public class ConfigLoader {
	private static File blacklistFile = null;

	// Loading the configs
	public static void ConfigLoader() {
		// Loading the main config
		File Config = new File(TrollPlus.getPlugin().getDataFolder(), "config.yml");

		if (Config.exists()) {
			TrollPlus.logger.info(Vars.consolePrefix + "Config was loaded successfully");
			TrollPlus.getPlugin().reloadConfig();
		} else {
			TrollPlus.logger.info(Vars.consolePrefix + "Can't find a config, create a new one");
			TrollPlus.getPlugin().getConfig().options().copyDefaults(true);
			TrollPlus.getPlugin().saveConfig();
		}

		reloadBlacklist();
	}

	// Reload blacklist
	public static void reloadBlacklist() {
		if (blacklistFile == null)
			blacklistFile = new File(TrollPlus.getPlugin().getDataFolder(), "blacklist.yml");

		Vars.blacklist = YamlConfiguration.loadConfiguration(blacklistFile);
		InputStream defConfigStream = TrollPlus.getPlugin().getResource("blacklist.yml");

		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
			Vars.blacklist.setDefaults(defConfig);
		}
	}

	// Get blacklist
	public static FileConfiguration getBlacklist() {
		if (Vars.blacklist == null)
			reloadBlacklist();

		return Vars.blacklist;
	}

	// Save blacklist
	public static void saveBlacklist() {
		if (Vars.blacklist == null || blacklistFile == null)
			return;

		try {
			Vars.blacklist.save(blacklistFile);
		} catch (IOException ex) {
			TrollPlus.logger.warning(Vars.consolePrefix + "Failed to save to the blacklist");
		}
	}
}
