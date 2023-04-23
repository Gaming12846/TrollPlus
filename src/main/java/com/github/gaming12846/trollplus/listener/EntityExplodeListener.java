package com.github.gaming12846.trollplus.listener;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.VMConstants;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Objects;

/**
 * TrollPlus com.github.gaming12846.trollplus.listener EntityExplodeListener.java
 *
 * @author Gaming12846
 */
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
            if (plugin.getConfig().getBoolean(VMConstants.CONFIG_BREAK_BLOCKS, true)) return;

            event.setCancelled(true);
            Objects.requireNonNull(entity.getLocation().getWorld()).playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20, 1);
        }
    }
}