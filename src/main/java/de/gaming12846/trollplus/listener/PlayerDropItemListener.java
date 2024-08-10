/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

// Listener for handling item drop events by players
public class PlayerDropItemListener implements Listener {
    // Event handler for the PlayerDropItemEvent
    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        // Prevent item drop if the player has the "TROLLPLUS_FREEZE" metadata
        if (player.hasMetadata("TROLLPLUS_FREEZE")) event.setCancelled(true);

        // Prevent item drop if the player has the "TROLLPLUS_CONTROL_TARGET" metadata
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) event.setCancelled(true);

        // Prevent item drop if the player has the "TROLLPLUS_SEMI_BAN" metadata
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) event.setCancelled(true);
    }
}