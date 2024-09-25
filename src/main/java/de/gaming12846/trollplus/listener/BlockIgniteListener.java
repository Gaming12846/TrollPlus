/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.ConfigConstants;
import de.gaming12846.trollplus.constants.MetadataConstants;
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
        if (!plugin.getConfigHelper().getBoolean(ConfigConstants.SET_FIRE)) {
            // Ensure the entity is not null, check the cause and for "TROLLPLUS_TROLLBOWS_LIGHTNING_BOLT" metadata
            if (entity != null && event.getCause() == BlockIgniteEvent.IgniteCause.LIGHTNING && entity.hasMetadata(MetadataConstants.TROLLPLUS_TROLLBOWS_LIGHTNING_BOLT))
                event.setCancelled(true);
        }
    }
}