package com.github.gaming12846.trollplus.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Objects;

/**
 * TrollPlus com.github.gaming12846.trollplus.listener EntityExplodeListener.java
 *
 * @author Gaming12846
 */
public final class EntityExplodeListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        // Feature tnt track
        if (entity.getName().equals("TROLLPLUS_TNT_TRACK_TNT")) {
            event.setCancelled(true);
            Objects.requireNonNull(entity.getLocation().getWorld()).playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20, 1);
        }

    }

}