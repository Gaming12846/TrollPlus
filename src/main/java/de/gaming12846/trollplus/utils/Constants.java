/*
 *
 *  * This file is part of TrollPlus.
 *  * Copyright (C) 2023 Gaming12846
 *
 */

package de.gaming12846.trollplus.utils;

import de.gaming12846.trollplus.TrollPlus;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Constants {
    public Constants(TrollPlus plugin) {
        PLUGIN_NO_CONSOLE = " " + plugin.getLanguageConfig().getConfig().getString("no-console");
        String invalidSyntaxReplace = PLUGIN_PREFIX + ChatColor.RED + plugin.getLanguageConfig().getConfig().getString("invalid-syntax");
        PLUGIN_INVALID_SYNTAX = invalidSyntaxReplace.replace("[colour-code]", ChatColor.RESET.toString());
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

    // Target player
    public static Player TARGET;

    // Inventorys
    public static Inventory TROLL_GUI;
    public static Inventory TROLLBOWS_GUI;

    // Features status
    public static Integer STATUS_VANISH = 0;
    public static boolean STATUS_FREEZE = false;
    public static boolean STATUS_HAND_ITEM_DROP = false;
    public static boolean STATUS_CONTROL = false;
    public static boolean STATUS_FLIP_BACKWARDS = false;
    public static boolean STATUS_SPAM_MESSAGES = false;
    public static boolean STATUS_SPAM_SOUNDS = false;
    public static boolean STATUS_SEMI_BAN = false;
    public static boolean STATUS_TNT_TRACK = false;
    public static boolean STATUS_MOB_SPAWNER = false;
    public static boolean STATUS_SLOWLY_KILL = false;

    public static String getFeatureStatus(Boolean status) {
        if (status) return "§a§lON";
        return "§c§lOFF";
    }

    public static String getVanishStatus(Integer status) {
        if (status.equals(0)) {
            return "§c§lOFF";
        } else if (status.equals(1)) {
            return "§a§lTarget";
        }
        return "§b§lAll";
    }

    // Feature control
    public static Boolean CONTROL_MESSAGE_BOOLEAN = false;
    public static String CONTROL_MESSAGE = null;
}