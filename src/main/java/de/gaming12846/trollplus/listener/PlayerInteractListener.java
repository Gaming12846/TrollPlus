/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

// Listener for handling player interaction events
public class PlayerInteractListener implements Listener {
    // Event handler for the PlayerInteractEvent
    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Prevent player interaction if they are frozen
        if (player.hasMetadata("TROLLPLUS_FREEZE")) event.setCancelled(true);

        // Prevent player interaction if they are being controlled
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) event.setCancelled(true);

        // Flip the player's direction if they have the "flip behind" feature
        if (player.hasMetadata("TROLLPLUS_FLIP_BEHIND")) {
            Location location = player.getLocation();
            location.setYaw(player.getLocation().getYaw() + 180);
            player.teleport(location);
        }

        // Prevent player interaction if they are semi-banned
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) event.setCancelled(true);
    }
}