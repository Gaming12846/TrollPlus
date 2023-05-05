/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Objects;

public class EntityExplodeListener implements Listener {
    private final TrollPlus plugin;

    public EntityExplodeListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        // Feature tnt track and tnt bow
        if (entity instanceof TNTPrimed && entity.hasMetadata("TROLLPLUS_TNT")) {
            if (plugin.getConfig().getBoolean("break-blocks", true)) return;

            event.setCancelled(true);
            Objects.requireNonNull(entity.getLocation().getWorld()).playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20, 1);
        }
    }
}