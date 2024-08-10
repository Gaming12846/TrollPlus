/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

// Listener for handling the PlayerItemHeldEvent
public class PlayerItemHeldListener implements Listener {
    // Event handler for the PlayerItemHeldEvent
    @EventHandler
    private void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        // Cancel item switch if the player has the "TROLLPLUS_CONTROL_TARGET" metadata
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) event.setCancelled(true);
    }
}