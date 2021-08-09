package com.github.gaming12846.trollplus.listener;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.VMConstants;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

/**
 * TrollPlus com.github.gaming12846.trollplus.listener BlockIgniteListener.java
 *
 * @author Gaming12846
 */
public final class BlockIgniteListener implements Listener {

    private final TrollPlus plugin;

    public BlockIgniteListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onBlockIgniteEvent(BlockIgniteEvent event) {
        Entity entity = event.getIgnitingEntity();

        // Lightning bolt bow
        if (!plugin.getConfig().getBoolean(VMConstants.CONFIG_SET_FIRE, true)) {
            assert entity != null;
            if (event.getCause() == BlockIgniteEvent.IgniteCause.LIGHTNING && entity.hasMetadata("TROLLPLUS_LIGHTNING_BOLT")) {
                event.setCancelled(true);
            }
        }
    }

}