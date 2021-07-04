package com.github.gaming12846.trollplus.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import com.github.gaming12846.trollplus.TrollPlus;

/**
 * TrollPlus com.github.gaming12846.trollplus.utils UpdateChecker.java
 *
 * @author Gaming12846
 */
public class UpdateChecker {

	private JavaPlugin plugin;
	private int resourceId;

	public UpdateChecker(JavaPlugin plugin, int resourceId) {
		this.plugin = plugin;
		this.resourceId = resourceId;
	}

	public void getVersion(final Consumer<String> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
			try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
					Scanner scanner = new Scanner(inputStream)) {

				if (scanner.hasNext())
					consumer.accept(scanner.next());

			} catch (IOException exception) {
				TrollPlus.logger.warning(Vars.consolePrefix + "Cannot look for updates: " + exception.getMessage());
			}
		});
	}
}
