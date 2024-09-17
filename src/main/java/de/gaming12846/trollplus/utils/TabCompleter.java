/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

// A class to provide tab completion for commands
public class TabCompleter implements org.bukkit.command.TabCompleter {
    private final List<String> results = new ArrayList<>();

    // Provides a list of possible completions
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        // Clear previous results to avoid stale completions
        results.clear();

        // Handle tab completion for the "trollplus" command
        if (cmd.getLabel().equalsIgnoreCase("trollplus")) {
            switch (args.length) {
                case 1 -> {
                    // First argument completion
                    if (sender.hasPermission("trollplus.version")) results.add("version");
                    if (sender.hasPermission("trollplus.reload")) results.add("reload");
                    if (sender.hasPermission("trollplus.troll") || sender.hasPermission("trollplus.blocklist.remove"))
                        results.add("blocklist");
                    if (sender.hasPermission("trollplus.settings")) results.add("settings");
                }
                case 2 -> {
                    // Second argument completion for "blocklist"
                    if (args[0].equals("blocklist")) {
                        if (sender.hasPermission("trollplus.troll")) results.add("add");
                        if (sender.hasPermission("trollplus.blocklist.remove")) results.add("remove");
                    }
                }
                case 3 -> {
                    // Third argument completion for "blocklist add/remove"
                    if (args[0].equals("blocklist") && (sender.hasPermission("trollplus.troll") || sender.hasPermission("trollplus.blocklist.remove"))) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            results.add(p.getName());
                        }
                    }
                }
            }
        }
        // Handle tab completion for the "troll" command
        else if (cmd.getLabel().equalsIgnoreCase("troll") && sender.hasPermission("trollplus.troll")) {
            if (args.length == 1) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    results.add(p.getName());
                }
            }
        }
        // Handle tab completion for the "trollbows" command
        else if (cmd.getLabel().equalsIgnoreCase("trollbows")) {
            // No specific completions for this command; clear results
            results.clear();
        }

        return results;
    }
}