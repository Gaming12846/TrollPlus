/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.commands;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.LangConstants;
import de.gaming12846.trollplus.constants.MetadataConstants;
import de.gaming12846.trollplus.constants.PermissionConstants;
import de.gaming12846.trollplus.utils.ConfigHelper;
import de.gaming12846.trollplus.utils.GUIHelper;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Handles the "troll" command which allows a player to troll another player using various options
public class TrollCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIHelper guiHelperTroll;

    // Constructor for the TrollCommand
    public TrollCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Executes the command logic when a player uses the "/troll" command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigHelper configHelperLanguage = plugin.getConfigHelperLanguage();

        // Check if the command sender is not a player
        if (!(sender instanceof Player player)) {
            sender.sendMessage(LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.NO_PERMISSION));
            return true;
        }

        // Check if the player has the required permission
        if (!player.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_TROLL)) {
            player.sendMessage(ChatColor.RED + configHelperLanguage.getString(LangConstants.NO_PERMISSION));
            return true;
        }

        // Check if the command syntax is correct
        if (args.length != 1) {
            player.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + ChatColor.RESET + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX_USAGE) + label + " <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        // Check if the target player is online
        if (target == null) {
            player.sendMessage((LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLL_PLAYER_NOT_ONLINE)).replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[0] + ChatColor.RESET));
            return true;
        }

        // Check if the target player is immune to trolling
        if (plugin.getConfigHelperBlocklist().contains(target.getUniqueId().toString())) {
            player.sendMessage((LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLL_PLAYER_IS_IMMUNE)).replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[0] + ChatColor.RESET));
            return true;
        }

        // Creates the Troll GUI for the player
        createTrollGUI(target, configHelperLanguage);

        // Open the Troll GUI for the player to select trolling options
        player.openInventory(getGUIHelperTroll().getGUI());
        return true;
    }

    // Creates the Troll GUI for the player
    private void createTrollGUI(Player target, ConfigHelper configHelperLanguage) {
        guiHelperTroll = new GUIHelper(ChatColor.BLACK + configHelperLanguage.getString(LangConstants.TROLL_GUI_TITLE) + " " + ChatColor.DARK_RED + target.getName(), 54, target, plugin);

        // Add the available trolling options to the GUI
        getGUIHelperTroll().addItem(4, ItemBuilder.createSkull(ChatColor.GOLD + target.getName(), target.getPlayer()));

        getGUIHelperTroll().addItemWithLoreAndStatus(11, Material.BLUE_ICE, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_FREEZE), MetadataConstants.TROLLPLUS_FREEZE, configHelperLanguage.getString(LangConstants.TROLL_GUI_FREEZE_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(12, Material.SHEARS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_HAND_ITEM_DROP), MetadataConstants.TROLLPLUS_HAND_ITEM_DROP, configHelperLanguage.getString(LangConstants.TROLL_GUI_HAND_ITEM_DROP_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(13, Material.LEAD, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_CONTROL), MetadataConstants.TROLLPLUS_CONTROL_TARGET, configHelperLanguage.getString(LangConstants.TROLL_GUI_CONTROL_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(14, Material.COMPASS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_FLIP_BACKWARDS), MetadataConstants.TROLLPLUS_FLIP_BEHIND, configHelperLanguage.getString(LangConstants.TROLL_GUI_FLIP_BACKWARDS_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(15, Material.SLIME_BALL, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_SPANK), MetadataConstants.TROLLPLUS_SPANK, configHelperLanguage.getString(LangConstants.TROLL_GUI_SPANK_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(19, Material.WRITABLE_BOOK, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_SPAM_MESSAGES), MetadataConstants.TROLLPLUS_SPAM_MESSAGES, configHelperLanguage.getString(LangConstants.TROLL_GUI_SPAM_MESSAGES_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(20, Material.NOTE_BLOCK, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_SPAM_SOUNDS), MetadataConstants.TROLLPLUS_SPAM_SOUNDS, configHelperLanguage.getString(LangConstants.TROLL_GUI_SPAM_SOUNDS_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(21, Material.TRIPWIRE_HOOK, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_SEMI_BAN), MetadataConstants.TROLLPLUS_SEMI_BAN, configHelperLanguage.getString(LangConstants.TROLL_GUI_SEMI_BAN_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(22, Material.ANVIL, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_FALLING_ANVILS), MetadataConstants.TROLLPLUS_FALLING_ANVILS, configHelperLanguage.getString(LangConstants.TROLL_GUI_FALLING_ANVILS_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(23, Material.TNT, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_TNT_TRACK), MetadataConstants.TROLLPLUS_TNT_TRACK, configHelperLanguage.getString(LangConstants.TROLL_GUI_TNT_TRACK_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(24, Material.SPAWNER, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_MOB_SPAWNER), MetadataConstants.TROLLPLUS_MOB_SPAWNER, configHelperLanguage.getString(LangConstants.TROLL_GUI_MOB_SPAWNER_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(25, Material.SKELETON_SKULL, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_SLOWLY_KILL), MetadataConstants.TROLLPLUS_SLOWLY_KILL, configHelperLanguage.getString(LangConstants.TROLL_GUI_SLOWLY_KILL_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(28, Material.ENDER_EYE, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_RANDOM_TELEPORT), MetadataConstants.TROLLPLUS_RANDOM_TELEPORT, configHelperLanguage.getString(LangConstants.TROLL_GUI_RANDOM_TELEPORT_DESCRIPTION));
        getGUIHelperTroll().addItemWithLore(33, Material.EGG, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_INVENTORY_DROP), configHelperLanguage.getString(LangConstants.TROLL_GUI_INVENTORY_DROP_DESCRIPTION));
        getGUIHelperTroll().addItemWithLore(34, Material.BARREL, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_INVENTORY_SHUFFLE), configHelperLanguage.getString(LangConstants.TROLL_GUI_INVENTORY_SHUFFLE_DESCRIPTION));
        getGUIHelperTroll().addItemWithLore(38, Material.MUSIC_DISC_11, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_RANDOM_SCARY_SOUND), configHelperLanguage.getString(LangConstants.TROLL_GUI_RANDOM_SCARY_SOUND_DESCRIPTION));
        getGUIHelperTroll().addItemWithLore(39, Material.FIREWORK_ROCKET, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_ROCKET), configHelperLanguage.getString(LangConstants.TROLL_GUI_ROCKET_DESCRIPTION));
        getGUIHelperTroll().addItemWithLore(40, Material.FEATHER, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_FREEFALL), configHelperLanguage.getString(LangConstants.TROLL_GUI_FREEFALL_DESCRIPTION));
        getGUIHelperTroll().addItemWithLore(41, Material.PAPER, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_FAKE_BAN), configHelperLanguage.getString(LangConstants.TROLL_GUI_FAKE_BAN_DESCRIPTION));
        getGUIHelperTroll().addItemWithLore(42, Material.ENCHANTED_GOLDEN_APPLE, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_FAKE_OP), configHelperLanguage.getString(LangConstants.TROLL_GUI_FAKE_OP_DESCRIPTION));

        getGUIHelperTroll().addItemWithLore(18, Material.ENDER_PEARL, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_TELEPORT), configHelperLanguage.getString(LangConstants.TROLL_GUI_TELEPORT_DESCRIPTION));
        getGUIHelperTroll().addItemWithLore(26, Material.CHEST, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_INVSEE), configHelperLanguage.getString(LangConstants.TROLL_GUI_INVSEE_DESCRIPTION));
        getGUIHelperTroll().addItemWithLore(27, Material.WITHER_SKELETON_SKULL, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_KILL), configHelperLanguage.getString(LangConstants.TROLL_GUI_KILL_DESCRIPTION));
        getGUIHelperTroll().addItemWithLore(35, Material.ENDER_CHEST, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_INVSEE_ENDER_CHEST), configHelperLanguage.getString(LangConstants.TROLL_GUI_INVSEE_ENDER_CHEST_DESCRIPTION));
        getGUIHelperTroll().addItemWithLoreAndStatus(48, Material.POTION, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_VANISH), MetadataConstants.TROLLPLUS_VANISH, configHelperLanguage.getString(LangConstants.TROLL_GUI_VANISH_DESCRIPTION));
        getGUIHelperTroll().addItemWithLore(50, Material.CHORUS_FRUIT, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_RANDOM_TROLL), configHelperLanguage.getString(LangConstants.TROLL_GUI_RANDOM_TROLL_DESCRIPTION));

        // Add placeholders to the GUI
        final byte[] placeholderSlots = {0, 1, 2, 3, 5, 6, 7, 8, 45, 46, 49, 52, 53};
        for (int slot : placeholderSlots) {
            getGUIHelperTroll().addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", configHelperLanguage.getString(LangConstants.GUI_PLACEHOLDER_DESCRIPTION)));
        }
        final byte[] placeholderSlots1 = {3, 5, 9, 17, 36, 44, 47, 51};
        for (int slot : placeholderSlots1) {
            getGUIHelperTroll().addItem(slot, ItemBuilder.createItemWithLore(Material.WHITE_STAINED_GLASS_PANE, " ", configHelperLanguage.getString(LangConstants.GUI_PLACEHOLDER_DESCRIPTION)));
        }
    }

    // Retrieves the TrollBowsCommand instance
    public GUIHelper getGUIHelperTroll() {
        return guiHelperTroll;
    }
}