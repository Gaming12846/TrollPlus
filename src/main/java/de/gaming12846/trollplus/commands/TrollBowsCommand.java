/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.commands;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.ConfigUtil;
import de.gaming12846.trollplus.utils.Constants;
import de.gaming12846.trollplus.utils.GUIUtil;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Handles the 'trollbows' command which opens a GUI for players to interact with different types of troll bows
public class TrollBowsCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIUtil trollBowsGUI;

    // Constructor for the TrollBowsCommand
    public TrollBowsCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Executes the command logic when a player uses the "/trollbows" command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigUtil langConfig = plugin.getLanguageConfig();

        // Check if the command sender is not a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("no-console"));
            return true;
        }

        Player player = (Player) sender;

        // Check if the player has the required permission
        if (!player.hasPermission(Constants.PERMISSION_TROLLBOWS)) {
            player.sendMessage(ChatColor.RED + langConfig.getString("no-permission"));
            return true;
        }

        // Check if the command syntax is correct
        if (args.length != 0) {
            player.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("invalid-syntax") + " " + ChatColor.RESET + langConfig.getString("invalid-syntax-use") + label);
            return true;
        }

        // Creates the TrollBows GUI for the player
        createTrollBowsGUI(langConfig);

        // Opens the TrollBows GUI for the player
        player.openInventory(trollBowsGUI.getGUI());
        return true;
    }

    // Creates the TrollBows GUI for the player
    private void createTrollBowsGUI(ConfigUtil langConfig) {
        trollBowsGUI = new GUIUtil(langConfig.getString("trollbows.title"), 9);

        // Add troll bows to the GUI
        trollBowsGUI.addItemWithLore(8, Material.BARRIER, ChatColor.RED + langConfig.getString("guis.close"), langConfig.getString("guis.close-description"));
        trollBowsGUI.addItem(0, ItemBuilder.createBow(plugin, langConfig.getString("trollbows.explosion-bow"), langConfig.getString("trollbows.explosion-bow-description")));
        trollBowsGUI.addItem(1, ItemBuilder.createBow(plugin, langConfig.getString("trollbows.tnt-bow"), langConfig.getString("trollbows.tnt-bow-description")));
        trollBowsGUI.addItem(2, ItemBuilder.createBow(plugin, langConfig.getString("trollbows.lightning-bolt-bow"), langConfig.getString("trollbows.lightning-bolt-bow-description")));
        trollBowsGUI.addItem(3, ItemBuilder.createBow(plugin, langConfig.getString("trollbows.silverfish-bow"), langConfig.getString("trollbows.silverfish-bow-description")));

        // Add placeholders to the GUI
        final byte[] placeholderSlots = {4, 5, 6, 7};
        for (int slot : placeholderSlots) {
            trollBowsGUI.addItem(slot, ItemBuilder.createItemWithLore(Material.GRAY_STAINED_GLASS_PANE, " ", langConfig.getString("guis.placeholder.description")));
        }
    }
}