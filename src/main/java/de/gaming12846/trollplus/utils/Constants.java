/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.ChatColor;

public class Constants {
    // Plugin presets
    public static final String PLUGIN_PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + ChatColor.BOLD + "TrollPlus" + ChatColor.RESET + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " ";
    public static final String PLUGIN_CONSOLE_PREFIX = "[TrollPlus] ";

    // Permission nodes
    public static final String PERMISSION_RELOAD = "trollplus.reload";
    public static final String PERMISSION_BLOCKLIST_ADD = "trollplus.blocklist.add";
    public static final String PERMISSION_BLOCKLIST_REMOVE = "trollplus.blocklist.remove";
    public static final String PERMISSION_TROLL = "trollplus.troll";
    public static final String PERMISSION_TROLLBOWS = "trollplus.bows";
    public static String PLUGIN_NO_PERMISSION;
    public static String PLUGIN_NO_CONSOLE;
    public static String PLUGIN_INVALID_SYNTAX;

    // Plugin info messages presets
    public Constants(TrollPlus plugin) {
        PLUGIN_NO_PERMISSION = ChatColor.RED + plugin.getLanguageConfig().getConfig().getString("no-permission");
        PLUGIN_NO_CONSOLE = " " + plugin.getLanguageConfig().getConfig().getString("no-console");
        PLUGIN_INVALID_SYNTAX = PLUGIN_PREFIX + ChatColor.RED + plugin.getLanguageConfig().getConfig().getString("invalid-syntax") + " " + ChatColor.RESET + plugin.getLanguageConfig().getConfig().getString("invalid-syntax-use");
    }
}