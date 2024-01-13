/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import org.bukkit.ChatColor;

public class Constants {
    // Plugin presets
    public static final String PLUGIN_PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + ChatColor.BOLD + "TrollPlus" + ChatColor.RESET + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " ";

    // Permission nodes
    public static final String PERMISSION_VERSION = "trollplus.version";
    public static final String PERMISSION_RELOAD = "trollplus.reload";
    public static final String PERMISSION_BLOCKLIST_ADD = "trollplus.blocklist.add";
    public static final String PERMISSION_BLOCKLIST_REMOVE = "trollplus.blocklist.remove";
    public static final String PERMISSION_SETTINGS = "trollplus.settings";
    public static final String PERMISSION_TROLL = "trollplus.troll";
    public static final String PERMISSION_TROLLBOWS = "trollplus.bows";
}