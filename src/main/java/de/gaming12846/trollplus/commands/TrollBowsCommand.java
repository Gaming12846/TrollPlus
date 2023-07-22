/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.commands;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.Constants;
import de.gaming12846.trollplus.utils.GUIUtil;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TrollBowsCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIUtil trollBowsGUI;

    public TrollBowsCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + label + Constants.PLUGIN_NO_CONSOLE);
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission(Constants.PERMISSION_TROLLBOWS)) {
            player.sendMessage(Constants.PLUGIN_NO_PERMISSION);
            return true;
        }

        if (args.length != 0) {
            player.sendMessage(Constants.PLUGIN_INVALID_SYNTAX + label);
            return true;
        }

        FileConfiguration langConfig = plugin.getLanguageConfig().getConfig();

        // Create trollbows GUI
        trollBowsGUI = new GUIUtil(langConfig.getString("trollbows.title"), 9);

        // Add trollbows
        trollBowsGUI.addItem(8, ItemBuilder.createItemWithLore(Material.BARRIER, ChatColor.RED + langConfig.getString("trollbows.close"), langConfig.getString("trollbows.close-description")));

        trollBowsGUI.addItem(0, ItemBuilder.createBow(langConfig.getString("trollbows.explosion-bow"), langConfig.getString("trollbows.explosion-bow-description")));
        trollBowsGUI.addItem(1, ItemBuilder.createBow(langConfig.getString("trollbows.tnt-bow"), langConfig.getString("trollbows.tnt-bow-description")));
        trollBowsGUI.addItem(2, ItemBuilder.createBow(langConfig.getString("trollbows.lighting-bolt-bow"), langConfig.getString("trollbows.lighting-bolt-bow-description")));
        trollBowsGUI.addItem(3, ItemBuilder.createBow(langConfig.getString("trollbows.silverfish-bow"), langConfig.getString("trollbows.silverfish-bow-description")));

        // Placeholders
        byte[] placeholderArray = new byte[]{4, 5, 6, 7};
        for (int slot : placeholderArray) {
            trollBowsGUI.addItem(slot, ItemBuilder.createItemWithLore(Material.GRAY_STAINED_GLASS_PANE, " ", langConfig.getString("guis.placeholder-description")));
        }

        player.openInventory(trollBowsGUI.getGUI());

        return true;
    }
}