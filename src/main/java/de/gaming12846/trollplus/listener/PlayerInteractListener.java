/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Feature freeze
        if (player.hasMetadata("TROLLPLUS_FREEZE")) event.setCancelled(true);

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) event.setCancelled(true);

        // Feature flip behind
        if (player.hasMetadata("TROLLPLUS_FLIP_BEHIND")) {
            Location location = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
            location.setYaw(player.getLocation().getYaw() + 180);
            player.teleport(location);
        }

        // Feature semi ban
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) event.setCancelled(true);
    }
}