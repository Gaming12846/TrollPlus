package com.github.gaming12846.trollplus.listener;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.VMConstants;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * TrollPlus com.github.gaming12846.trollplus.listener ProjectileHitListener.java
 *
 * @author Gaming12846
 */
public class ProjectileHitListener implements Listener {

    private final TrollPlus plugin;

    public ProjectileHitListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onProjectileHitEvent(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();

        // Explosion bow
        if (arrow.hasMetadata("TROLLPLUS_EXPLOSION_ARROW")) {
            arrow.removeMetadata("TROLLPLUS_EXPLOSION_ARROW", plugin);

            arrow.getWorld().createExplosion(arrow.getLocation(), 2, plugin.getConfig().getBoolean(VMConstants.CONFIG_SET_FIRE), plugin.getConfig().getBoolean(VMConstants.CONFIG_BREAK_BLOCKS));
        }

        // TNT bow
        if (arrow.hasMetadata("TROLLPLUS_TNT_ARROW")) {
            arrow.removeMetadata("TROLLPLUS_TNT_ARROW", plugin);

            TNTPrimed tnt = arrow.getWorld().spawn(arrow.getLocation(), TNTPrimed.class);
            tnt.setFuseTicks(40);
            tnt.setMetadata("TROLLPLUS_TNT", new FixedMetadataValue(plugin, tnt));
            tnt.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_TNT_PRIMED, 20, 1);
        }

        // Lightning bolt bow
        if (arrow.hasMetadata("TROLLPLUS_LIGHTNING_BOLT_ARROW")) {
            arrow.removeMetadata("TROLLPLUS_LIGHTNING_BOLT_ARROW", plugin);

            LightningStrike lightningStrike = arrow.getWorld().strikeLightning(arrow.getLocation());
            lightningStrike.setMetadata("TROLLPLUS_LIGHTNING_BOLT", new FixedMetadataValue(plugin, lightningStrike));
        }

        // Silverfish bow
        if (arrow.hasMetadata("TROLLPLUS_SILVERFISH_ARROW")) {
            arrow.removeMetadata("TROLLPLUS_SILVERFISH_ARROW", plugin);

            arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_SILVERFISH_STEP, 20, 1);
            arrow.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, arrow.getLocation(), 1);
            for (byte i = 0; i < 5; i++) {
                arrow.getWorld().spawnEntity(arrow.getLocation().add(RandomUtils.JVM_RANDOM.nextInt(2), RandomUtils.JVM_RANDOM.nextInt(2), RandomUtils.JVM_RANDOM.nextInt(2)), EntityType.SILVERFISH);
            }
        }
    }

}