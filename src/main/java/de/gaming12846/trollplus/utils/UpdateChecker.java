/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

// A utility class for checking updates for the plugin by querying the SpigotMC API
public class UpdateChecker {
    private final TrollPlus plugin;
    private final int resourceId;

    // Constructor for the UpdateChecker
    public UpdateChecker(TrollPlus plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    // Retrieves the latest version of the plugin from the SpigotMC API asynchronously
    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) consumer.accept(scanner.next());
            } catch (IOException exception) {
                plugin.getLogger().warning(plugin.getLanguageConfig().getString("unable-check-updates") + " " + exception.getMessage());
            }
        });
    }
}