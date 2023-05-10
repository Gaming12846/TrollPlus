/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Constants {
    public Constants(TrollPlus plugin) {
        PLUGIN_NO_CONSOLE = " " + plugin.getLanguageConfig().getConfig().getString("no-console");
        String invalidSyntaxReplace = PLUGIN_PREFIX + ChatColor.RED + plugin.getLanguageConfig().getConfig().getString("invalid-syntax");
        PLUGIN_INVALID_SYNTAX = invalidSyntaxReplace.replace("[color-code]", ChatColor.RESET.toString());
    }

    // Plugin info messages presets
    public static final String PLUGIN_PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + ChatColor.BOLD + "TrollPlus" + ChatColor.RESET + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " ";
    public static final String PLUGIN_CONSOLE_PREFIX = "[TrollPlus] ";
    public static String PLUGIN_NO_PERMISSION;
    public static String PLUGIN_NO_CONSOLE;
    public static String PLUGIN_INVALID_SYNTAX;

    // Permission nodes
    public static final String PERMISSION_ALL = "trollplus.*";
    public static final String PERMISSION_RELOAD = "trollplus.reload";
    public static final String PERMISSION_BLOCKLIST_ALL = "trollplus.blocklist.*";
    public static final String PERMISSION_BLOCKLIST_ADD = "trollplus.blocklist.add";
    public static final String PERMISSION_BLOCKLIST_REMOVE = "trollplus.blocklist.remove";
    public static final String PERMISSION_TROLL = "trollplus.troll";
    public static final String PERMISSION_TROLLBOWS = "trollplus.bows";
    public static final String PERMISSION_IGNORE_IMMUNE = "trollplus.ignoreimmune";

    // Inventorys
    public static Inventory TROLLBOWS_GUI;

    // Feature control
    public static Boolean CONTROL_MESSAGE_BOOLEAN = false;
    public static String CONTROL_MESSAGE = null;
    public static Location CONTROL_PLAYER_LOCATION;
    public static ItemStack[] CONTROL_PLAYER_INVENTORY;
    public static ItemStack[] CONTROL_PLAYER_ARMOR;
    public static ItemStack CONTROL_PLAYER_OFF_HAND_ITEM;
}