/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.gaming12846.troll.commands.BlacklistCommand;
import de.gaming12846.troll.commands.TrollCommand;
import de.gaming12846.troll.features.Control;
import de.gaming12846.troll.features.FlipBehind;
import de.gaming12846.troll.features.Freeze;
import de.gaming12846.troll.features.TrollMenu;
import de.gaming12846.troll.utilitys.ConfigLoader;

public class Main extends JavaPlugin {

	private static Main plugin;

	// Plugin disable
	@Override
	public void onDisable() {
	}

	// Plugin enable
	@Override
	public void onEnable() {
		plugin = this;
		ConfigLoader.ConfigLoader();
		registerCommands();
		registerListeners();
	}

	public static Main getPlugin() {
		return plugin;
	}

	// Register commands
	private void registerCommands() {
		getCommand("troll").setExecutor(new TrollCommand());
		getCommand("trollblacklist").setExecutor(new BlacklistCommand());
	}

	// Register listeners
	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new TrollMenu(), this);
		pm.registerEvents(new Freeze(), this);
		pm.registerEvents(new Control(), this);
		pm.registerEvents(new FlipBehind(), this);
	}
}
