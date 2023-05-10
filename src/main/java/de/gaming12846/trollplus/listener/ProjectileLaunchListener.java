/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class ProjectileLaunchListener implements Listener {
    private final TrollPlus plugin;

    public ProjectileLaunchListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        if (!(event.getEntity() instanceof Arrow)) return;

        Arrow arrow = (Arrow) event.getEntity();
        Player player = (Player) arrow.getShooter();

        if (player.getInventory().getItemInMainHand().getItemMeta() == null) return;

        // Explosion bow
        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Explosion bow")) {
            arrow.setMetadata("TROLLPLUS_EXPLOSION_ARROW", new FixedMetadataValue(plugin, arrow));

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (arrow.isInBlock()) {
                        cancel();
                        return;
                    }

                    arrow.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, arrow.getLocation(), 1, 0, 0, 0, 0);
                }
            }.runTaskTimer(plugin, 0, 1);
        }

        // TNT bow
        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "TNT bow")) {
            arrow.setMetadata("TROLLPLUS_TNT_ARROW", new FixedMetadataValue(plugin, arrow));

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (arrow.isInBlock()) {
                        cancel();
                        return;
                    }

                    arrow.getWorld().spawnParticle(Particle.SMOKE_NORMAL, arrow.getLocation(), 1, 0, 0, 0, 0);
                }
            }.runTaskTimer(plugin, 0, 1);
        }

        // Lightning bolt bow
        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Lightning bolt bow")) {
            arrow.setMetadata("TROLLPLUS_LIGHTNING_BOLT_ARROW", new FixedMetadataValue(plugin, arrow));

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (arrow.isInBlock()) {
                        cancel();
                        return;
                    }

                    arrow.getWorld().spawnParticle(Particle.FLASH, arrow.getLocation(), 1, 0, 0, 0, 0);
                }
            }.runTaskTimer(plugin, 0, 1);
        }

        // Silverfish bow
        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Silverfish bow")) {
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
}