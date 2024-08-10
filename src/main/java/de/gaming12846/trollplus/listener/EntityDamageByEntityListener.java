/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

// Listener for handling entity damage by entity events
public class EntityDamageByEntityListener implements Listener {
    // Event handler for the EntityDamageByEntityEvent
    @EventHandler
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        // Cancel damage if the damager has the "TROLLPLUS_FREEZE" metadata
        if (damager.hasMetadata("TROLLPLUS_FREEZE")) event.setCancelled(true);

        // Cancel damage if the damager has the "TROLLPLUS_CONTROL_TARGET" metadata
        if (damager.hasMetadata("TROLLPLUS_CONTROL_TARGET")) event.setCancelled(true);

        // Flip the damager 180 degrees if it has the "TROLLPLUS_FLIP_BEHIND" metadata
        if (damager.hasMetadata("TROLLPLUS_FLIP_BEHIND")) {
            Location location = damager.getLocation();
            location.setYaw(damager.getLocation().getYaw() + 180);
            damager.teleport(location);
        }

        // Cancel damage if the damager has the "TROLLPLUS_SEMI_BAN" metadata
        if (damager.hasMetadata("TROLLPLUS_SEMI_BAN")) event.setCancelled(true);
    }
}