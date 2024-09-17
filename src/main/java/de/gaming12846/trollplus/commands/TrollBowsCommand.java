/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.commands;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.ConfigHelper;
import de.gaming12846.trollplus.utils.GUIHelper;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.gaming12846.trollplus.TrollPlus.PLUGIN_PREFIX;

// Handles the 'trollbows' command which opens a GUI for players to interact with different types of troll bows
public class TrollBowsCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIHelper GUIHelperTrollBows;

    // Constructor for the TrollBowsCommand
    public TrollBowsCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Executes the command logic when a player uses the "/trollbows" command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigHelper configHelperLanguage = plugin.getConfigHelperLanguage();

        // Check if the command sender is not a player
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PLUGIN_PREFIX + configHelperLanguage.getString("no-console"));
            return true;
        }

        // Check if the player has the required permission
        if (!player.hasPermission("trollplus.bows")) {
            player.sendMessage(ChatColor.RED + configHelperLanguage.getString("no-permission"));
            return true;
        }

        // Check if the command syntax is correct
        if (args.length != 0) {
            player.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("invalid-syntax") + " " + ChatColor.RESET + configHelperLanguage.getString("invalid-syntax-use") + label);
            return true;
        }

        // Creates the TrollBows GUI for the player
        createTrollBowsGUI(configHelperLanguage);

        // Opens the TrollBows GUI for the player
        player.openInventory(GUIHelperTrollBows.getGUI());
        return true;
    }

    // Creates the TrollBows GUI for the player
    private void createTrollBowsGUI(ConfigHelper configHelperLanguage) {
        GUIHelperTrollBows = new GUIHelper(ChatColor.BLACK + configHelperLanguage.getString("trollbows.title"), 9);

        // Add troll bows to the GUI
        GUIHelperTrollBows.addItem(2, ItemBuilder.createBow(plugin, configHelperLanguage.getString("trollbows.explosion-bow"), configHelperLanguage.getString("trollbows.explosion-bow-description")));
        GUIHelperTrollBows.addItem(3, ItemBuilder.createBow(plugin, configHelperLanguage.getString("trollbows.tnt-bow"), configHelperLanguage.getString("trollbows.tnt-bow-description")));
        GUIHelperTrollBows.addItem(4, ItemBuilder.createBow(plugin, configHelperLanguage.getString("trollbows.lightning-bolt-bow"), configHelperLanguage.getString("trollbows.lightning-bolt-bow-description")));
        GUIHelperTrollBows.addItem(5, ItemBuilder.createBow(plugin, configHelperLanguage.getString("trollbows.silverfish-bow"), configHelperLanguage.getString("trollbows.silverfish-bow-description")));
        GUIHelperTrollBows.addItem(6, ItemBuilder.createBow(plugin, configHelperLanguage.getString("trollbows.potion-effect-bow"), configHelperLanguage.getString("trollbows.potion-effect-bow-description")));

        // Add placeholders to the GUI
        final byte[] placeholderSlots = {0, 8};
        for (int slot : placeholderSlots) {
            GUIHelperTrollBows.addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", configHelperLanguage.getString("guis.placeholder.description")));
        }
        final byte[] placeholderSlots1 = {1, 7};
        for (int slot : placeholderSlots1) {
            GUIHelperTrollBows.addItem(slot, ItemBuilder.createItemWithLore(Material.WHITE_STAINED_GLASS_PANE, " ", configHelperLanguage.getString("guis.placeholder.description")));
        }
    }
}