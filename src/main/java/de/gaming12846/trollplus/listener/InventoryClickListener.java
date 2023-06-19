/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.Constants;
import de.gaming12846.trollplus.utils.ControlUtil;
import de.gaming12846.trollplus.utils.GUIUtil;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InventoryClickListener implements Listener {
    private final TrollPlus plugin;
    public ControlUtil controlUtil;

    public InventoryClickListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        // Feature freeze
        if (player.hasMetadata("TROLLPLUS_FREEZE")) event.setCancelled(true);

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) event.setCancelled(true);

        // Feature semi ban
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) event.setCancelled(true);

        FileConfiguration langConfig = plugin.getLanguageConfig().getConfig();

        if (plugin.getTrollCommand().trollGUI != null && Objects.equals(event.getClickedInventory(), plugin.getTrollCommand().trollGUI.getGUI())) {
            int slot = event.getSlot();
            GUIUtil trollGUI = plugin.getTrollCommand().trollGUI.getGUIUtil();
            Player target = trollGUI.getTarget();

            event.setCancelled(true);

            if (slot == 49) {
                int[] slots = new int[]{10, 12, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44};
                slot = slots[RandomUtils.nextInt(0, slots.length)];
            }

            switch (slot) {
                default:
                    return;
                case 45:
                    if (target == player) {
                        player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll.vanish-not-available"));

                        break;
                    }

                    if (!target.hasMetadata("TROLLPLUS_VANISH")) {
                        target.setMetadata("TROLLPLUS_VANISH", new FixedMetadataValue(plugin, target.getName()));
                        target.hidePlayer(plugin, player);
                        if (plugin.getConfig().getBoolean("vanish-join-quit-message-enabled", true)) {
                            String vanishQuitMessage = plugin.getConfig().getString("vanish-quit-message");
                            assert vanishQuitMessage != null;
                            target.sendMessage(vanishQuitMessage.replace("[player]", player.getName()));
                        }

                        trollGUI.addItem(47, ItemBuilder.createItemWithLore(Material.POTION, ChatColor.WHITE + langConfig.getString("troll-gui.vanish") + " " + trollGUI.getStatus("TROLLPLUS_VANISH"), langConfig.getString("troll-gui.vanish-description")));

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_VANISH", plugin);
                    target.showPlayer(plugin, player);
                    if (plugin.getConfig().getBoolean("vanish-join-quit-message-enabled", true)) {
                        String vanishJoinMessage = plugin.getConfig().getString("vanish-join-message");
                        assert vanishJoinMessage != null;
                        target.sendMessage(vanishJoinMessage.replace("[player]", player.getName()));
                    }

                    trollGUI.addItem(47, ItemBuilder.createItemWithLore(Material.POTION, ChatColor.WHITE + langConfig.getString("troll-gui.vanish") + " " + trollGUI.getStatus("TROLLPLUS_VANISH"), langConfig.getString("troll-gui.vanish-description")));

                    break;
                case 46:
                    if (target == player) {
                        player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll.teleport-not-available"));

                        break;
                    }

                    player.teleport(target);

                    break;
                case 47:
                    target.setHealth(0.0);

                    break;
                case 51:
                    player.openInventory(Objects.requireNonNull(target.getPlayer()).getInventory());

                    break;
                case 52:
                    player.openInventory(Objects.requireNonNull(target.getPlayer()).getEnderChest());

                    break;
                case 53:
                    player.closeInventory();

                    break;
                // Features
                case 10:
                    if (!target.hasMetadata("TROLLPLUS_FREEZE")) {
                        target.setMetadata("TROLLPLUS_FREEZE", new FixedMetadataValue(plugin, target.getName()));
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 6));
                        trollGUI.addItem(10, ItemBuilder.createItemWithLore(Material.ICE, ChatColor.WHITE + langConfig.getString("troll-gui.freeze") + " " + trollGUI.getStatus("TROLLPLUS_FREEZE"), langConfig.getString("troll-gui.freeze-description")));

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_FREEZE", plugin);
                    target.removePotionEffect(PotionEffectType.SLOW);
                    trollGUI.addItem(10, ItemBuilder.createItemWithLore(Material.ICE, ChatColor.WHITE + langConfig.getString("troll-gui.freeze") + " " + trollGUI.getStatus("TROLLPLUS_FREEZE"), langConfig.getString("troll-gui.freeze-description")));

                    break;
                case 12:
                    if (!target.hasMetadata("TROLLPLUS_HAND_ITEM_DROP")) {
                        target.setMetadata("TROLLPLUS_HAND_ITEM_DROP", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(12, ItemBuilder.createItemWithLore(Material.SHEARS, ChatColor.WHITE + langConfig.getString("troll-gui.hand-item-drop") + " " + trollGUI.getStatus("TROLLPLUS_HAND_ITEM_DROP"), langConfig.getString("troll-gui.hand-item-drop-description")));
                        handItemDrop(target);

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_HAND_ITEM_DROP", plugin);
                    trollGUI.addItem(12, ItemBuilder.createItemWithLore(Material.SHEARS, ChatColor.WHITE + langConfig.getString("troll-gui.hand-item-drop") + " " + trollGUI.getStatus("TROLLPLUS_HAND_ITEM_DROP"), langConfig.getString("troll-gui.hand-item-drop-description")));

                    break;
                case 14:
                    if (target == player) {
                        player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll.control-not-available"));

                        break;
                    }

                    if (!target.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
                        target.setMetadata("TROLLPLUS_CONTROL_TARGET", new FixedMetadataValue(plugin, target.getName()));
                        player.setMetadata("TROLLPLUS_CONTROL_PLAYER", new FixedMetadataValue(plugin, player.getName()));
                        trollGUI.addItem(14, ItemBuilder.createItemWithLore(Material.LEAD, ChatColor.WHITE + langConfig.getString("troll-gui.control") + " " + trollGUI.getStatus("TROLLPLUS_CONTROL_TARGET"), langConfig.getString("troll-gui.control-description")));

                        controlUtil = new ControlUtil(plugin, target, player);
                        controlUtil.control();

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_CONTROL_TARGET", plugin);
                    target.removeMetadata("TROLLPLUS_CONTROL_PLAYER", plugin);
                    trollGUI.addItem(14, ItemBuilder.createItemWithLore(Material.LEAD, ChatColor.WHITE + langConfig.getString("troll-gui.control") + " " + trollGUI.getStatus("TROLLPLUS_CONTROL_TARGET"), langConfig.getString("troll-gui.control-description")));

                    break;
                case 16:
                    if (!target.hasMetadata("TROLLPLUS_FLIP_BEHIND")) {
                        target.setMetadata("TROLLPLUS_FLIP_BEHIND", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(16, ItemBuilder.createItemWithLore(Material.COMPASS, ChatColor.WHITE + langConfig.getString("troll-gui.flip-backwards") + " " + trollGUI.getStatus("TROLLPLUS_FLIP_BEHIND"), langConfig.getString("troll-gui.flip-backwards-description")));

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_FLIP_BEHIND", plugin);
                    trollGUI.addItem(16, ItemBuilder.createItemWithLore(Material.COMPASS, ChatColor.WHITE + langConfig.getString("troll-gui.flip-backwards") + " " + trollGUI.getStatus("TROLLPLUS_FLIP_BEHIND"), langConfig.getString("troll-gui.flip-backwards-description")));

                    break;
                case 18:
                    if (!target.hasMetadata("TROLLPLUS_SPANK")) {
                        target.setMetadata("TROLLPLUS_SPANK", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(18, ItemBuilder.createItemWithLore(Material.SNOWBALL, ChatColor.WHITE + langConfig.getString("troll-gui.spank") + " " + trollGUI.getStatus("TROLLPLUS_SPANK"), langConfig.getString("troll-gui.spank-description")));
                        spank(target);

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_SPANK", plugin);
                    trollGUI.addItem(18, ItemBuilder.createItemWithLore(Material.SNOWBALL, ChatColor.WHITE + langConfig.getString("troll-gui.spank") + " " + trollGUI.getStatus("TROLLPLUS_SPANK"), langConfig.getString("troll-gui.spank-description")));

                    break;
                case 20:
                    if (!target.hasMetadata("TROLLPLUS_SPAM_MESSAGES")) {
                        target.setMetadata("TROLLPLUS_SPAM_MESSAGES", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + langConfig.getString("troll-gui.spam-messages") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_MESSAGES"), langConfig.getString("troll-gui.spam-messages-description")));
                        spamMessages(target);

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_SPAM_MESSAGES", plugin);
                    trollGUI.addItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + langConfig.getString("troll-gui.spam-messages") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_MESSAGES"), langConfig.getString("troll-gui.spam-messages-description")));

                    break;
                case 22:
                    if (!target.hasMetadata("TROLLPLUS_SPAM_SOUNDS")) {
                        target.setMetadata("TROLLPLUS_SPAM_SOUNDS", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(22, ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, ChatColor.WHITE + langConfig.getString("troll-gui.spam-sounds") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_SOUNDS"), langConfig.getString("troll-gui.spam-sounds-description")));
                        spamSounds(target);

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_SPAM_SOUNDS", plugin);
                    trollGUI.addItem(22, ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, ChatColor.WHITE + langConfig.getString("troll-gui.spam-sounds") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_SOUNDS"), langConfig.getString("troll-gui.spam-sounds-description")));

                    break;
                case 24:
                    if (!target.hasMetadata("TROLLPLUS_SEMI_BAN")) {
                        target.setMetadata("TROLLPLUS_SEMI_BAN", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(24, ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, ChatColor.WHITE + langConfig.getString("troll-gui.semi-ban") + " " + trollGUI.getStatus("TROLLPLUS_SEMI_BAN"), langConfig.getString("troll-gui.semi-ban-description")));
                        spamSounds(target);

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_SEMI_BAN", plugin);
                    trollGUI.addItem(24, ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, ChatColor.WHITE + langConfig.getString("troll-gui.semi-ban") + " " + trollGUI.getStatus("TROLLPLUS_SEMI_BAN"), langConfig.getString("troll-gui.semi-ban-description")));

                    break;
                case 26:
                    if (!target.hasMetadata("TROLLPLUS_FALLING_ANVIL")) {
                        Location loc = target.getLocation();

                        for (int i = 0; i < 3; i++) {
                            if (loc.getBlock().getType().equals(Material.AIR)) {
                                loc.setY(loc.getY() + 1);
                            } else {
                                player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll.falling-anvil-not-available"));

                                return;
                                //break;
                            }
                        }

                        target.setMetadata("TROLLPLUS_FALLING_ANVIL", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(26, ItemBuilder.createItemWithLore(Material.ANVIL, ChatColor.WHITE + langConfig.getString("troll-gui.falling-anvil") + " " + trollGUI.getStatus("TROLLPLUS_FALLING_ANVIL"), langConfig.getString("troll-gui.falling-anvil-description")));
                        fallingAnvil(target);

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_FALLING_ANVIL", plugin);
                    trollGUI.addItem(26, ItemBuilder.createItemWithLore(Material.ANVIL, ChatColor.WHITE + langConfig.getString("troll-gui.falling-anvil") + " " + trollGUI.getStatus("TROLLPLUS_FALLING_ANVIL"), langConfig.getString("troll-gui.falling-anvil-description")));

                    break;
                case 28:
                    if (!target.hasMetadata("TROLLPLUS_TNT_TRACK")) {
                        target.setMetadata("TROLLPLUS_TNT_TRACK", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(28, ItemBuilder.createItemWithLore(Material.TNT, ChatColor.WHITE + langConfig.getString("troll-gui.tnt-track") + " " + trollGUI.getStatus("TROLLPLUS_TNT_TRACK"), langConfig.getString("troll-gui.tnt-track-description")));
                        tntTrack(target);

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_TNT_TRACK", plugin);
                    trollGUI.addItem(28, ItemBuilder.createItemWithLore(Material.TNT, ChatColor.WHITE + langConfig.getString("troll-gui.tnt-track") + " " + trollGUI.getStatus("TROLLPLUS_TNT_TRACK"), langConfig.getString("troll-gui.tnt-track-description")));

                    break;
                case 30:
                    if (!target.hasMetadata("TROLLPLUS_MOB_SPAWNER")) {
                        target.setMetadata("TROLLPLUS_MOB_SPAWNER", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(30, ItemBuilder.createItemWithLore(Material.SPAWNER, ChatColor.WHITE + langConfig.getString("troll-gui.mob-spawner") + " " + trollGUI.getStatus("TROLLPLUS_MOB_SPAWNER"), langConfig.getString("troll-gui.mob-spawner-description")));
                        mobSpawner(target);

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_MOB_SPAWNER", plugin);
                    trollGUI.addItem(30, ItemBuilder.createItemWithLore(Material.SPAWNER, ChatColor.WHITE + langConfig.getString("troll-gui.mob-spawner") + " " + trollGUI.getStatus("TROLLPLUS_MOB_SPAWNER"), langConfig.getString("troll-gui.mob-spawner-description")));

                    break;
                case 32:
                    if (!target.hasMetadata("TROLLPLUS_SLOWLY_KILL")) {
                        target.setMetadata("TROLLPLUS_SLOWLY_KILL", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-gui.slowly-kill") + " " + trollGUI.getStatus("TROLLPLUS_SLOWLY_KILL"), langConfig.getString("troll-gui.slowly-kill-description")));
                        slowlyKill(target, player);

                        break;
                    }

                    target.removeMetadata("TROLLPLUS_SLOWLY_KILL", plugin);
                    trollGUI.addItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-gui.slowly-kill") + " " + trollGUI.getStatus("TROLLPLUS_SLOWLY_KILL"), langConfig.getString("troll-gui.slowly-kill-description")));

                    break;
                case 34:
                    randomScarySound(target);

                    break;
                case 36:
                    inventoryDrop(target);


                    break;
                case 38:
                    if (target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
                        player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll.rocket-cannot-launch"));
                        target.removeMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE", plugin);

                        break;
                    }

                    rocket(target, player);

                    break;
                case 40:
                    fakeBan(target);

                    break;
                case 42:
                    fakeOp(target);

                    break;
                case 44:
                    freefall(target, player);

                    break;
            }

        } else if (plugin.getTrollBowsCommand().trollBowsGUI != null && Objects.equals(event.getClickedInventory(), plugin.getTrollBowsCommand().trollBowsGUI.getGUI())) {
            event.setCancelled(true);

            ItemStack arrow = new ItemStack(Material.ARROW, 1);

            switch (event.getSlot()) {
                default:
                    return;
                case 8:
                    player.closeInventory();

                    break;
                // Trollbows
                case 0:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW)) {
                        player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows.explosion-bow"), langConfig.getString("trollbows.explosion-bow-description")));
                        player.getInventory().addItem(arrow);

                        break;
                    }

                    player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows.explosion-bow"), langConfig.getString("trollbows.explosion-bow-description")));

                    break;
                case 1:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW)) {
                        player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows.tnt-bow"), langConfig.getString("trollbows.tnt-bow-description")));
                        player.getInventory().addItem(arrow);

                        break;
                    }

                    player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows.tnt-bow"), langConfig.getString("trollbows.tnt-bow-description")));

                    break;
                case 2:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW)) {
                        player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows.lighting-bolt-bow"), langConfig.getString("trollbows.lighting-bolt-bow-description")));
                        player.getInventory().addItem(arrow);

                        break;
                    }

                    player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows.lighting-bolt-bow"), langConfig.getString("trollbows.lighting-bolt-bow-description")));

                    break;
                case 3:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW)) {
                        player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows.silverfish-bow"), langConfig.getString("trollbows.silverfish-bow-description")));
                        player.getInventory().addItem(arrow);

                        break;
                    }

                    player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows.silverfish-bow"), langConfig.getString("trollbows.silverfish-bow-description")));

                    break;
            }
        } else if (plugin.getTrollPlusCommand().settingsGUI != null && Objects.equals(event.getClickedInventory(), plugin.getTrollPlusCommand().settingsGUI.getGUI())) {
            GUIUtil settingsGUI = plugin.getTrollPlusCommand().settingsGUI.getGUIUtil();

            event.setCancelled(true);

            switch (event.getSlot()) {
                default:
                    return;
                case 26:
                    player.closeInventory();

                    break;
                // TrollPlus settings
                case 10:
                    if (Objects.equals(plugin.getConfig().getString("language"), "zhcn")) {
                        plugin.getConfig().set("language", "custom");
                        settingsGUI.addItem(10, ItemBuilder.createItemWithLore(Material.PAPER, ChatColor.WHITE + langConfig.getString("trollsettings-language") + ChatColor.DARK_GRAY + " " + plugin.getConfig().getString("language"), langConfig.getString("trollsettings-language-description")));
                        plugin.saveConfig();

                        break;
                    } else if (Objects.equals(plugin.getConfig().getString("language"), "custom")) {
                        plugin.getConfig().set("language", "en");
                        settingsGUI.addItem(10, ItemBuilder.createItemWithLore(Material.PAPER, ChatColor.WHITE + langConfig.getString("trollsettings-language") + ChatColor.DARK_GRAY + " " + plugin.getConfig().getString("language"), langConfig.getString("trollsettings-language-description")));
                        plugin.saveConfig();

                        break;
                    }

                    plugin.getConfig().set("language", "zhcn");
                    settingsGUI.addItem(10, ItemBuilder.createItemWithLore(Material.PAPER, ChatColor.WHITE + langConfig.getString("trollsettings-language") + ChatColor.DARK_GRAY + " " + plugin.getConfig().getString("language"), langConfig.getString("trollsettings-language-description")));
                    plugin.saveConfig();

                    break;
                case 11:
                    if (plugin.getConfig().getBoolean("metrics-enabled")) {
                        plugin.getConfig().set("metrics-enabled", false);
                        settingsGUI.addItem(11, ItemBuilder.createItemWithLore(Material.BOOK, ChatColor.WHITE + langConfig.getString("trollsettings-metrics-enabled") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("metrics-enabled")), langConfig.getString("trollsettings-metrics-enabled-description")));
                        plugin.saveConfig();

                        break;
                    }

                    plugin.getConfig().set("metrics-enabled", true);
                    settingsGUI.addItem(11, ItemBuilder.createItemWithLore(Material.BOOK, ChatColor.WHITE + langConfig.getString("trollsettings-metrics-enabled") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("metrics-enabled")), langConfig.getString("trollsettings-metrics-enabled-description")));
                    plugin.saveConfig();

                    break;
                case 12:
                    if (plugin.getConfig().getBoolean("check-for-updates")) {
                        plugin.getConfig().set("check-for-updates", false);
                        settingsGUI.addItem(12, ItemBuilder.createItemWithLore(Material.GLOWSTONE, ChatColor.WHITE + langConfig.getString("trollsettings-check-for-updates") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("check-for-updates")), langConfig.getString("trollsettings-check-for-updates-description")));
                        plugin.saveConfig();

                        break;
                    }

                    plugin.getConfig().set("check-for-updates", true);
                    settingsGUI.addItem(12, ItemBuilder.createItemWithLore(Material.GLOWSTONE, ChatColor.WHITE + langConfig.getString("trollsettings-check-for-updates") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("check-for-updates")), langConfig.getString("trollsettings-check-for-updates-description")));
                    plugin.saveConfig();

                    break;
                case 13:
                    if (plugin.getConfig().getBoolean("deactivate-features-on-quit")) {
                        plugin.getConfig().set("deactivate-features-on-quit", false);
                        settingsGUI.addItem(13, ItemBuilder.createItemWithLore(Material.REDSTONE_LAMP, ChatColor.WHITE + langConfig.getString("trollsettings-deactivate-features-on-quit") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("deactivate-features-on-quit")), langConfig.getString("trollsettings-deactivate-features-on-quit-description")));
                        plugin.saveConfig();

                        break;
                    }

                    plugin.getConfig().set("deactivate-features-on-quit", true);
                    settingsGUI.addItem(13, ItemBuilder.createItemWithLore(Material.REDSTONE_LAMP, ChatColor.WHITE + langConfig.getString("trollsettings-deactivate-features-on-quit") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("deactivate-features-on-quit")), langConfig.getString("trollsettings-deactivate-features-on-quit-description")));
                    plugin.saveConfig();

                    break;
                case 14:
                    if (plugin.getConfig().getBoolean("control-teleport-back")) {
                        plugin.getConfig().set("control-teleport-back", false);
                        settingsGUI.addItem(14, ItemBuilder.createItemWithLore(Material.ENDER_PEARL, ChatColor.WHITE + langConfig.getString("trollsettings-control-teleport-back") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("control-teleport-back")), langConfig.getString("trollsettings-control-teleport-back-description")));
                        plugin.saveConfig();

                        break;
                    }

                    plugin.getConfig().set("control-teleport-back", true);
                    settingsGUI.addItem(14, ItemBuilder.createItemWithLore(Material.ENDER_PEARL, ChatColor.WHITE + langConfig.getString("trollsettings-control-teleport-back") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("control-teleport-back")), langConfig.getString("trollsettings-control-teleport-back-description")));
                    plugin.saveConfig();

                    break;
                case 15:
                    if (plugin.getConfig().getBoolean("set-fire")) {
                        plugin.getConfig().set("set-fire", false);
                        settingsGUI.addItem(15, ItemBuilder.createItemWithLore(Material.FIRE_CHARGE, ChatColor.WHITE + langConfig.getString("trollsettings-set-fire") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("set-fire")), langConfig.getString("trollsettings-set-fire-description")));
                        plugin.saveConfig();

                        break;
                    }

                    plugin.getConfig().set("set-fire", true);
                    settingsGUI.addItem(15, ItemBuilder.createItemWithLore(Material.FIRE_CHARGE, ChatColor.WHITE + langConfig.getString("trollsettings-set-fire") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("set-fire")), langConfig.getString("trollsettings-set-fire-description")));
                    plugin.saveConfig();

                    break;
                case 16:
                    if (plugin.getConfig().getBoolean("break-blocks")) {
                        plugin.getConfig().set("break-blocks", false);
                        settingsGUI.addItem(16, ItemBuilder.createItemWithLore(Material.NETHERITE_PICKAXE, ChatColor.WHITE + langConfig.getString("trollsettings-break-blocks") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("break-blocks")), langConfig.getString("trollsettings-break-blocks-description")));
                        plugin.saveConfig();

                        break;
                    }

                    plugin.getConfig().set("break-blocks", true);
                    settingsGUI.addItem(16, ItemBuilder.createItemWithLore(Material.NETHERITE_PICKAXE, ChatColor.WHITE + langConfig.getString("trollsettings-break-blocks") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("break-blocks")), langConfig.getString("trollsettings-break-blocks-description")));
                    plugin.saveConfig();

                    break;
            }
        }
    }

    // Feature hand item drop
    private void handItemDrop(Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_HAND_ITEM_DROP")) {
                    cancel();
                }

                if (target.getInventory().getItemInMainHand().getType() == Material.AIR) return;

                ItemStack item = target.getInventory().getItemInMainHand();
                ItemStack dropItem = new ItemStack(item.getType(), 1);
                dropItem.setItemMeta(item.getItemMeta());
                Item itemDrop = target.getWorld().dropItemNaturally(target.getLocation(), dropItem);
                itemDrop.setPickupDelay(40);
                int amount = item.getAmount();
                amount--;
                item.setAmount(amount);
                target.getInventory().setItemInMainHand(item);
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    // Feature Spank (Knockback)
    private void spank(Player target) {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SPANK")) {
                    cancel();
                    return;
                }
                double x = RandomUtils.nextDouble(0.1, 1) - RandomUtils.nextDouble(0.1, 1);
                double y = RandomUtils.nextDouble(0.33, 1);
                double z = RandomUtils.nextDouble(0.1, 1) - RandomUtils.nextDouble(0.1, 1);

                target.setVelocity(new Vector(x, y, z));
            }
        }.runTaskTimer(plugin, 0, 100);
    }

    // Feature spam messages
    private void spamMessages(Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SPAM_MESSAGES")) {
                    cancel();
                    return;
                }

                List<String> spamMessages = plugin.getConfig().getStringList("spam-messages");

                StringBuilder stringBuilderChat = new StringBuilder();
                StringBuilder stringBuilderTitle = new StringBuilder();
                StringBuilder stringBuilderTitle2 = new StringBuilder();

                for (Character character : spamMessages.get(RandomUtils.nextInt(0, spamMessages.size())).toCharArray()) {
                    stringBuilderChat.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.nextInt(0, 16))));
                    stringBuilderChat.append(character);
                }

                for (Character character : spamMessages.get(RandomUtils.nextInt(0, spamMessages.size())).toCharArray()) {
                    stringBuilderTitle.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.nextInt(0, 16))));
                    stringBuilderTitle.append(character);
                }

                for (Character character : spamMessages.get(RandomUtils.nextInt(0, spamMessages.size())).toCharArray()) {
                    stringBuilderTitle2.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.nextInt(0, 16))));
                    stringBuilderTitle2.append(character);
                }

                target.sendMessage(stringBuilderChat.toString());
                target.sendTitle(stringBuilderTitle.toString(), stringBuilderTitle2.toString(), 3, 10, 3);
            }
        }.runTaskTimer(plugin, 0, 10);
    }

    // Feature spam sounds
    private void spamSounds(Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SPAM_SOUNDS")) {
                    cancel();
                    return;
                }

                List<Sound> sounds = Arrays.asList(Sound.ENTITY_FOX_BITE, Sound.ENTITY_VILLAGER_YES, Sound.ENTITY_PLAYER_HURT, Sound.ENTITY_CHICKEN_DEATH, Sound.ENTITY_WOLF_GROWL, Sound.BLOCK_BELL_USE, Sound.BLOCK_ANVIL_FALL, Sound.ENTITY_WITHER_DEATH, Sound.ENTITY_WOLF_DEATH, Sound.BLOCK_IRON_DOOR_CLOSE, Sound.BLOCK_CHEST_OPEN, Sound.ENTITY_PIG_HURT, Sound.BLOCK_GRAVEL_BREAK, Sound.ENTITY_SHULKER_BULLET_HIT, Sound.ENTITY_ILLUSIONER_DEATH, Sound.BLOCK_PORTAL_AMBIENT, Sound.BLOCK_CANDLE_BREAK, Sound.ENTITY_BAT_HURT, Sound.ITEM_AXE_WAX_OFF);

                target.playSound(target.getLocation(), sounds.get(RandomUtils.nextInt(0, sounds.size())), RandomUtils.nextInt(), RandomUtils.nextInt());
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    // Feature falling anvil
    private void fallingAnvil(Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_FALLING_ANVIL")) {
                    cancel();
                    return;
                }

                Location loc = target.getLocation();
                loc.setY(loc.getY() + 3);

                if (loc.getBlock().getType().equals(Material.AIR)) loc.getBlock().setType(Material.DAMAGED_ANVIL);
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    // Feature tnt track
    private void tntTrack(Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_TNT_TRACK")) {
                    cancel();
                    return;
                }

                TNTPrimed tnt = target.getWorld().spawn(target.getLocation(), TNTPrimed.class);
                tnt.setFuseTicks(80);
                tnt.setMetadata("TROLLPLUS_TNT", new FixedMetadataValue(plugin, tnt));
                tnt.getWorld().playSound(target.getLocation(), Sound.ENTITY_TNT_PRIMED, 20, 1);
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    // Feature mob spawner
    private void mobSpawner(Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_MOB_SPAWNER")) {
                    cancel();
                    return;
                }

                List<EntityType> mobs = Arrays.asList(EntityType.DROWNED, EntityType.ENDERMITE, EntityType.HOGLIN, EntityType.HUSK, EntityType.MAGMA_CUBE, EntityType.PHANTOM, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.STRAY, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.ZOGLIN, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ILLUSIONER);

                target.getWorld().spawnEntity(target.getLocation(), mobs.get(RandomUtils.nextInt(0, mobs.size()))).setGlowing(true);
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    // Feature slowly kill
    private void slowlyKill(Player target, Player player) {
        new BukkitRunnable() {
            @Override

            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SLOWLY_KILL")) {
                    cancel();
                    return;
                }

                if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) {
                    FileConfiguration langConfig = plugin.getLanguageConfig().getConfig();

                    player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll.slowly-kill-not-available"));
                    target.removeMetadata("TROLLPLUS_SLOWLY_KILL", plugin);
                    GUIUtil trollGUI = plugin.getTrollCommand().trollGUI.getGUIUtil();
                    trollGUI.addItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-gui.slowly-kill") + " " + trollGUI.getStatus("TROLLPLUS_SLOWLY_KILL"), langConfig.getString("troll-gui.slowly-kill-description")));
                    cancel();
                    return;
                }

                target.damage(1);
            }
        }.runTaskTimer(plugin, 0, 60);
    }

    // Feature random scary sound
    private void randomScarySound(Player target) {
        List<Sound> sounds = Arrays.asList(Sound.AMBIENT_BASALT_DELTAS_MOOD, Sound.AMBIENT_CAVE, Sound.AMBIENT_CRIMSON_FOREST_MOOD, Sound.AMBIENT_NETHER_WASTES_MOOD, Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, Sound.AMBIENT_WARPED_FOREST_MOOD, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE);

        target.playSound(target.getLocation(), sounds.get(RandomUtils.nextInt(0, sounds.size())), 200, 1);
    }

    // Inventory Drop
    private void inventoryDrop(Player target) {
        for (ItemStack inventoryItems : target.getInventory().getContents()) {
            if (inventoryItems != null) {
                Item inventoryItemsDrop = target.getWorld().dropItemNaturally(target.getLocation(), inventoryItems);
                inventoryItemsDrop.setPickupDelay(40);
            }
        }

        target.getInventory().clear();
    }

    // Feature rocket
    private void rocket(Player target, Player player) {
        target.setMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE", new FixedMetadataValue(plugin, target.getName()));

        boolean targetAllowedToFlight = false;
        if (!target.getAllowFlight()) {
            target.setAllowFlight(true);
        } else
            targetAllowedToFlight = true;

        target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, target.getLocation(), 1);
        Particle[] particles = new Particle[]{Particle.FIREWORKS_SPARK, Particle.LAVA, Particle.FLAME};
        for (Particle particle : particles) {
            target.getWorld().spawnParticle(particle, target.getLocation(), 25);
        }

        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20, 1);

        Boolean finalTargetAllowToFlight = targetAllowedToFlight;
        new BukkitRunnable() {
            byte rocket = 0;

            @Override
            public void run() {
                if (rocket < plugin.getConfig().getInt("rocket-charges")) {
                    if (target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
                        if (!finalTargetAllowToFlight) target.setAllowFlight(false);

                        player.sendMessage(Constants.PLUGIN_PREFIX + plugin.getLanguageConfig().getConfig().getString("troll.rocket-launch-stopped"));

                        cancel();
                        return;
                    }

                    target.setVelocity(target.getVelocity().setY(20));
                    rocket++;
                } else {
                    if (!finalTargetAllowToFlight) target.setAllowFlight(false);

                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    // Feature fake ban
    private void fakeBan(Player target) {
        String fakeBanMessagePlayer = plugin.getConfig().getString("fake-ban-message-player", "");

        if (!fakeBanMessagePlayer.isEmpty()) target.kickPlayer(fakeBanMessagePlayer);

        if (plugin.getConfig().getBoolean("fake-ban-message-broadcast-enabled", true)) {
            String fakeBanMessageBroadcast = plugin.getConfig().getString("fake-ban-message-broadcast", "");

            if (!fakeBanMessageBroadcast.isEmpty()) {
                String fakeBanMessageBroadcastReplace = fakeBanMessageBroadcast.replace("[player]", target.getName());
                Bukkit.broadcastMessage(fakeBanMessageBroadcastReplace);
            }
        }
    }

    // Feature fake op
    private void fakeOp(Player target) {
        String fakeOpMessage = plugin.getConfig().getString("fake-op-message", "");

        if (!plugin.getConfig().getBoolean("fake-op-message-broadcast-enabled", true)) {

            if (!fakeOpMessage.isEmpty()) {
                String fakeOpMessageReplace = fakeOpMessage.replace("[player]", target.getName());
                target.sendMessage(ChatColor.GRAY + fakeOpMessageReplace);
            }
            return;
        }

        if (!fakeOpMessage.isEmpty()) {
            String fakeOpMessageReplace = fakeOpMessage.replace("[player]", target.getName());
            Bukkit.broadcastMessage(ChatColor.GRAY + fakeOpMessageReplace);
        }
    }

    // Feature freefall
    private void freefall(Player target, Player player) {
        Location loc = target.getLocation();

        for (int i = 0; i < plugin.getConfig().getInt("freefall-height"); i++) {
            if (loc.getBlock().getType().equals(Material.AIR)) {
                loc.setY(loc.getY() + 1);
            } else {
                player.sendMessage(Objects.requireNonNull(plugin.getLanguageConfig().getConfig().getString("troll.freefall-cannot-fall")));
                return;
            }
        }

        if (target.isFlying())
            target.setFlying(false);

        target.teleport(loc);
    }
}