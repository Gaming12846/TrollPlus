/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.commands;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.Constants;
import de.gaming12846.trollplus.utils.GUIUtil;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class TrollCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIUtil trollGUI;

    public TrollCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + label + Constants.PLUGIN_NO_CONSOLE);
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission(Constants.PERMISSION_TROLL)) {
            player.sendMessage(Constants.PLUGIN_NO_PERMISSION);
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(Constants.PLUGIN_INVALID_SYNTAX + label + " <player>");
            return true;
        }

        FileConfiguration langConfig = plugin.getLanguageConfig().getConfig();

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            String playerNotOnlineReplace = Constants.PLUGIN_PREFIX + plugin.getLanguageConfig().getConfig().getString("troll-player-not-online");
            player.sendMessage(playerNotOnlineReplace.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[0] + ChatColor.RESET));
            return true;
        }

        if (plugin.getBlocklistConfig().getConfig().contains(target.getUniqueId().toString())) {
            String playerIsImmuneReplace = Constants.PLUGIN_PREFIX + plugin.getLanguageConfig().getConfig().getString("troll-player-is-immune");
            player.sendMessage(playerIsImmuneReplace.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[0] + ChatColor.RESET));
            return true;
        }

        // Create troll GUI
        trollGUI = new GUIUtil("Troll " + ChatColor.GOLD + ChatColor.BOLD + target.getName(), 54, target, plugin);

        // Add features
        trollGUI.addItem(4, ItemBuilder.createSkull(ChatColor.GOLD + target.getName(), target.getPlayer()));
        trollGUI.addItem(47, ItemBuilder.createItemWithLore(Material.POTION, ChatColor.WHITE + langConfig.getString("troll-vanish") + " " + trollGUI.getStatus("TROLLPLUS_VANISH"), langConfig.getString("troll-vanish-description")));
        trollGUI.addItem(48, ItemBuilder.createItemWithLore(Material.ENDER_PEARL, ChatColor.WHITE + langConfig.getString("troll-teleport"), langConfig.getString("troll-teleport-description")));
        trollGUI.addItem(49, ItemBuilder.createItemWithLore(Material.WITHER_SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-kill"), langConfig.getString("troll-kill-description")));
        trollGUI.addItem(50, ItemBuilder.createItemWithLore(Material.CHEST, ChatColor.WHITE + langConfig.getString("troll-invsee"), langConfig.getString("troll-invsee-description")));
        trollGUI.addItem(51, ItemBuilder.createItemWithLore(Material.ENDER_CHEST, ChatColor.WHITE + langConfig.getString("troll-invsee-ender-chest"), langConfig.getString("troll-invsee-ender-chest-description")));
        trollGUI.addItem(53, ItemBuilder.createItemWithLore(Material.BARRIER, ChatColor.RED + langConfig.getString("troll-close"), langConfig.getString("troll-close-description")));
        trollGUI.addItem(10, ItemBuilder.createItemWithLore(Material.ICE, ChatColor.WHITE + langConfig.getString("troll-freeze") + " " + trollGUI.getStatus("TROLLPLUS_FREEZE"), langConfig.getString("troll-freeze-description")));
        trollGUI.addItem(12, ItemBuilder.createItemWithLore(Material.SHEARS, ChatColor.WHITE + langConfig.getString("troll-hand-item-drop") + " " + trollGUI.getStatus("TROLLPLUS_HAND_ITEM_DROP"), langConfig.getString("troll-hand-item-drop-description")));
        trollGUI.addItem(14, ItemBuilder.createItemWithLore(Material.LEAD, ChatColor.WHITE + langConfig.getString("troll-control") + " " + trollGUI.getStatus("TROLLPLUS_CONTROL_TARGET"), langConfig.getString("troll-control-description")));
        trollGUI.addItem(16, ItemBuilder.createItemWithLore(Material.COMPASS, ChatColor.WHITE + langConfig.getString("troll-flip-backwards") + " " + trollGUI.getStatus("TROLLPLUS_FLIP_BEHIND"), langConfig.getString("troll-flip-backwards-description")));
        trollGUI.addItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + langConfig.getString("troll-spam-messages") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_MESSAGES"), langConfig.getString("troll-spam-messages-description")));
        trollGUI.addItem(22, ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, ChatColor.WHITE + langConfig.getString("troll-spam-sounds") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_SOUNDS"), langConfig.getString("troll-spam-sounds-description")));
        trollGUI.addItem(24, ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, ChatColor.WHITE + langConfig.getString("troll-semi-ban") + " " + trollGUI.getStatus("TROLLPLUS_SEMI_BAN"), langConfig.getString("troll-semi-ban-description")));
        trollGUI.addItem(28, ItemBuilder.createItemWithLore(Material.TNT, ChatColor.WHITE + langConfig.getString("troll-tnt-track") + " " + trollGUI.getStatus("TROLLPLUS_TNT_TRACK"), langConfig.getString("troll-tnt-track-description")));
        trollGUI.addItem(30, ItemBuilder.createItemWithLore(Material.SPAWNER, ChatColor.WHITE + langConfig.getString("troll-mob-spawner") + " " + trollGUI.getStatus("TROLLPLUS_MOB_SPAWNER"), langConfig.getString("troll-mob-spawner-description")));
        trollGUI.addItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-slowly-kill") + " " + trollGUI.getStatus("TROLLPLUS_SLOWLY_KILL"), langConfig.getString("troll-slowly-kill-description")));
        trollGUI.addItem(34, ItemBuilder.createItemWithLore(Material.MUSIC_DISC_11, ChatColor.WHITE + langConfig.getString("troll-random-scary-sound"), langConfig.getString("troll-random-scary-sound-description")));
        trollGUI.addItem(36, ItemBuilder.createItemWithLore(Material.EGG, ChatColor.WHITE + langConfig.getString("troll-inventory-drop"), langConfig.getString("troll-inventory-drop-description")));
        trollGUI.addItem(38, ItemBuilder.createItemWithLore(Material.FIREWORK_ROCKET, ChatColor.WHITE + langConfig.getString("troll-rocket"), langConfig.getString("troll-rocket-description")));
        trollGUI.addItem(40, ItemBuilder.createItemWithLore(Material.PAPER, ChatColor.WHITE + langConfig.getString("troll-fake-ban"), langConfig.getString("troll-fake-ban-description")));
        trollGUI.addItem(42, ItemBuilder.createItemWithLore(Material.ENCHANTED_GOLDEN_APPLE, ChatColor.WHITE + langConfig.getString("troll-fake-op"), langConfig.getString("troll-fake-op-description")));

        // Placeholders
        byte[] placeholderArray = new byte[]{0, 1, 2, 3, 5, 6, 7, 8, 45, 46, 52};
        for (int slot : placeholderArray) {
            trollGUI.addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", langConfig.getString("gui-placeholder-description")));
        }

        // Placeholders
        byte[] placeholderArray2 = new byte[]{9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43};
        for (int slot : placeholderArray2) {
            trollGUI.addItem(slot, ItemBuilder.createItemWithLore(Material.GRAY_STAINED_GLASS_PANE, " ", langConfig.getString("gui-placeholder-description")));
        }

        // Placeholders coming soon
        byte[] placeholderArray3 = new byte[]{18, 26, 44};
        for (int slot : placeholderArray3) {
            trollGUI.addItem(slot, ItemBuilder.createItemWithLore(Material.REDSTONE, langConfig.getString("gui-placeholder-coming-soon"), langConfig.getString("gui-placeholder-coming-soon-description")));
        }

        player.openInventory(trollGUI.getGUI());

        return true;
    }
}