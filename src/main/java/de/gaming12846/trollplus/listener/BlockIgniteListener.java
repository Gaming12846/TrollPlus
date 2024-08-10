/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

// Listener for handling block ignition events
public class BlockIgniteListener implements Listener {
    private final TrollPlus plugin;

    // Constructor for the BlockIgniteListener
    public BlockIgniteListener(TrollPlus plugin) {
        this.plugin = plugin;
    }


    // Event handler for the BlockIgniteEvent
    @EventHandler
    private void onBlockIgniteEvent(BlockIgniteEvent event) {
        Entity entity = event.getIgnitingEntity();

        // Check if the plugin configuration allows setting fire
        if (!plugin.getConfig().getBoolean("set-fire", true)) {
            // Ensure the entity is not null, check the cause and for custom metadata
            if (entity != null && event.getCause() == BlockIgniteEvent.IgniteCause.LIGHTNING && entity.hasMetadata("TROLLPLUS_LIGHTNING_BOLT"))
                event.setCancelled(true);
        }
    }
}