/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.commands;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.ConfigHelper;
import de.gaming12846.trollplus.utils.GUIHelper;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import static de.gaming12846.trollplus.TrollPlus.PLUGIN_PREFIX;

// Handles the "trollplus" commands, the main commands for the plugin
public class TrollPlusCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIHelper GUIHelperSettings;

    // Constructor for the TrollPlusCommand
    public TrollPlusCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Executes the command logic when a player uses the "/trollplus" command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigHelper configHelperLanguage = plugin.getConfigHelperLanguage();

        // Display usage information if no arguments are provided
        if (args.length == 0) {
            sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("invalid-syntax") + " " + ChatColor.RESET + configHelperLanguage.getString("invalid-syntax-use") + label + " <version|reload|blocklist|settings>");
            return true;
        }

        // Trollplus subcommand selection
        switch (args[0].toLowerCase()) {
            case "version" -> handleVersionSubcommand(sender, label, args, configHelperLanguage);
            case "reload" -> handleReloadSubcommand(sender, label, args, configHelperLanguage);
            case "blocklist" -> handleBlocklistSubcommand(sender, label, args, configHelperLanguage);
            case "settings" -> handleSettingsSubcommand(sender, args, configHelperLanguage);
            default ->
                    sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("invalid-syntax") + " " + ChatColor.RESET + configHelperLanguage.getString("invalid-syntax-use") + " " + label + " <version|reload|blocklist|settings>");
        }

        return true;
    }

    // Handles the "version" subcommand to display plugin information
    private void handleVersionSubcommand(CommandSender sender, String label, String[] args, ConfigHelper configHelperLanguage) {
        // Check if the player has the required permission
        if (!sender.hasPermission("trollplus.version")) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 1) {
            sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("invalid-syntax") + " " + ChatColor.RESET + configHelperLanguage.getString("invalid-syntax-use") + " " + label + " version");
            return;
        }

        PluginDescriptionFile description = plugin.getDescription();
        String headerFooter = ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + StringUtils.repeat("-", 36);

        // Display plugin information
        sender.sendMessage(PLUGIN_PREFIX + headerFooter);
        sender.sendMessage(PLUGIN_PREFIX);
        sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("trollplus.version") + " " + ChatColor.WHITE + description.getVersion());
        sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("trollplus.developer") + " " + ChatColor.WHITE + description.getAuthors());
        sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("trollplus.plugin-website"));
        sender.sendMessage(PLUGIN_PREFIX + ChatColor.WHITE + description.getWebsite());
        sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("trollplus.report-bugs"));
        sender.sendMessage(PLUGIN_PREFIX + ChatColor.WHITE + "https://github.com/Gaming12846/TrollPlus/issues");
        sender.sendMessage(PLUGIN_PREFIX);
        sender.sendMessage(PLUGIN_PREFIX + headerFooter);

        // Send a message when an update is available
        sender.sendMessage(plugin.updateCheckerLog);
    }

    // Handles the "reload" subcommand to reload plugin configurations
    private void handleReloadSubcommand(CommandSender sender, String label, String[] args, ConfigHelper configHelperLanguage) {
        // Check if the player has the required permission
        if (!sender.hasPermission("trollplus.reload")) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 1) {
            sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("invalid-syntax") + " " + ChatColor.RESET + configHelperLanguage.getString("invalid-syntax-use") + " " + label + " reload");
            return;
        }

        // Reload the plugin configurations
        plugin.getConfigHelper().loadConfig();
        plugin.getConfigHelperBlocklist().loadConfig();
        plugin.getConfigHelperLanguage().loadConfig();

        // Send a message after successfully reloading the configurations
        sender.sendMessage(PLUGIN_PREFIX + ChatColor.GREEN + configHelperLanguage.getString("trollplus.reload"));
    }

    // Handles the "blocklist" subcommand to manage the blocklist of players
    private void handleBlocklistSubcommand(CommandSender sender, String label, String[] args, ConfigHelper configHelperLanguage) {
        // Check if the player has the required permission
        if (!sender.hasPermission("trollplus.blocklist.add") && !sender.hasPermission("trollplus.blocklist.remove")) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length < 2) {
            sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("invalid-syntax") + " " + ChatColor.RESET + configHelperLanguage.getString("invalid-syntax-use") + " " + label + " blocklist <add|remove>");
            return;
        }

        ConfigHelper configHelperBlocklist = plugin.getConfigHelperBlocklist();

        // Blocklist subcommand selection
        switch (args[1].toLowerCase()) {
            case "add" -> handleBlocklistAddSubcommand(sender, args, configHelperBlocklist, configHelperLanguage);
            case "remove" -> handleBlocklistRemoveSubcommand(sender, args, configHelperBlocklist, configHelperLanguage);
            default ->
                    sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("invalid-syntax") + " " + ChatColor.RESET + configHelperLanguage.getString("invalid-syntax-use") + " " + label + " blocklist <add|remove>");
        }
    }

    // Handles the "blocklist add" subcommand to add a player to the blocklist
    private void handleBlocklistAddSubcommand(CommandSender sender, String[] args, ConfigHelper configHelperBlocklist, ConfigHelper configHelperLanguage) {
        // Check if the player has the required permission
        if (!sender.hasPermission("trollplus.blocklist.add")) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 3) {
            sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("invalid-syntax") + " " + ChatColor.RESET + configHelperLanguage.getString("invalid-syntax-use") + " " + "blocklist add <player>");
            return;
        }

        // Check if the player is in the blocklist
        OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
        if (configHelperBlocklist.contains(offlineTarget.getUniqueId().toString())) {
            String message = PLUGIN_PREFIX + configHelperLanguage.getString("trollplus.already-in-blocklist");
            sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));
            return;
        }

        // Add player to the blocklist
        configHelperBlocklist.set(offlineTarget.getUniqueId().toString(), offlineTarget.getName());
        String message = PLUGIN_PREFIX + configHelperLanguage.getString("trollplus.added-to-blocklist");
        sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));

        // Save and reload the blocklist configuration
        configHelperBlocklist.saveConfig();
        configHelperBlocklist.loadConfig();
    }

    // Handles the "blocklist remove" subcommand to remove a player from the blocklist
    private void handleBlocklistRemoveSubcommand(CommandSender sender, String[] args, ConfigHelper configHelperBlocklist, ConfigHelper configHelperLanguage) {
        // Check if the player has the required permission
        if (!sender.hasPermission("trollplus.blocklist.remove")) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 3) {
            sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("invalid-syntax") + " " + ChatColor.RESET + configHelperLanguage.getString("invalid-syntax-use") + " " + "blocklist remove <player>");
            return;
        }

        // Check if the player is in the blocklist
        OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
        if (!configHelperBlocklist.contains(offlineTarget.getUniqueId().toString())) {
            String message = PLUGIN_PREFIX + configHelperLanguage.getString("trollplus.not-in-blocklist");
            sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));
            return;
        }

        // Remove player to the blocklist
        configHelperBlocklist.set(offlineTarget.getUniqueId().toString(), null);
        String message = PLUGIN_PREFIX + configHelperLanguage.getString("trollplus.removed-from-blocklist");
        sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));

        // Save and reload the blocklist configuration
        configHelperBlocklist.saveConfig();
        configHelperBlocklist.loadConfig();
    }

    // Handles the "settings" subcommand to open the settings GUI for a player
    private void handleSettingsSubcommand(CommandSender sender, String[] args, ConfigHelper configHelperLanguage) {
        // Check if the command sender is not a player
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PLUGIN_PREFIX + configHelperLanguage.getString("no-console"));
            return;
        }

        // Check if the player has the required permission
        if (!sender.hasPermission("trollplus.settings")) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 1) {
            sender.sendMessage(PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString("invalid-syntax") + " " + ChatColor.RESET + configHelperLanguage.getString("invalid-syntax-use") + " settings");
            return;
        }

        GUIHelperSettings = new GUIHelper(ChatColor.BLACK + configHelperLanguage.getString("trollsettings.title"), 9, plugin);

        // Add the available settings to the GUI
        GUIHelperSettings.addItem(1, ItemBuilder.createItemWithLore(Material.PAPER, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.language"), configHelperLanguage.getString("trollsettings.language-description")));
        GUIHelperSettings.addItem(2, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.metrics-enabled") + ChatColor.DARK_GRAY + " " + GUIHelperSettings.getStatus(plugin.getConfigHelper().getBoolean("metrics-enabled")), configHelperLanguage.getString("trollsettings.metrics-enabled-description")));
        GUIHelperSettings.addItem(3, ItemBuilder.createItemWithLore(Material.GLOWSTONE, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.check-for-updates") + ChatColor.DARK_GRAY + " " + GUIHelperSettings.getStatus(plugin.getConfigHelper().getBoolean("check-for-updates")), configHelperLanguage.getString("trollsettings.check-for-updates-description")));
        GUIHelperSettings.addItem(4, ItemBuilder.createItemWithLore(Material.REDSTONE_LAMP, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.deactivate-features-on-quit") + ChatColor.DARK_GRAY + " " + GUIHelperSettings.getStatus(plugin.getConfigHelper().getBoolean("deactivate-features-on-quit")), configHelperLanguage.getString("trollsettings.deactivate-features-on-quit-description")));
        GUIHelperSettings.addItem(5, ItemBuilder.createItemWithLore(Material.ENDER_PEARL, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.control-teleport-back") + ChatColor.DARK_GRAY + " " + GUIHelperSettings.getStatus(plugin.getConfigHelper().getBoolean("control-teleport-back")), configHelperLanguage.getString("trollsettings.control-teleport-back-description")));
        GUIHelperSettings.addItem(6, ItemBuilder.createItemWithLore(Material.FIRE_CHARGE, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.set-fire") + ChatColor.DARK_GRAY + " " + GUIHelperSettings.getStatus(plugin.getConfigHelper().getBoolean("set-fire")), configHelperLanguage.getString("trollsettings.set-fire-description")));
        GUIHelperSettings.addItem(7, ItemBuilder.createItemWithLore(Material.DIAMOND_PICKAXE, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.break-blocks") + ChatColor.DARK_GRAY + " " + GUIHelperSettings.getStatus(plugin.getConfigHelper().getBoolean("break-blocks")), configHelperLanguage.getString("trollsettings.break-blocks-description")));

        // Add placeholders
        byte[] placeholderArray = {0, 8};
        for (int slot : placeholderArray) {
            GUIHelperSettings.addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", configHelperLanguage.getString("guis.placeholder.description")));
        }

        // Open the Settings GUI for the player
        player.openInventory(GUIHelperSettings.getGUI());
    }
}