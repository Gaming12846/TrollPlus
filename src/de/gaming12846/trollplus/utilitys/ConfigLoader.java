/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.utilitys;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.gaming12846.trollplus.main.Main;

public class ConfigLoader {

	private static File blacklistFile = null;

	// Loading the configs
	public static void ConfigLoader() {
		// Loading the main config
		File Config = new File(Main.getPlugin().getDataFolder(), "config.yml");

		if (Config.exists()) {
			Main.getPlugin().getLogger().info("Config was loaded successfully");
			Main.getPlugin().reloadConfig();
		} else {
			Main.getPlugin().getLogger().info("Can't find a config, create a new one");
			Main.getPlugin().getConfig().options().copyDefaults(true);
			Main.getPlugin().saveConfig();
		}
		reloadBlacklist();
	}

	// Reload Blacklist
	public static void reloadBlacklist() {
		if (blacklistFile == null) {
			blacklistFile = new File(Main.getPlugin().getDataFolder(), "blacklist.yml");
		}
		Vars.blacklist = YamlConfiguration.loadConfiguration(blacklistFile);
		InputStream defConfigStream = Main.getPlugin().getResource("blacklist.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
			Vars.blacklist.setDefaults(defConfig);
		}
	}

	// Get Blacklist
	public static FileConfiguration getBlacklist() {
		if (Vars.blacklist == null) {
			reloadBlacklist();
		}
		return Vars.blacklist;
	}

	// Save Blacklist
	public static void saveBlacklist() {
		if (Vars.blacklist == null || blacklistFile == null) {
			return;
		}
		try {
			Vars.blacklist.save(blacklistFile);
		} catch (IOException ex) {
			Main.getPlugin().getLogger().info("Failed to save to the blacklist");
		}
	}
}