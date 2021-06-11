/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.gaming12846.troll.commands.TrollCommand;
import de.gaming12846.troll.features.Control;
import de.gaming12846.troll.features.Freeze;
import de.gaming12846.troll.features.TrollMenu;

public class Main extends JavaPlugin {

	private static Main plugin;

	@Override
	public void onEnable() {
		plugin = this;

		File Config = new File("plugins/Troll/config.yml");

		if (Config.exists()) {
			Bukkit.getConsoleSender().sendMessage("[Troll] Config was loaded.");
			reloadConfig();
		} else {
			Bukkit.getConsoleSender().sendMessage("[Troll] Cant find a config, create a new one.");
			getConfig().options().copyDefaults(true);
			saveConfig();
		}
		registerCommands();
		registerListeners();
	}

	public static Main getPlugin() {
		return plugin;
	}

	private void registerCommands() {
		getCommand("troll").setExecutor(new TrollCommand());
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new TrollMenu(), this);
		pm.registerEvents(new Freeze(), this);
		pm.registerEvents(new Control(), this);
	}

}
