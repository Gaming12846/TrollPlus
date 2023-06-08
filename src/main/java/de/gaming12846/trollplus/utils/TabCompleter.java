/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    final List<String> results = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getLabel().equalsIgnoreCase("trollplus")) {
            if (args.length == 1) {
                results.clear();
                if (sender.hasPermission(Constants.PERMISSION_VERSION)) results.add("version");
                if (sender.hasPermission(Constants.PERMISSION_RELOAD)) results.add("reload");
                if (sender.hasPermission(Constants.PERMISSION_BLOCKLIST_ADD) || sender.hasPermission(Constants.PERMISSION_BLOCKLIST_REMOVE))
                    results.add("blocklist");
                if (sender.hasPermission(Constants.PERMISSION_SETTINGS)) results.add("settings");
            } else if (args.length == 2 && args[0].equals("blocklist")) {
                results.clear();
                if (sender.hasPermission(Constants.PERMISSION_BLOCKLIST_ADD)) results.add("add");
                if (sender.hasPermission(Constants.PERMISSION_BLOCKLIST_REMOVE)) results.add("remove");
            } else if (args.length == 3 && args[0].equals("blocklist") && sender.hasPermission(Constants.PERMISSION_BLOCKLIST_ADD) || sender.hasPermission(Constants.PERMISSION_BLOCKLIST_REMOVE)) {
                results.clear();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    results.add(p.getName());
                }
            } else results.clear();
        } else if (cmd.getLabel().equalsIgnoreCase("troll") && sender.hasPermission(Constants.PERMISSION_TROLL)) {
            if (args.length == 1) {
                results.clear();

                for (Player p : Bukkit.getOnlinePlayers()) {
                    results.add(p.getName());
                }
            } else results.clear();
        } else if (cmd.getLabel().equalsIgnoreCase("trollbows")) results.clear();

        return results;
    }
}