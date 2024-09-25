/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.constants.MetadataConstants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

// Listener for handling entity item pickup events
public class EntityPickupItemEvent implements Listener {
    // Event handler for the EntityPickupItemEvent
    @EventHandler
    private void onEntityPickupItemEvent(org.bukkit.event.entity.EntityPickupItemEvent event) {
        // Check if the entity picking up the item is a player
        if (!(event.getEntity() instanceof Player player)) return;

        // Cancel the item pickup event if the player has the "TROLLPLUS_FREEZE" metadata
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_FREEZE)) event.setCancelled(true);

        // Cancel the item pickup event if the player has the "TROLLPLUS_CONTROL_TARGET" metadata
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_CONTROL_TARGET)) event.setCancelled(true);

        // Cancel the item pickup event if the player has the "TROLLPLUS_SEMI_BAN" metadata
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_SEMI_BAN)) event.setCancelled(true);
    }
}