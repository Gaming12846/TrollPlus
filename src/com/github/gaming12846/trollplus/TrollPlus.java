package com.github.gaming12846.trollplus;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.gaming12846.trollplus.commands.BlacklistCommand;
import com.github.gaming12846.trollplus.commands.TrollCommand;
import com.github.gaming12846.trollplus.features.ControlFeature;
import com.github.gaming12846.trollplus.features.FlipBehindFeature;
import com.github.gaming12846.trollplus.features.FreezeFeature;
import com.github.gaming12846.trollplus.features.RocketFeature;
import com.github.gaming12846.trollplus.features.SemiBanFeature;
import com.github.gaming12846.trollplus.features.TNTTrackFeature;
import com.github.gaming12846.trollplus.features.TrollMenuFeature;
import com.github.gaming12846.trollplus.metrics.BStats;
import com.github.gaming12846.trollplus.utils.ConfigLoader;
import com.github.gaming12846.trollplus.utils.UpdateChecker;
import com.github.gaming12846.trollplus.utils.Vars;

/**
 * TrollPlus com.github.gaming12846.trollplus TrollPlus.java
 *
 * @author Gaming12846
 */
public class TrollPlus extends JavaPlugin {
	private static TrollPlus plugin;
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

		if (Vars.Booleans.metricsEnabled == true) {
			@SuppressWarnings("unused")
			BStats metrics = new BStats(this, 11761);
		}

		if (Vars.Booleans.checkForUpdates == true) {
			new UpdateChecker(this, 81193).getVersion(version -> {

				if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
					logger.info(Vars.consolePrefix + "There is no new update available");
				} else {
					logger.info(Vars.consolePrefix + "There is a new update available");
					logger.info(Vars.consolePrefix
							+ "To download the latest version visit: https://www.spigotmc.org/resources/81193/ , https://github.com/Gaming12846/TrollPlus/releases or https://discord.com/invite/XvK2UMfGEJ");
				}

			});
		}
	}

	public static TrollPlus getPlugin() {
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
		pm.registerEvents(new TrollMenuFeature(), this);
		pm.registerEvents(new FreezeFeature(), this);
		pm.registerEvents(new ControlFeature(), this);
		pm.registerEvents(new FlipBehindFeature(), this);
		pm.registerEvents(new SemiBanFeature(), this);
		pm.registerEvents(new TNTTrackFeature(), this);
		pm.registerEvents(new RocketFeature(), this);
	}
}