/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

// Listener for handling entity damage events
public class EntityDamageListener implements Listener {
    private final TrollPlus plugin;

    // Constructor for the EntityDamageListener
    public EntityDamageListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Event handler for the EntityDamageEvent
    @EventHandler
    private void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        // Handle the "rocket" feature
        if (entity instanceof Player player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {

            // Cancel fall damage if the player has the "TROLLPLUS_ROCKET_NO_FALL_DAMAGE" metadata and then remove it
            if (player.hasMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE")) {
                event.setCancelled(true);
                player.removeMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE", plugin);
            }
        }
    }
}