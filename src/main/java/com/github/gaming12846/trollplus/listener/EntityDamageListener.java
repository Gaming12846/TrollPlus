package com.github.gaming12846.trollplus.listener;

import com.github.gaming12846.trollplus.TrollPlus;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * TrollPlus com.github.gaming12846.trollplus.listener EntityDamageListener.java
 *
 * @author Gaming12846
 */
public class EntityDamageListener implements Listener {
    private final TrollPlus plugin;

    public EntityDamageListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        // Feature rocket
        if (entity instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player player = (Player) entity;

            if (player.hasMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE")) {
                event.setCancelled(true);
                player.removeMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE", plugin);
            }
        }
    }
}