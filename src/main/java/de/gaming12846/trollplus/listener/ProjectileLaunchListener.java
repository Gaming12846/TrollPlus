/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.ConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

// Listener for handling projectile launch events
public class ProjectileLaunchListener implements Listener {
    private final TrollPlus plugin;

    // Constructor for the ProjectileLaunchListener
    public ProjectileLaunchListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Event handler for the ProjectileLaunchEvent
    @EventHandler
    private void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        // Check if the projectile is an arrow shot by a player
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        if (!(event.getEntity() instanceof Arrow)) return;

        Arrow arrow = (Arrow) event.getEntity();
        Player player = (Player) arrow.getShooter();
        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();

        if (itemMeta == null) return;

        ConfigUtil langConfig = plugin.getLanguageConfig();
        String displayName = itemMeta.getDisplayName();

        // Handle explosion arrow
        if (displayName.equals(ChatColor.RED + langConfig.getString("trollbows.explosion-bow")) && itemMeta.isUnbreakable())
            handleExplosionArrow(arrow);

        // Handle TNT arrow
        if (displayName.equals(ChatColor.RED + langConfig.getString("trollbows.tnt-bow")) && itemMeta.isUnbreakable())
            handleTntArrow(arrow);

        // Handle lightning bolt arrow
        if (displayName.equals(ChatColor.RED + langConfig.getString("trollbows.lightning-bolt-bow")) && itemMeta.isUnbreakable())
            handleLightningBoltArrow(arrow);

        // Handle silverfish arrow
        if (displayName.equals(ChatColor.RED + langConfig.getString("trollbows.silverfish-bow")) && itemMeta.isUnbreakable())
            handleSilverfishArrow(arrow);
    }

    // Handles the explosion arrow effect
    private void handleExplosionArrow(Arrow arrow) {
        arrow.setMetadata("TROLLPLUS_EXPLOSION_ARROW", new FixedMetadataValue(plugin, arrow));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isInBlock()) {
                    cancel();
                    return;
                }

                arrow.getWorld().spawnParticle(Particle.FIREWORK, arrow.getLocation(), 1, 0, 0, 0, 0);
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    // Handles the TNT arrow effect
    private void handleTntArrow(Arrow arrow) {
        arrow.setMetadata("TROLLPLUS_TNT_ARROW", new FixedMetadataValue(plugin, arrow));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isInBlock()) {
                    cancel();
                    return;
                }

                arrow.getWorld().spawnParticle(Particle.SMOKE, arrow.getLocation(), 1, 0, 0, 0, 0);
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    // Handles the lightning bolt arrow effect
    private void handleLightningBoltArrow(Arrow arrow) {
        arrow.setMetadata("TROLLPLUS_LIGHTNING_BOLT_ARROW", new FixedMetadataValue(plugin, arrow));

        Particle particleType = plugin.getServer().getBukkitVersion().contains("1.13") ? Particle.FIREWORK : Particle.FLASH;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isInBlock()) {
                    cancel();
                    return;
                }

                arrow.getWorld().spawnParticle(particleType, arrow.getLocation(), 1, 0, 0, 0, 0);
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    // Handles the silverfish arrow effect
    private void handleSilverfishArrow(Arrow arrow) {
        arrow.setMetadata("TROLLPLUS_SILVERFISH_ARROW", new FixedMetadataValue(plugin, arrow));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isInBlock()) {
                    cancel();
                    return;
                }

                arrow.getWorld().spawnParticle(Particle.SPIT, arrow.getLocation(), 1, 0, 0, 0, 0);
            }
        }.runTaskTimer(plugin, 0, 1);
    }
}