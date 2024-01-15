/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.commands;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.ConfigUtil;
import de.gaming12846.trollplus.utils.Constants;
import de.gaming12846.trollplus.utils.GUIUtil;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrollCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIUtil trollGUI;

    public TrollCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigUtil langConfig = plugin.getLanguageConfig();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getConfig().getString("no-console"));
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission(Constants.PERMISSION_TROLL)) {
            player.sendMessage(ChatColor.RED + langConfig.getString("no-permission"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("invalid-syntax") + " " + ChatColor.RESET + langConfig.getString("invalid-syntax-use") + label + " <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            String playerNotOnlineReplace = Constants.PLUGIN_PREFIX + langConfig.getString("troll.player-not-online");
            player.sendMessage(playerNotOnlineReplace.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[0] + ChatColor.RESET));
            return true;
        }

        if (plugin.getBlocklistConfig().getConfig().contains(target.getUniqueId().toString())) {
            String playerIsImmuneReplace = Constants.PLUGIN_PREFIX + langConfig.getString("troll.player-is-immune");
            player.sendMessage(playerIsImmuneReplace.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[0] + ChatColor.RESET));
            return true;
        }

        // Create troll GUI
        trollGUI = new GUIUtil(langConfig.getString("troll-gui.title") + " " + ChatColor.GOLD + ChatColor.BOLD + target.getName(), 54, target, plugin);

        // Add features
        trollGUI.addItem(4, ItemBuilder.createSkull(ChatColor.GOLD + target.getName(), target.getPlayer()));
        trollGUI.addItemWithLore(45, Material.POTION, ChatColor.WHITE + langConfig.getString("troll-gui.vanish") + " " + trollGUI.getStatus("TROLLPLUS_VANISH"), langConfig.getString("troll-gui.vanish-description"));
        trollGUI.addItemWithLore(46, Material.ENDER_PEARL, ChatColor.WHITE + langConfig.getString("troll-gui.teleport"), langConfig.getString("troll-gui.teleport-description"));
        trollGUI.addItemWithLore(47, Material.WITHER_SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-gui.kill"), langConfig.getString("troll-gui.kill-description"));
        trollGUI.addItemWithLore(49, Material.CHORUS_FRUIT, ChatColor.WHITE + langConfig.getString("troll-gui.random-troll"), langConfig.getString("troll-gui.random-troll-description"));
        trollGUI.addItemWithLore(51, Material.CHEST, ChatColor.WHITE + langConfig.getString("troll-gui.invsee"), langConfig.getString("troll-gui.invsee-description"));
        trollGUI.addItemWithLore(52, Material.ENDER_CHEST, ChatColor.WHITE + langConfig.getString("troll-gui.invsee-ender-chest"), langConfig.getString("troll-gui.invsee-ender-chest-description"));
        trollGUI.addItemWithLore(53, Material.BARRIER, ChatColor.RED + langConfig.getString("troll-gui.close"), langConfig.getString("troll-gui.close-description"));

        trollGUI.addItemWithLore(10, Material.ICE, ChatColor.WHITE + langConfig.getString("troll-gui.freeze") + " " + trollGUI.getStatus("TROLLPLUS_FREEZE"), langConfig.getString("troll-gui.freeze-description"));
        trollGUI.addItemWithLore(12, Material.SHEARS, ChatColor.WHITE + langConfig.getString("troll-gui.hand-item-drop") + " " + trollGUI.getStatus("TROLLPLUS_HAND_ITEM_DROP"), langConfig.getString("troll-gui.hand-item-drop-description"));
        trollGUI.addItemWithLore(14, Material.LEAD, ChatColor.WHITE + langConfig.getString("troll-gui.control") + " " + trollGUI.getStatus("TROLLPLUS_CONTROL_TARGET"), langConfig.getString("troll-gui.control-description"));
        trollGUI.addItemWithLore(16, Material.COMPASS, ChatColor.WHITE + langConfig.getString("troll-gui.flip-backwards") + " " + trollGUI.getStatus("TROLLPLUS_FLIP_BEHIND"), langConfig.getString("troll-gui.flip-backwards-description"));
        trollGUI.addItemWithLore(18, Material.SNOWBALL, ChatColor.WHITE + langConfig.getString("troll-gui.spank") + " " + trollGUI.getStatus("TROLLPLUS_SPANK"), langConfig.getString("troll-gui.spank-description"));
        trollGUI.addItemWithLore(20, Material.WRITABLE_BOOK, ChatColor.WHITE + langConfig.getString("troll-gui.spam-messages") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_MESSAGES"), langConfig.getString("troll-gui.spam-messages-description"));
        trollGUI.addItemWithLore(22, Material.NOTE_BLOCK, ChatColor.WHITE + langConfig.getString("troll-gui.spam-sounds") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_SOUNDS"), langConfig.getString("troll-gui.spam-sounds-description"));
        trollGUI.addItemWithLore(24, Material.TRIPWIRE_HOOK, ChatColor.WHITE + langConfig.getString("troll-gui.semi-ban") + " " + trollGUI.getStatus("TROLLPLUS_SEMI_BAN"), langConfig.getString("troll-gui.semi-ban-description"));
        trollGUI.addItemWithLore(26, Material.ANVIL, ChatColor.WHITE + langConfig.getString("troll-gui.falling-anvils") + " " + trollGUI.getStatus("TROLLPLUS_FALLING_ANVILS"), langConfig.getString("troll-gui.falling-anvils-description"));
        trollGUI.addItemWithLore(28, Material.TNT, ChatColor.WHITE + langConfig.getString("troll-gui.tnt-track") + " " + trollGUI.getStatus("TROLLPLUS_TNT_TRACK"), langConfig.getString("troll-gui.tnt-track-description"));
        trollGUI.addItemWithLore(30, Material.SPAWNER, ChatColor.WHITE + langConfig.getString("troll-gui.mob-spawner") + " " + trollGUI.getStatus("TROLLPLUS_MOB_SPAWNER"), langConfig.getString("troll-gui.mob-spawner-description"));
        trollGUI.addItemWithLore(32, Material.SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-gui.slowly-kill") + " " + trollGUI.getStatus("TROLLPLUS_SLOWLY_KILL"), langConfig.getString("troll-gui.slowly-kill-description"));
        trollGUI.addItemWithLore(34, Material.MUSIC_DISC_11, ChatColor.WHITE + langConfig.getString("troll-gui.random-scary-sound"), langConfig.getString("troll-gui.random-scary-sound-description"));
        trollGUI.addItemWithLore(36, Material.EGG, ChatColor.WHITE + langConfig.getString("troll-gui.inventory-drop"), langConfig.getString("troll-gui.inventory-drop-description"));
        trollGUI.addItemWithLore(38, Material.FIREWORK_ROCKET, ChatColor.WHITE + langConfig.getString("troll-gui.rocket"), langConfig.getString("troll-gui.rocket-description"));
        trollGUI.addItemWithLore(40, Material.PAPER, ChatColor.WHITE + langConfig.getString("troll-gui.fake-ban"), langConfig.getString("troll-gui.fake-ban-description"));
        trollGUI.addItemWithLore(42, Material.ENCHANTED_GOLDEN_APPLE, ChatColor.WHITE + langConfig.getString("troll-gui.fake-op"), langConfig.getString("troll-gui.fake-op-description"));
        trollGUI.addItemWithLore(44, Material.FEATHER, ChatColor.WHITE + langConfig.getString("troll-gui.freefall"), langConfig.getString("troll-gui.freefall-description"));

        // Placeholders
        byte[] placeholderArray = new byte[]{0, 1, 2, 3, 5, 6, 7, 8, 48, 50};
        for (int slot : placeholderArray) {
            trollGUI.addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", langConfig.getString("guis.placeholder-description")));
        }

        // Placeholders
        byte[] placeholderArray2 = new byte[]{9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43};
        for (int slot : placeholderArray2) {
            trollGUI.addItem(slot, ItemBuilder.createItemWithLore(Material.GRAY_STAINED_GLASS_PANE, " ", langConfig.getString("guis.placeholder-description")));
        }

        player.openInventory(trollGUI.getGUI());

        return true;
    }
}