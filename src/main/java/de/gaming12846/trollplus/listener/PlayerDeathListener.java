/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.LangConstants;
import de.gaming12846.trollplus.constants.MetadataConstants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

// Listener for handling player death events
public class PlayerDeathListener implements Listener {
    private final TrollPlus plugin;

    // Constructor for the PlayerDeathListener
    public PlayerDeathListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Event handler for the PlayerDeathEvent
    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // Handle the control feature to clear the inventory of the player being controlled
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_CONTROL_TARGET))
            plugin.getInventoryClickListener().controlUtil.getPlayer().getInventory().clear();

        // Handle the kill feature to remove the kill metadata and set a custom death message
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_KILL)) {
            player.removeMetadata(MetadataConstants.TROLLPLUS_KILL, plugin);
            event.setDeathMessage(player.getName() + " " + plugin.getConfigHelperLanguage().getString(LangConstants.TROLL_KILL_MESSAGE));
        }
    }
}