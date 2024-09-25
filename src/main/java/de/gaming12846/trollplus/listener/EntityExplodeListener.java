/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.ConfigConstants;
import de.gaming12846.trollplus.constants.MetadataConstants;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Objects;

// Listener for handling entity explosion events
public class EntityExplodeListener implements Listener {
    private final TrollPlus plugin;

    // Constructor for the EntityExplodeListener
    public EntityExplodeListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Event handler for the EntityExplodeEvent
    @EventHandler
    private void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        // Check if the exploding entity is a TNTPrimed entity with the "TROLLPLUS_TNT" metadata
        if (entity instanceof TNTPrimed && entity.hasMetadata(MetadataConstants.TROLLPLUS_TNT)) {
            // If the plugin configuration allows block breaking, exit the method early
            if (plugin.getConfigHelper().getBoolean(ConfigConstants.BREAK_BLOCKS)) return;

            // Cancel the explosion event and play an explosion sound at the entity's location
            event.setCancelled(true);
            Objects.requireNonNull(entity.getLocation().getWorld()).playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20, 1);
        }
    }
}