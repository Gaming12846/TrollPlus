/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.ConfigConstants;
import de.gaming12846.trollplus.constants.LangConstants;
import de.gaming12846.trollplus.constants.PermissionConstants;
import de.gaming12846.trollplus.utils.ConfigHelper;
import de.gaming12846.trollplus.utils.GUIHelper;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.UUID;

// Handles the "trollplus" commands, the main commands for the plugin
public class TrollPlusCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIHelper guiHelperSettings;

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
            sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + ChatColor.RESET + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX_USAGE) + label + " <version|reload|blocklist|settings>");
            return true;
        }

        // Trollplus subcommand selection
        switch (args[0].toLowerCase()) {
            case "version" -> handleVersionSubcommand(sender, label, args, configHelperLanguage);
            case "reload" -> handleReloadSubcommand(sender, label, args, configHelperLanguage);
            case "blocklist" -> handleBlocklistSubcommand(sender, label, args, configHelperLanguage);
            case "settings" -> handleSettingsSubcommand(sender, args, configHelperLanguage);
            default ->
                    sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + ChatColor.RESET + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX_USAGE) + " " + label + " <version|reload|blocklist|settings>");
        }

        return true;
    }

    // Handles the "version" subcommand to display plugin information
    private void handleVersionSubcommand(CommandSender sender, String label, String[] args, ConfigHelper configHelperLanguage) {
        // Check if the player has the required permission
        if (!sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_VERSION)) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString(LangConstants.NO_PERMISSION));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 1) {
            sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + ChatColor.RESET + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX_USAGE) + " " + label + " version");
            return;
        }

        PluginDescriptionFile description = plugin.getDescription();
        String headerFooter = ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + StringUtils.repeat("-", 36);

        // Display plugin information
        sender.sendMessage(LangConstants.PLUGIN_PREFIX + headerFooter);
        sender.sendMessage(LangConstants.PLUGIN_PREFIX);
        sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.TROLLPLUS_VERSION) + " " + ChatColor.WHITE + description.getVersion());
        sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.TROLLPLUS_DEVELOPER) + " " + ChatColor.WHITE + description.getAuthors());
        sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.TROLLPLUS_PLUGIN_WEBSITE));
        sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.WHITE + description.getWebsite());
        sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.TROLLPLUS_REPORT_BUGS));
        sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.WHITE + "https://github.com/Gaming12846/TrollPlus/issues");
        sender.sendMessage(LangConstants.PLUGIN_PREFIX);
        sender.sendMessage(LangConstants.PLUGIN_PREFIX + headerFooter);

        // Send a message when an update is available
        if (plugin.updateAvailable) sender.sendMessage(LangConstants.UPDATE_AVAILABLE);
    }

    // Handles the "reload" subcommand to reload plugin configurations
    private void handleReloadSubcommand(CommandSender sender, String label, String[] args, ConfigHelper configHelperLanguage) {
        // Check if the player has the required permission
        if (!sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_RELOAD)) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString(LangConstants.NO_PERMISSION));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 1) {
            sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX_USAGE) + " " + ChatColor.RESET + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX_USAGE) + " " + label + " reload");
            return;
        }

        // Reload the plugin configurations
        plugin.getConfigHelper().loadConfig();
        plugin.getConfigHelperBlocklist().loadConfig();
        plugin.getConfigHelperLanguage().loadConfig();

        // Send a message after successfully reloading the configurations
        sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.GREEN + configHelperLanguage.getString(LangConstants.TROLLPLUS_RELOAD));
    }

    // Handles the "blocklist" subcommand to manage the blocklist of players
    private void handleBlocklistSubcommand(CommandSender sender, String label, String[] args, ConfigHelper configHelperLanguage) {
        // Check if the player has the required permission
        if (!sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_BLOCKLIST_ADD) && !sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_BLOCKLIST_REMOVE)) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString(LangConstants.NO_PERMISSION));
            return;
        }

        // Check if the command syntax is correct
        if (args.length < 2) {
            sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + ChatColor.RESET + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + label + " blocklist <add|remove>");
            return;
        }

        ConfigHelper configHelperBlocklist = plugin.getConfigHelperBlocklist();

        // Blocklist subcommand selection
        switch (args[1].toLowerCase()) {
            case "add" -> handleBlocklistAddSubcommand(sender, args, configHelperBlocklist, configHelperLanguage);
            case "remove" -> handleBlocklistRemoveSubcommand(sender, args, configHelperBlocklist, configHelperLanguage);
            default ->
                    sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + ChatColor.RESET + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + label + " blocklist <add|remove>");
        }
    }

    // Handles the "blocklist add" subcommand to add a player to the blocklist
    private void handleBlocklistAddSubcommand(CommandSender sender, String[] args, ConfigHelper configHelperBlocklist, ConfigHelper configHelperLanguage) {
        // Check if the player has the required permission
        if (!sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_BLOCKLIST_ADD)) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString(LangConstants.NO_PERMISSION));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 3) {
            sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + ChatColor.RESET + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + "blocklist add <player>");
            return;
        }

        // Check if the player is in the blocklist
        Player onlinePlayer = Bukkit.getPlayer(args[2]);
        UUID playerUUID;
        if (onlinePlayer != null) {
            playerUUID = onlinePlayer.getUniqueId();
        } else playerUUID = getOfflinePlayerUUID(args[2]);

        if (playerUUID != null) {
            if (configHelperBlocklist.contains(playerUUID.toString())) {
                String message = LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLLPLUS_ALREADY_IN_BLOCKLIST);
                sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[2] + ChatColor.RESET));
                return;
            }

            // Add player to the blocklist
            configHelperBlocklist.set(playerUUID.toString(), args[2]);
            String message = LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLLPLUS_ADDED_TO_BLOCKLIST);
            sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[2] + ChatColor.RESET));

            // Save and reload the blocklist configuration
            configHelperBlocklist.saveConfig();
            configHelperBlocklist.loadConfig();
        }
    }

    // Handles the "blocklist remove" subcommand to remove a player from the blocklist
    private void handleBlocklistRemoveSubcommand(CommandSender sender, String[] args, ConfigHelper configHelperBlocklist, ConfigHelper configHelperLanguage) {
        // Check if the player has the required permission
        if (!sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_BLOCKLIST_REMOVE)) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString(LangConstants.NO_PERMISSION));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 3) {
            sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + ChatColor.RESET + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + "blocklist remove <player>");
            return;
        }

        // Check if the player is in the blocklist
        Player onlinePlayer = Bukkit.getPlayer(args[2]);
        UUID playerUUID;
        if (onlinePlayer != null) {
            playerUUID = onlinePlayer.getUniqueId();
        } else playerUUID = getOfflinePlayerUUID(args[2]);

        if (playerUUID != null) {
            if (!configHelperBlocklist.contains(playerUUID.toString())) {
                String message = LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLLPLUS_NOT_IN_BLOCKLIST);
                sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[2] + ChatColor.RESET));
                return;
            }

            // Remove player to the blocklist
            configHelperBlocklist.set(playerUUID.toString(), null);
            String message = LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLLPLUS_REMOVED_FROM_BLOCKLIST);
            sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + args[2] + ChatColor.RESET));

            // Save and reload the blocklist configuration
            configHelperBlocklist.saveConfig();
            configHelperBlocklist.loadConfig();
        }
    }

    // Method for retrieving the UUID of an offline player with the Mojang api
    private UUID getOfflinePlayerUUID(String playerName) {
        try {
            String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the UUID from the JSON response
                String jsonResponse = response.toString();
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                // Extract the content string from the first choice's message
                String contentString = jsonObject.get("id").getAsString();
                return UUID.fromString(contentString.replaceFirst("(.{8})(.{4})(.{4})(.{4})(.{12})", "$1-$2-$3-$4-$5"));
            }
        } catch (Exception e) {
            plugin.getLoggingHelper().error(e.getMessage());
        }
        return null; // Player not found or error occurred
    }

    // Handles the "settings" subcommand to open the settings GUI for a player
    private void handleSettingsSubcommand(CommandSender sender, String[] args, ConfigHelper configHelperLanguage) {
        // Check if the command sender is not a player
        if (!(sender instanceof Player player)) {
            sender.sendMessage(LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.NO_CONSOLE));
            return;
        }

        // Check if the player has the required permission
        if (!sender.hasPermission(PermissionConstants.PERMISSION_TROLLPLUS_SETTINGS)) {
            sender.sendMessage(ChatColor.RED + configHelperLanguage.getString(LangConstants.NO_PERMISSION));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 1) {
            sender.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.RED + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " " + ChatColor.RESET + configHelperLanguage.getString(LangConstants.INVALID_SYNTAX) + " settings");
            return;
        }

        guiHelperSettings = new GUIHelper(ChatColor.BLACK + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_TITLE), 9, plugin);

        // Add the available settings to the GUI
        getGuiHelperSettings().addItem(1, ItemBuilder.createItemWithLore(Material.PAPER, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANGUAGE), configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANGUAGE_DESCRIPTION)));
        getGuiHelperSettings().addItem(2, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_METRICS_ENABLED) + ChatColor.DARK_GRAY + " " + getGuiHelperSettings().getStatus(plugin.getConfigHelper().getBoolean(ConfigConstants.METRICS_ENABLED)), configHelperLanguage.getString(LangConstants.TROLLSETTINGS_METRICS_ENABLED_DESCRIPTION)));
        getGuiHelperSettings().addItem(3, ItemBuilder.createItemWithLore(Material.GLOWSTONE, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_CHECK_FOR_UPDATES) + ChatColor.DARK_GRAY + " " + getGuiHelperSettings().getStatus(plugin.getConfigHelper().getBoolean(ConfigConstants.CHECK_FOR_UPDATES)), configHelperLanguage.getString(LangConstants.TROLLSETTINGS_CHECK_FOR_UPDATES_DESCRIPTION)));
        getGuiHelperSettings().addItem(4, ItemBuilder.createItemWithLore(Material.REDSTONE_LAMP, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_DEACTIVATE_FEATURES_ON_QUIT) + ChatColor.DARK_GRAY + " " + getGuiHelperSettings().getStatus(plugin.getConfigHelper().getBoolean(ConfigConstants.DEACTIVATE_FEATURES_ON_QUIT)), configHelperLanguage.getString(LangConstants.TROLLSETTINGS_DEACTIVATE_FEATURES_ON_QUIT_DESCRIPTION)));
        getGuiHelperSettings().addItem(5, ItemBuilder.createItemWithLore(Material.ENDER_PEARL, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_CONTROL_TELEPORT_BACK) + ChatColor.DARK_GRAY + " " + getGuiHelperSettings().getStatus(plugin.getConfigHelper().getBoolean(ConfigConstants.CONTROL_TELEPORT_BACK)), configHelperLanguage.getString(LangConstants.TROLLSETTINGS_CONTROL_TELEPORT_BACK_DESCRIPTION)));
        getGuiHelperSettings().addItem(6, ItemBuilder.createItemWithLore(Material.FIRE_CHARGE, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_SET_FIRE) + ChatColor.DARK_GRAY + " " + getGuiHelperSettings().getStatus(plugin.getConfigHelper().getBoolean(ConfigConstants.SET_FIRE)), configHelperLanguage.getString(LangConstants.TROLLSETTINGS_SET_FIRE_DESCRIPTION)));
        getGuiHelperSettings().addItem(7, ItemBuilder.createItemWithLore(Material.DIAMOND_PICKAXE, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_BREAK_BLOCKS) + ChatColor.DARK_GRAY + " " + getGuiHelperSettings().getStatus(plugin.getConfigHelper().getBoolean(ConfigConstants.BREAK_BLOCKS)), configHelperLanguage.getString(LangConstants.TROLLSETTINGS_BREAK_BLOCKS_DESCRIPTION)));

        // Add placeholders
        byte[] placeholderArray = {0, 8};
        for (int slot : placeholderArray) {
            getGuiHelperSettings().addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", configHelperLanguage.getString(LangConstants.GUI_PLACEHOLDER_DESCRIPTION)));
        }

        // Open the Settings GUI for the player
        player.openInventory(getGuiHelperSettings().getGUI());
    }

    // Retrieves the TrollBowsCommand instance
    public GUIHelper getGuiHelperSettings() {
        return guiHelperSettings;
    }
}