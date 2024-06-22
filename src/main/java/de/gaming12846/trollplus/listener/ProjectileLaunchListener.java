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

public class ProjectileLaunchListener implements Listener {
    private final TrollPlus plugin;

    public ProjectileLaunchListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        if (!(event.getEntity() instanceof Arrow)) return;

        ConfigUtil langConfig = plugin.getLanguageConfig();

        Arrow arrow = (Arrow) event.getEntity();
        Player player = (Player) arrow.getShooter();
        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();

        assert itemMeta != null;

        // Explosion bow
        if (itemMeta.getDisplayName().equals(ChatColor.RED + langConfig.getString("trollbows.explosion-bow")) && itemMeta.isUnbreakable()) {
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

        // TNT bow
        if (itemMeta.getDisplayName().equals(ChatColor.RED + langConfig.getString("trollbows.tnt-bow")) && itemMeta.isUnbreakable()) {
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

        // Lightning bolt bow
        if (itemMeta.getDisplayName().equals(ChatColor.RED + langConfig.getString("trollbows.lighting-bolt-bow")) && itemMeta.isUnbreakable()) {
            arrow.setMetadata("TROLLPLUS_LIGHTNING_BOLT_ARROW", new FixedMetadataValue(plugin, arrow));

            if (plugin.getServer().getBukkitVersion().contains("1.13")) {
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
            } else {
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
        }

        // Silverfish bow
        if (itemMeta.getDisplayName().equals(ChatColor.RED + langConfig.getString("trollbows.silverfish-bow")) && itemMeta.isUnbreakable()) {
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