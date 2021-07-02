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
import de.gaming12846.trollplus.utilitys.Vars;

public class Main extends JavaPlugin {

	private static Main plugin;
	public static Logger logger = Bukkit.getLogger();

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

		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, 11761);

		new UpdateChecker(this, 81193).getVersion(version -> {
			if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
				logger.info(Vars.consolePrefix + "There is no new update available");
			} else {
				logger.info(Vars.consolePrefix + "There is a new update available");
				logger.info(Vars.consolePrefix
						+ "To download the latest version visit: https://www.spigotmc.org/resources/troll-plus.81193/ or https://github.com/Gaming12846/TrollPlus/releases/");
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
