package com.github.gaming12846.trollplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * TrollPlus com.github.gaming12846.trollplus.listener PlayerPickupItemListener.java
 *
 * @author Gaming12846
 */
public final class PlayerPickupItemListener implements Listener {

    @EventHandler
    private void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
            event.setCancelled(true);
        }

        // Feature semi ban
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) {
            event.setCancelled(true);
        }

        return;
    }

}