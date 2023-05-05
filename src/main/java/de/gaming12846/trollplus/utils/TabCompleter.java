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
                results.add("version");
                results.add("reload");
                results.add("blocklist");
            } else if (args.length == 2 && args[0].equals("blocklist")) {
                results.clear();
                results.add("add");
                results.add("remove");
            } else if (args.length == 3 && args[0].equals("blocklist")) {
                results.clear();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    results.add(p.getName());
                }
            } else results.clear();
        } else if (cmd.getLabel().equalsIgnoreCase("troll")) {
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