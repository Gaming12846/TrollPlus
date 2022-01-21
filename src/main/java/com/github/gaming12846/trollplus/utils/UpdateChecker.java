package com.github.gaming12846.trollplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * TrollPlus com.github.gaming12846.trollplus.utils UpdateChecker.java
 *
 * @author Gaming12846
 */
public final class UpdateChecker {

    private final JavaPlugin plugin;
    private final int resourceId;

    // Update checker
    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    // Get version for update checker
    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {

            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                this.plugin.getLogger().warning("Cannot look for updates: " + exception.getMessage());
            }

        });
    }

}