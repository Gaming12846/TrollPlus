/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class BlockIgniteListener implements Listener {
    private final TrollPlus plugin;

    public BlockIgniteListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onBlockIgniteEvent(BlockIgniteEvent event) {
        Entity entity = event.getIgnitingEntity();

        // Lightning bolt bow
        if (!plugin.getConfig().getBoolean("set-fire", true)) {
            assert entity != null;
            if (event.getCause() == BlockIgniteEvent.IgniteCause.LIGHTNING && entity.hasMetadata("TROLLPLUS_LIGHTNING_BOLT"))
                event.setCancelled(true);
        }
    }
}