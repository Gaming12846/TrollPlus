/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.LangConstants;
import de.gaming12846.trollplus.constants.MetadataConstants;
import de.gaming12846.trollplus.utils.ConfigHelper;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
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
        if (!(event.getEntity() instanceof Arrow arrow)) return;

        Player player = (Player) arrow.getShooter();
        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();

        if (itemMeta == null) return;

        ConfigHelper configHelperLanguage = plugin.getConfigHelperLanguage();
        String displayName = itemMeta.getDisplayName();

        // Handle explosion arrow
        if (displayName.equals(ChatColor.RED + configHelperLanguage.getString(LangConstants.TROLLBOWS_EXPLOSION_BOW)) && itemMeta.isUnbreakable()) {
            handleExplosionArrow(arrow);
            if (plugin.getServerVersion() < 1.20 && player.getGameMode() != GameMode.CREATIVE)
                player.getInventory().addItem(new ItemStack(Material.ARROW));
        }

        // Handle TNT arrow
        if (displayName.equals(ChatColor.RED + configHelperLanguage.getString(LangConstants.TROLLBOWS_TNT_BOW)) && itemMeta.isUnbreakable()) {
            handleTntArrow(arrow);
            if (plugin.getServerVersion() < 1.20 && player.getGameMode() != GameMode.CREATIVE)
                player.getInventory().addItem(new ItemStack(Material.ARROW));
        }

        // Handle lightning bolt arrow
        if (displayName.equals(ChatColor.RED + configHelperLanguage.getString(LangConstants.TROLLBOWS_LIGHTNING_BOLT_BOW)) && itemMeta.isUnbreakable()) {
            handleLightningBoltArrow(arrow);
            if (plugin.getServerVersion() < 1.20 && player.getGameMode() != GameMode.CREATIVE)
                player.getInventory().addItem(new ItemStack(Material.ARROW));
        }

        // Handle silverfish arrow
        if (displayName.equals(ChatColor.RED + configHelperLanguage.getString(LangConstants.TROLLBOWS_SILVERFISH_BOW)) && itemMeta.isUnbreakable()) {
            handleSilverfishArrow(arrow);
            if (plugin.getServerVersion() < 1.20 && player.getGameMode() != GameMode.CREATIVE)
                player.getInventory().addItem(new ItemStack(Material.ARROW));
        }

        // Handle potion effect arrow
        if (displayName.equals(ChatColor.RED + configHelperLanguage.getString(LangConstants.TROLLBOWS_POTION_EFFECT_BOW)) && itemMeta.isUnbreakable()) {
            handlePotionEffectArrow(arrow);
            if (plugin.getServerVersion() < 1.20 && player.getGameMode() != GameMode.CREATIVE)
                player.getInventory().addItem(new ItemStack(Material.ARROW));
        }
    }

    // Handles the explosion arrow effect
    private void handleExplosionArrow(Arrow arrow) {
        arrow.setMetadata(MetadataConstants.TROLLPLUS_TROLLBOWS_EXPLOSION_ARROW, new FixedMetadataValue(plugin, arrow));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isInBlock() || arrow.isOnGround() ||arrow.isInWater() ||arrow.isDead()) {
                    cancel();
                    return;
                }

                Particle particleType = plugin.getServerVersion() < 1.20 ? Particle.FLAME : Particle.FIREWORK;
                arrow.getWorld().spawnParticle(particleType, arrow.getLocation(), 1, 0, 0, 0, 0);
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    // Handles the TNT arrow effect
    private void handleTntArrow(Arrow arrow) {
        arrow.setMetadata(MetadataConstants.TROLLPLUS_TROLLBOWS_TNT_ARROW, new FixedMetadataValue(plugin, arrow));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isInBlock() || arrow.isOnGround() ||arrow.isInWater() ||arrow.isDead()) {
                    cancel();
                    return;
                }

                Particle particleType = plugin.getServerVersion() < 1.20 ? Particle.FLAME : Particle.SMOKE;
                arrow.getWorld().spawnParticle(particleType, arrow.getLocation(), 1, 0, 0, 0, 0);
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    // Handles the lightning bolt arrow effect
    private void handleLightningBoltArrow(Arrow arrow) {
        arrow.setMetadata(MetadataConstants.TROLLPLUS_TROLLBOWS_LIGHTNING_BOLT_ARROW, new FixedMetadataValue(plugin, arrow));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isInBlock() || arrow.isOnGround() ||arrow.isInWater() ||arrow.isDead()) {
                    cancel();
                    return;
                }

                Particle particleType = plugin.getServerVersion() < 1.20 ? Particle.FLASH : Particle.FIREWORK;
                arrow.getWorld().spawnParticle(particleType, arrow.getLocation(), 1, 0, 0, 0, 0);
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    // Handles the silverfish arrow effect
    private void handleSilverfishArrow(Arrow arrow) {
        arrow.setMetadata(MetadataConstants.TROLLPLUS_TROLLBOWS_SILVERFISH_ARROW, new FixedMetadataValue(plugin, arrow));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isInBlock() || arrow.isOnGround() ||arrow.isInWater() ||arrow.isDead()) {
                    cancel();
                    return;
                }

                arrow.getWorld().spawnParticle(Particle.SPIT, arrow.getLocation(), 1, 0, 0, 0, 0);
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    // Handles the potion effect arrow effect
    private void handlePotionEffectArrow(Arrow arrow) {
        arrow.setMetadata(MetadataConstants.TROLLPLUS_TROLLBOWS_POTION_EFFECT_ARROW, new FixedMetadataValue(plugin, arrow));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isInBlock() || arrow.isOnGround() || arrow.isInWater() || arrow.isDead()) {
                    cancel();
                    return;
                }

                arrow.getWorld().spawnParticle(Particle.EFFECT, arrow.getLocation(), 1, 0, 0, 0, 0);
            }
        }.runTaskTimer(plugin, 0, 1);
    }
}