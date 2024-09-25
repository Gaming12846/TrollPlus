/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.commands;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.LangConstants;
import de.gaming12846.trollplus.constants.PermissionConstants;
import de.gaming12846.trollplus.utils.ConfigHelper;
import de.gaming12846.trollplus.utils.GUIHelper;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Handles the "trollbows" command which opens a GUI for players to interact with different types of troll bows
public class TrollBowsCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIHelper guiHelperTrollBows;

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
            sender.sendMessage(LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.NO_CONSOLE));
            return true;
        }

        // Check if the player has the required permission
        if (!player.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_BOWS)) {
            player.sendMessage(ChatColor.RED + configHelperLanguage.getString(LangConstants.NO_PERMISSION));
            return true;
        }

        // Check if the command syntax is correct
        if (args.length != 0) {
            player.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + ChatColor.RESET + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX_USAGE) + label);
            return true;
        }

        // Creates the TrollBows GUI for the player
        createTrollBowsGUI(configHelperLanguage);

        // Opens the TrollBows GUI for the player
        player.openInventory(getGUIHelperTrollBows().getGUI());
        return true;
    }

    // Creates the TrollBows GUI for the player
    private void createTrollBowsGUI(ConfigHelper configHelperLanguage) {
        guiHelperTrollBows = new GUIHelper(ChatColor.BLACK + configHelperLanguage.getString(LangConstants.TROLLBOWS_TITLE), 9);

        // Add troll bows to the GUI
        getGUIHelperTrollBows().addItem(2, ItemBuilder.createBow(plugin, configHelperLanguage.getString(LangConstants.TROLLBOWS_EXPLOSION_BOW), configHelperLanguage.getString(LangConstants.TROLLBOWS_EXPLOSION_BOW_DESCRIPTION)));
        getGUIHelperTrollBows().addItem(3, ItemBuilder.createBow(plugin, configHelperLanguage.getString(LangConstants.TROLLBOWS_TNT_BOW), configHelperLanguage.getString(LangConstants.TROLLBOWS_TNT_BOW_DESCRIPTION)));
        getGUIHelperTrollBows().addItem(4, ItemBuilder.createBow(plugin, configHelperLanguage.getString(LangConstants.TROLLBOWS_LIGHTNING_BOLT_BOW), configHelperLanguage.getString(LangConstants.TROLLBOWS_LIGHTNING_BOLT_BOW_DESCRIPTION)));
        getGUIHelperTrollBows().addItem(5, ItemBuilder.createBow(plugin, configHelperLanguage.getString(LangConstants.TROLLBOWS_SILVERFISH_BOW), configHelperLanguage.getString(LangConstants.TROLLBOWS_SILVERFISH_BOW_DESCRIPTION)));
        getGUIHelperTrollBows().addItem(6, ItemBuilder.createBow(plugin, configHelperLanguage.getString(LangConstants.TROLLBOWS_POTION_EFFECT_BOW), configHelperLanguage.getString(LangConstants.TROLLBOWS_POTION_EFFECT_BOW_DESCRIPTION)));

        // Add placeholders to the GUI
        final byte[] placeholderSlots = {0, 8};
        for (int slot : placeholderSlots) {
            getGUIHelperTrollBows().addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", configHelperLanguage.getString(LangConstants.GUI_PLACEHOLDER_DESCRIPTION)));
        }
        final byte[] placeholderSlots1 = {1, 7};
        for (int slot : placeholderSlots1) {
            getGUIHelperTrollBows().addItem(slot, ItemBuilder.createItemWithLore(Material.WHITE_STAINED_GLASS_PANE, " ", configHelperLanguage.getString(LangConstants.GUI_PLACEHOLDER_DESCRIPTION)));
        }
    }

    // Retrieves the GUIHelperTrollBows instance
    public GUIHelper getGUIHelperTrollBows() {
        return guiHelperTrollBows;
    }
}