package com.github.gaming12846.trollplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * TrollPlus com.github.gaming12846.trollplus.listener PlayerPickupItemListener.java
 *
 * @author Gaming12846
 */
public class EntityPickupItemEvent implements Listener {

    @EventHandler
    private void onEntityPickupItemEvent(org.bukkit.event.entity.EntityPickupItemEvent event) {
        Player player = (Player) event.getEntity();

        // Feature freeze
        if (player.hasMetadata("TROLLPLUS_FREEZE")) {
            event.setCancelled(true);
        }

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
            event.setCancelled(true);
        }

        // Feature semi ban
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) {
            event.setCancelled(true);
        }
    }

}