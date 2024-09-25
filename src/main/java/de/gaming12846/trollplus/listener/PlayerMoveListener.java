/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.constants.MetadataConstants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

// Listener for handling player move events
public class PlayerMoveListener implements Listener {
    // Event handler for the PlayerMoveEvent
    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Cancel movement if the player has the "TROLLPLUS_FREEZE" metadata
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_FREEZE)) event.setCancelled(true);
    }
}