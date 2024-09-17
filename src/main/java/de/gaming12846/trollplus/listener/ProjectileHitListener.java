/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        if (!(event.getEntity() instanceof Arrow arrow)) return;

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

        // Handle potion effect arrow
        if (arrow.hasMetadata("TROLLPLUS_POTION_EFFECT_ARROW")) {
            handlePotionEffectArrow(arrow);
        }
    }

    // Handles the explosion arrow effect
    private void handleExplosionArrow(Arrow arrow) {
        arrow.removeMetadata("TROLLPLUS_EXPLOSION_ARROW", plugin);
        Location location = arrow.getLocation();
        arrow.getWorld().createExplosion(location, 2, plugin.getConfigHelper().getBoolean("set-fire"), plugin.getConfigHelper().getBoolean("break-blocks"));
    }

    // Handles the TNT arrow effect
    private void handleTntArrow(Arrow arrow) {
        arrow.removeMetadata("TROLLPLUS_TNT_ARROW", plugin);
        TNTPrimed tnt = arrow.getWorld().spawn(arrow.getLocation(), TNTPrimed.class);
        tnt.setFuseTicks(40);
        tnt.setMetadata("TROLLPLUS_TNT", new FixedMetadataValue(plugin, tnt));
        tnt.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_TNT_PRIMED, 20, 1);
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
        arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_SILVERFISH_STEP, 20, 1);
        Particle particleType = plugin.getServerVersion() < 1.20 ? Particle.ASH : Particle.EXPLOSION;
        arrow.getWorld().spawnParticle(particleType, arrow.getLocation(), 1);

        for (int i = 0; i < RandomUtils.nextInt(3, 5); i++) {
            Location spawnLocation = arrow.getLocation().add(RandomUtils.nextInt(0, 2), RandomUtils.nextInt(0, 2), RandomUtils.nextInt(0, 2));
            arrow.getWorld().spawnEntity(spawnLocation, EntityType.SILVERFISH);
        }
    }

    // Handles the potion effect arrow effect
    private void handlePotionEffectArrow(Arrow arrow) {
        arrow.removeMetadata("TROLLPLUS_POTION_EFFECT_ARROW", plugin);
        PotionEffectType[] possibleEffects = {PotionEffectType.SLOWNESS, PotionEffectType.MINING_FATIGUE, PotionEffectType.NAUSEA, PotionEffectType.BLINDNESS, PotionEffectType.HUNGER, PotionEffectType.WEAKNESS, PotionEffectType.POISON, PotionEffectType.WITHER, PotionEffectType.LEVITATION};

        // Create a splash potion with the random effect
        ItemStack itemStack = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        if (potionMeta != null) {
            potionMeta.addCustomEffect(new PotionEffect(possibleEffects[RandomUtils.nextInt(0, 8)], 200, 1), true);
            itemStack.setItemMeta(potionMeta);
            ThrownPotion thrownPotion = (ThrownPotion) arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.POTION);
            thrownPotion.setItem(itemStack);
        }
    }
}