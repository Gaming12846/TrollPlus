/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;

// Listener for handling events when projectiles hit entities or blocks
public class ProjectileHitListener implements Listener {
    private final TrollPlus plugin;

    // Constructor for the ProjectileHitListener
    public ProjectileHitListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Event handler for the ProjectileHitEvent
    @EventHandler
    private void onProjectileHitEvent(ProjectileHitEvent event) {
        // Check if the projectile is an arrow shot by a player
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        if (!(event.getEntity() instanceof Arrow)) return;

        Arrow arrow = (Arrow) event.getEntity();

        // Handle explosion arrow
        if (arrow.hasMetadata("TROLLPLUS_EXPLOSION_ARROW")) {
            handleExplosionArrow(arrow);
        }

        // Handle TNT arrow
        if (arrow.hasMetadata("TROLLPLUS_TNT_ARROW")) {
            handleTntArrow(arrow);
        }

        // Handle lightning bolt arrow
        if (arrow.hasMetadata("TROLLPLUS_LIGHTNING_BOLT_ARROW")) {
            handleLightningBoltArrow(arrow);
        }

        // Handle silverfish arrow
        if (arrow.hasMetadata("TROLLPLUS_SILVERFISH_ARROW")) {
            handleSilverfishArrow(arrow);
        }
    }

    // Handles the explosion arrow effect
    private void handleExplosionArrow(Arrow arrow) {
        arrow.removeMetadata("TROLLPLUS_EXPLOSION_ARROW", plugin);
        Location location = arrow.getLocation();
        arrow.getWorld().createExplosion(location, 2, plugin.getConfig().getBoolean("set-fire"), plugin.getConfig().getBoolean("break-blocks"));
    }

    // Handles the TNT arrow effect
    private void handleTntArrow(Arrow arrow) {
        arrow.removeMetadata("TROLLPLUS_TNT_ARROW", plugin);
        TNTPrimed tnt = arrow.getWorld().spawn(arrow.getLocation(), TNTPrimed.class);
        tnt.setFuseTicks(40);
        tnt.setMetadata("TROLLPLUS_TNT", new FixedMetadataValue(plugin, tnt));
        tnt.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_TNT_PRIMED, 20.0f, 1.0f);
    }

    // Handles the lightning bolt arrow effect
    private void handleLightningBoltArrow(Arrow arrow) {
        arrow.removeMetadata("TROLLPLUS_LIGHTNING_BOLT_ARROW", plugin);
        LightningStrike lightningStrike = arrow.getWorld().strikeLightning(arrow.getLocation());
        lightningStrike.setMetadata("TROLLPLUS_LIGHTNING_BOLT", new FixedMetadataValue(plugin, lightningStrike));
    }

    // Handles the silverfish arrow effect
    private void handleSilverfishArrow(Arrow arrow) {
        arrow.removeMetadata("TROLLPLUS_SILVERFISH_ARROW", plugin);
        arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_SILVERFISH_STEP, 20.0f, 1.0f);
        arrow.getWorld().spawnParticle(Particle.EXPLOSION, arrow.getLocation(), 1);

        for (int i = 0; i < RandomUtils.nextInt(3, 5); i++) {
            Location spawnLocation = arrow.getLocation().add(RandomUtils.nextInt(0, 2), RandomUtils.nextInt(0, 2), RandomUtils.nextInt(0, 2));
            arrow.getWorld().spawnEntity(spawnLocation, EntityType.SILVERFISH);
        }
    }
}