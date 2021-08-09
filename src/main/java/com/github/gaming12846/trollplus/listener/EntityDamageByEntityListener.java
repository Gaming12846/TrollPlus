package com.github.gaming12846.trollplus.listener;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * TrollPlus com.github.gaming12846.trollplus.listener EntityDamageByEntityListener.java
 *
 * @author Gaming12846
 */
public final class EntityDamageByEntityListener implements Listener {

    @EventHandler
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        // Feature freeze
        if (damager.hasMetadata("TROLLPLUS_FREEZE")) {
            event.setCancelled(true);
        }

        // Feature control
        if (damager.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
            event.setCancelled(true);
        }

        // Feature flip behind
        if (damager.hasMetadata("TROLLPLUS_FLIP_BEHIND")) {
            Location location = new Location(damager.getLocation().getWorld(), damager.getLocation().getX(), damager.getLocation().getY(), damager.getLocation().getZ());
            location.setYaw(damager.getLocation().getYaw() + 180);
            damager.teleport(location);
        }

        // Feature semi ban
        if (damager.hasMetadata("TROLLPLUS_SEMI_BAN")) {
            event.setCancelled(true);
        }
    }

}