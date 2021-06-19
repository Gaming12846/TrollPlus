/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.utilitys;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.gaming12846.troll.main.Main;

public class ConfigLoader {

	private static File blacklistFile = null;

	// Loading the configs
	public static void ConfigLoader() {
		// Loading the main config
		File Config = new File(Main.getPlugin().getDataFolder(), "config.yml");

		if (Config.exists()) {
			Bukkit.getConsoleSender().sendMessage("[Troll] Config was loaded successfully");
			Main.getPlugin().reloadConfig();
		} else {
			Bukkit.getConsoleSender().sendMessage("[Troll] Can't find a config, create a new one");
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
			// Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE,"Konfiguration
			// konnte nicht nach " + blacklistFile + " geschrieben werden.", ex);
		}
	}
}
