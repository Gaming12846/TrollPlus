/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.commands;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.ConfigHelper;
import de.gaming12846.trollplus.utils.GUIHelper;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.gaming12846.trollplus.TrollPlus.PLUGIN_PREFIX;

// Handles the "troll" command which allows a player to troll another player using various options
public class TrollCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIHelper GUIHelperTroll;

    // Constructor for the TrollCommand
    public TrollCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Executes the command logic when a player uses the "/troll" command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigHelper configHelperLanguage = plugin.getConfigHelperLanguage();

        // Check if the command sender is not a player
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PLUGIN_PREFIX + configHelperLanguage.getString("no-console"));
            return true;
        }

        // Check if the player has the required permission
        if (!player.hasPermission("trollplus.troll")) {
            player.sendMessage(ChatColor.RED + configHelperLanguage.getString("no-permission"));
            return true;
        }

        // Check if the command syntax is correct
        if (args.length != 1) {
            player.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("invalid-syntax") + " " + ChatColor.RESET + configHelperLanguage.getString("invalid-syntax-use") + label + " <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        // Check if the target player is online
        if (target == null) {
            player.sendMessage((PLUGIN_PREFIX + configHelperLanguage.getString("troll.player-not-online")).replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[0] + ChatColor.RESET));
            return true;
        }

        // Check if the target player is immune to trolling
        if (plugin.getConfigHelperBlocklist().contains(target.getUniqueId().toString())) {
            player.sendMessage((PLUGIN_PREFIX + configHelperLanguage.getString("troll.player-is-immune")).replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[0] + ChatColor.RESET));
            return true;
        }

        // Creates the Troll GUI for the player
        createTrollGUI(target, configHelperLanguage);

        // Open the Troll GUI for the player to select trolling options
        player.openInventory(GUIHelperTroll.getGUI());
        return true;
    }

    // Creates the Troll GUI for the player
    private void createTrollGUI(Player target, ConfigHelper configHelperLanguage) {
        GUIHelperTroll = new GUIHelper(ChatColor.BLACK + configHelperLanguage.getString("troll-gui.title") + " " + ChatColor.DARK_RED + target.getName(), 54, target, plugin);

        // Add the available trolling options to the GUI
        GUIHelperTroll.addItem(4, ItemBuilder.createSkull(ChatColor.GOLD + target.getName(), target.getPlayer()));

        GUIHelperTroll.addItemWithLore(11, Material.BLUE_ICE, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.freeze") + " " + GUIHelperTroll.getStatus("TROLLPLUS_FREEZE"), configHelperLanguage.getString("troll-gui.freeze-description"));
        GUIHelperTroll.addItemWithLore(12, Material.SHEARS, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.hand-item-drop") + " " + GUIHelperTroll.getStatus("TROLLPLUS_HAND_ITEM_DROP"), configHelperLanguage.getString("troll-gui.hand-item-drop-description"));
        GUIHelperTroll.addItemWithLore(13, Material.LEAD, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.control") + " " + GUIHelperTroll.getStatus("TROLLPLUS_CONTROL_TARGET"), configHelperLanguage.getString("troll-gui.control-description"));
        GUIHelperTroll.addItemWithLore(14, Material.COMPASS, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.flip-backwards") + " " + GUIHelperTroll.getStatus("TROLLPLUS_FLIP_BEHIND"), configHelperLanguage.getString("troll-gui.flip-backwards-description"));
        GUIHelperTroll.addItemWithLore(15, Material.SLIME_BALL, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.spank") + " " + GUIHelperTroll.getStatus("TROLLPLUS_SPANK"), configHelperLanguage.getString("troll-gui.spank-description"));
        GUIHelperTroll.addItemWithLore(19, Material.WRITABLE_BOOK, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.spam-messages") + " " + GUIHelperTroll.getStatus("TROLLPLUS_SPAM_MESSAGES"), configHelperLanguage.getString("troll-gui.spam-messages-description"));
        GUIHelperTroll.addItemWithLore(20, Material.NOTE_BLOCK, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.spam-sounds") + " " + GUIHelperTroll.getStatus("TROLLPLUS_SPAM_SOUNDS"), configHelperLanguage.getString("troll-gui.spam-sounds-description"));
        GUIHelperTroll.addItemWithLore(21, Material.TRIPWIRE_HOOK, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.semi-ban") + " " + GUIHelperTroll.getStatus("TROLLPLUS_SEMI_BAN"), configHelperLanguage.getString("troll-gui.semi-ban-description"));
        GUIHelperTroll.addItemWithLore(22, Material.ANVIL, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.falling-anvils") + " " + GUIHelperTroll.getStatus("TROLLPLUS_FALLING_ANVILS"), configHelperLanguage.getString("troll-gui.falling-anvils-description"));
        GUIHelperTroll.addItemWithLore(23, Material.TNT, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.tnt-track") + " " + GUIHelperTroll.getStatus("TROLLPLUS_TNT_TRACK"), configHelperLanguage.getString("troll-gui.tnt-track-description"));
        GUIHelperTroll.addItemWithLore(24, Material.SPAWNER, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.mob-spawner") + " " + GUIHelperTroll.getStatus("TROLLPLUS_MOB_SPAWNER"), configHelperLanguage.getString("troll-gui.mob-spawner-description"));
        GUIHelperTroll.addItemWithLore(25, Material.SKELETON_SKULL, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.slowly-kill") + " " + GUIHelperTroll.getStatus("TROLLPLUS_SLOWLY_KILL"), configHelperLanguage.getString("troll-gui.slowly-kill-description"));
        GUIHelperTroll.addItemWithLore(33, Material.EGG, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.inventory-drop"), configHelperLanguage.getString("troll-gui.inventory-drop-description"));
        GUIHelperTroll.addItemWithLore(34, Material.BARREL, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.inventory-shuffle"), configHelperLanguage.getString("troll-gui.inventory-shuffle-description"));
        GUIHelperTroll.addItemWithLore(38, Material.MUSIC_DISC_11, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.random-scary-sound"), configHelperLanguage.getString("troll-gui.random-scary-sound-description"));
        GUIHelperTroll.addItemWithLore(39, Material.FIREWORK_ROCKET, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.rocket"), configHelperLanguage.getString("troll-gui.rocket-description"));
        GUIHelperTroll.addItemWithLore(40, Material.FEATHER, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.freefall"), configHelperLanguage.getString("troll-gui.freefall-description"));
        GUIHelperTroll.addItemWithLore(41, Material.PAPER, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.fake-ban"), configHelperLanguage.getString("troll-gui.fake-ban-description"));
        GUIHelperTroll.addItemWithLore(42, Material.ENCHANTED_GOLDEN_APPLE, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.fake-op"), configHelperLanguage.getString("troll-gui.fake-op-description"));

        GUIHelperTroll.addItemWithLore(18, Material.ENDER_PEARL, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.teleport"), configHelperLanguage.getString("troll-gui.teleport-description"));
        GUIHelperTroll.addItemWithLore(26, Material.CHEST, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.invsee"), configHelperLanguage.getString("troll-gui.invsee-description"));
        GUIHelperTroll.addItemWithLore(27, Material.WITHER_SKELETON_SKULL, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.kill"), configHelperLanguage.getString("troll-gui.kill-description"));
        GUIHelperTroll.addItemWithLore(35, Material.ENDER_CHEST, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.invsee-ender-chest"), configHelperLanguage.getString("troll-gui.invsee-ender-chest-description"));
        GUIHelperTroll.addItemWithLore(48, Material.POTION, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.vanish") + " " + GUIHelperTroll.getStatus("TROLLPLUS_VANISH"), configHelperLanguage.getString("troll-gui.vanish-description"));
        GUIHelperTroll.addItemWithLore(50, Material.CHORUS_FRUIT, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.random-troll"), configHelperLanguage.getString("troll-gui.random-troll-description"));

        // Add placeholders to the GUI
        final byte[] placeholderSlots = {0, 1, 2, 3, 5, 6, 7, 8, 45, 46, 49, 52, 53};
        for (int slot : placeholderSlots) {
            GUIHelperTroll.addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", configHelperLanguage.getString("guis.placeholder.description")));
        }
        final byte[] placeholderSlots1 = {3, 5, 9, 17, 36, 44, 47, 51};
        for (int slot : placeholderSlots1) {
            GUIHelperTroll.addItem(slot, ItemBuilder.createItemWithLore(Material.WHITE_STAINED_GLASS_PANE, " ", configHelperLanguage.getString("guis.placeholder.description")));
        }
    }
}