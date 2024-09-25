/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import de.gaming12846.trollplus.constants.PermissionConstants;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

// A class to provide tab completion for commands
public class TabCompleter implements org.bukkit.command.TabCompleter {
    private final List<String> results = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        // Clear previous results to avoid stale completions
        results.clear();

        // Handle tab completion based on the command label
        switch (cmd.getLabel().toLowerCase()) {
            case "trollplus":
                handleTrollPlusTabCompletion(sender, args);
                break;
            case "troll":
                handleTrollTabCompletion(sender, args);
                break;
            case "trollbows":
                // No specific completions for "trollbows", so results remain empty
                break;
        }

        return results; // Return the list of completions
    }

    // Handles tab completions for the "trollplus" command
    private void handleTrollPlusTabCompletion(CommandSender sender, String[] args) {
        // Check the length of arguments and provide appropriate completions
        if (args.length == 1) {
            addBasicTrollPlusCompletions(sender);
        } else if (args.length == 2 && args[0].equals("blocklist")) {
            addBlocklistCompletions(sender);
        } else if (args.length == 3 && args[0].equals("blocklist")) {
            addOnlinePlayerCompletions(); // Add online players to completion list
        }
    }

    // Adds basic command options for "trollplus"
    private void addBasicTrollPlusCompletions(CommandSender sender) {
        if (sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_VERSION)) {
            results.add("version");
        }
        if (sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_RELOAD)) {
            results.add("reload");
        }
        if (sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_BLOCKLIST_ADD) || sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_BLOCKLIST_REMOVE)) {
            results.add("blocklist");
        }
        if (sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_SETTINGS)) {
            results.add("settings");
        }
    }

    // Adds options for the "blocklist" command
    private void addBlocklistCompletions(CommandSender sender) {
        if (sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_BLOCKLIST_ADD)) {
            results.add("add");
        }
        if (sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_BLOCKLIST_REMOVE)) {
            results.add("remove");
        }
    }

    // Adds online players to the results list for player-based commands
    private void addOnlinePlayerCompletions() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            results.add(player.getName());
        }
    }

    // Handles tab completions for the "troll" command
    private void handleTrollTabCompletion(CommandSender sender, String[] args) {
        if (args.length == 1 && sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_TROLL)) {
            addOnlinePlayerCompletions(); // Add online players for the "troll" command
        }
    }
}