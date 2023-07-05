/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

import static de.gaming12846.trollplus.TrollPlus.BUKKIT_LOGGER;

// From: https://www.spigotmc.org/wiki/creating-an-update-checker-that-checks-for-updates
public class UpdateChecker {
    private final TrollPlus plugin;
    private final int resourceId;

    public UpdateChecker(TrollPlus plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                BUKKIT_LOGGER.info(Constants.PLUGIN_CONSOLE_PREFIX + plugin.getLanguageConfig().getConfig().getString("unable-check-updates") + " " + exception.getMessage());
            }
        });
    }
}