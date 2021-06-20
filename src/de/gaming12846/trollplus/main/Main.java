/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.gaming12846.trollplus.bstats.Metrics;
import de.gaming12846.trollplus.commands.BlacklistCommand;
import de.gaming12846.trollplus.commands.TrollCommand;
import de.gaming12846.trollplus.features.Control;
import de.gaming12846.trollplus.features.FlipBehind;
import de.gaming12846.trollplus.features.Freeze;
import de.gaming12846.trollplus.features.SemiBan;
import de.gaming12846.trollplus.features.TNTTrack;
import de.gaming12846.trollplus.features.TrollMenu;
import de.gaming12846.trollplus.utilitys.ConfigLoader;
import de.gaming12846.trollplus.utilitys.UpdateChecker;

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
		Logger logger = this.getLogger();

		ConfigLoader.ConfigLoader();
		registerCommands();
		registerListeners();

		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, 11761);

		new UpdateChecker(this, 12345).getVersion(version -> {
			if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
				logger.info("There is not a new update available.");
			} else {
				logger.info("There is a new update available.");
			}
		});
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
		pm.registerEvents(new SemiBan(), this);
		pm.registerEvents(new TNTTrack(), this);
	}
}
