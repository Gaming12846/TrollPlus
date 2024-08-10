/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import org.bukkit.ChatColor;

// Contains constant values used throughout the TrollPlus plugin
public class Constants {
    // The prefix used in plugin messages
    public static final String PLUGIN_PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + ChatColor.BOLD + "TrollPlus" + ChatColor.RESET + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " ";

    // Permission nodes
    public static final String PERMISSION_VERSION = "trollplus.version"; // Permission node for the version command
    public static final String PERMISSION_RELOAD = "trollplus.reload"; // Permission node for reload command
    public static final String PERMISSION_BLOCKLIST_ADD = "trollplus.blocklist.add"; // Permission node for blocklist add subcommand
    public static final String PERMISSION_BLOCKLIST_REMOVE = "trollplus.blocklist.remove"; // Permission node for blocklist remove subcommand
    public static final String PERMISSION_SETTINGS = "trollplus.settings"; // Permission node for settings subcommand
    public static final String PERMISSION_TROLL = "trollplus.troll"; // Permission node for troll command
    public static final String PERMISSION_TROLLBOWS = "trollplus.bows"; // Permission node for trollbows command
}