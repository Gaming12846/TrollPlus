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

// Handles the "trollplus" commands, the main commands for the plugin
public class TrollPlusCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIUtil settingsGUI;

    // Constructor for the TrollPlusCommand
    public TrollPlusCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Executes the command logic when a player uses the "/trollplus" command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigUtil langConfig = plugin.getLanguageConfig();

        // Display usage information if no arguments are provided
        if (args.length == 0) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("invalid-syntax") + " " + ChatColor.RESET + langConfig.getString("invalid-syntax-use") + label + " <version|reload|blocklist|settings>");
            return true;
        }

        // Trollplus subcommand selection
        switch (args[0].toLowerCase()) {
            case "version":
                handleVersionSubcommand(sender, label, args, langConfig);
                break;
            case "reload":
                handleReloadSubcommand(sender, label, args, langConfig);
                break;
            case "blocklist":
                handleBlocklistSubcommand(sender, label, args, langConfig);
                break;
            case "settings":
                handleSettingsSubcommand(sender, args, langConfig);
                break;
            default:
                sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("invalid-syntax") + " " + ChatColor.RESET + langConfig.getString("invalid-syntax-use") + " " + label + " <version|reload|blocklist|settings>");
                break;
        }

        return true;
    }

    // Handles the "version" subcommand to display plugin information
    private void handleVersionSubcommand(CommandSender sender, String label, String[] args, ConfigUtil langConfig) {
        // Check if the player has the required permission
        if (!sender.hasPermission(Constants.PERMISSION_VERSION)) {
            sender.sendMessage(ChatColor.RED + langConfig.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 1) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("invalid-syntax") + " " + ChatColor.RESET + langConfig.getString("invalid-syntax-use") + " " + label + " version");
            return;
        }

        PluginDescriptionFile description = plugin.getDescription();
        String headerFooter = ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + StringUtils.repeat("-", 36);

        // Display plugin information
        sender.sendMessage(Constants.PLUGIN_PREFIX + headerFooter);
        sender.sendMessage(Constants.PLUGIN_PREFIX);
        sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("trollplus.version") + " " + ChatColor.WHITE + description.getVersion());
        sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("trollplus.developer") + " " + ChatColor.WHITE + description.getAuthors().get(0));
        sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("trollplus.plugin-website"));
        sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.WHITE + description.getWebsite());
        sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("trollplus.report-bugs"));
        sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.WHITE + "https://github.com/Gaming12846/TrollPlus/issues");
        sender.sendMessage(Constants.PLUGIN_PREFIX);
        sender.sendMessage(Constants.PLUGIN_PREFIX + headerFooter);

        // Send a message when an update is available
        if (plugin.updateAvailable)
            sender.sendMessage(langConfig.getString("update-available") + " https://www.spigotmc.org/resources/81193");
    }

    // Handles the "reload" subcommand to reload plugin configurations
    private void handleReloadSubcommand(CommandSender sender, String label, String[] args, ConfigUtil langConfig) {
        // Check if the player has the required permission
        if (!sender.hasPermission(Constants.PERMISSION_RELOAD)) {
            sender.sendMessage(ChatColor.RED + langConfig.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 1) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("invalid-syntax") + " " + ChatColor.RESET + langConfig.getString("invalid-syntax-use") + " " + label + " reload");
            return;
        }

        // Reload the plugin configurations
        plugin.blocklistConfig.reload();
        plugin.langCustomConfig.reload();
        plugin.langEnglishConfig.reload();
        plugin.langSimplifiedChineseConfig.reload();
        plugin.reloadConfig();

        // Send a message after successfully reloading the configurations
        sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.GREEN + langConfig.getString("trollplus.reload"));
    }

    // Handles the "blocklist" subcommand to manage the blocklist of players
    private void handleBlocklistSubcommand(CommandSender sender, String label, String[] args, ConfigUtil langConfig) {
        // Check if the player has the required permission
        if (!sender.hasPermission(Constants.PERMISSION_BLOCKLIST_ADD) && !sender.hasPermission(Constants.PERMISSION_BLOCKLIST_REMOVE)) {
            sender.sendMessage(ChatColor.RED + langConfig.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length < 2) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("invalid-syntax") + " " + ChatColor.RESET + langConfig.getString("invalid-syntax-use") + " " + label + " blocklist <add|remove>");
            return;
        }

        ConfigUtil blocklistConfig = plugin.getBlocklistConfig();

        // Blocklist subcommand selection
        switch (args[1].toLowerCase()) {
            case "add":
                handleBlocklistAddSubcommand(sender, args, blocklistConfig, langConfig);
                break;
            case "remove":
                handleBlocklistRemoveSubcommand(sender, args, blocklistConfig, langConfig);
                break;
            default:
                sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("invalid-syntax") + " " + ChatColor.RESET + langConfig.getString("invalid-syntax-use") + " " + label + " blocklist <add|remove>");
                break;
        }
    }

    // Handles the "blocklist add" subcommand to add a player to the blocklist
    private void handleBlocklistAddSubcommand(CommandSender sender, String[] args, ConfigUtil blocklistConfig, ConfigUtil langConfig) {
        // Check if the player has the required permission
        if (!sender.hasPermission(Constants.PERMISSION_BLOCKLIST_ADD)) {
            sender.sendMessage(ChatColor.RED + langConfig.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 3) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("invalid-syntax") + " " + ChatColor.RESET + langConfig.getString("invalid-syntax-use") + " " + "blocklist add <player>");
            return;
        }

        // Check if the player is in the blocklist
        OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
        if (blocklistConfig.getConfig().contains(offlineTarget.getUniqueId().toString())) {
            String message = Constants.PLUGIN_PREFIX + langConfig.getString("trollplus.already-in-blocklist");
            sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));
            return;
        }

        // Add player to the blocklist
        blocklistConfig.getConfig().set(offlineTarget.getUniqueId().toString(), offlineTarget.getName());
        String message = Constants.PLUGIN_PREFIX + langConfig.getString("trollplus.added-to-blocklist");
        sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));

        // Save and reload the blocklist configuration
        blocklistConfig.save();
        blocklistConfig.reload();
    }

    // Handles the "blocklist remove" subcommand to remove a player from the blocklist
    private void handleBlocklistRemoveSubcommand(CommandSender sender, String[] args, ConfigUtil blocklistConfig, ConfigUtil langConfig) {
        // Check if the player has the required permission
        if (!sender.hasPermission(Constants.PERMISSION_BLOCKLIST_REMOVE)) {
            sender.sendMessage(ChatColor.RED + langConfig.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 3) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("invalid-syntax") + " " + ChatColor.RESET + langConfig.getString("invalid-syntax-use") + " " + "blocklist remove <player>");
            return;
        }

        // Check if the player is in the blocklist
        OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
        if (!blocklistConfig.getConfig().contains(offlineTarget.getUniqueId().toString())) {
            String message = Constants.PLUGIN_PREFIX + langConfig.getString("trollplus.not-in-blocklist");
            sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));
            return;
        }

        // Remove player to the blocklist
        blocklistConfig.getConfig().set(offlineTarget.getUniqueId().toString(), null);
        String message = Constants.PLUGIN_PREFIX + langConfig.getString("trollplus.removed-from-blocklist");
        sender.sendMessage(message.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));

        // Save and reload the blocklist configuration
        blocklistConfig.save();
        blocklistConfig.reload();
    }

    // Handles the "settings" subcommand to open the settings GUI for a player
    private void handleSettingsSubcommand(CommandSender sender, String[] args, ConfigUtil langConfig) {
        // Check if the command sender is not a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("no-console"));
            return;
        }

        // Check if the player has the required permission
        if (!sender.hasPermission(Constants.PERMISSION_SETTINGS)) {
            sender.sendMessage(ChatColor.RED + langConfig.getString("no-permission"));
            return;
        }

        // Check if the command syntax is correct
        if (args.length != 1) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.RED + langConfig.getString("invalid-syntax") + " " + ChatColor.RESET + langConfig.getString("invalid-syntax-use") + " settings");
            return;
        }

        Player player = (Player) sender;
        settingsGUI = new GUIUtil(langConfig.getString("trollsettings.title"), 27, plugin);

        // Add the available settings to the GUI
        settingsGUI.addItem(26, ItemBuilder.createItemWithLore(Material.BARRIER, ChatColor.RED + langConfig.getString("trollsettings.close"), langConfig.getString("trollsettings.close-description")));
        settingsGUI.addItem(10, ItemBuilder.createItemWithLore(Material.PAPER, ChatColor.WHITE + langConfig.getString("trollsettings.language") + ChatColor.DARK_GRAY + " " + plugin.getConfig().getString("language"), langConfig.getString("trollsettings.language-description")));
        settingsGUI.addItem(11, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + langConfig.getString("trollsettings.metrics-enabled") + ChatColor.DARK_GRAY + " " + settingsGUI.getStatusSettingsGUI(plugin.getConfig().getBoolean("metrics-enabled")), langConfig.getString("trollsettings.metrics-enabled-description")));
        settingsGUI.addItem(12, ItemBuilder.createItemWithLore(Material.GLOWSTONE, ChatColor.WHITE + langConfig.getString("trollsettings.check-for-updates") + ChatColor.DARK_GRAY + " " + settingsGUI.getStatusSettingsGUI(plugin.getConfig().getBoolean("check-for-updates")), langConfig.getString("trollsettings.check-for-updates-description")));
        settingsGUI.addItem(13, ItemBuilder.createItemWithLore(Material.REDSTONE_LAMP, ChatColor.WHITE + langConfig.getString("trollsettings.deactivate-features-on-quit") + ChatColor.DARK_GRAY + " " + settingsGUI.getStatusSettingsGUI(plugin.getConfig().getBoolean("deactivate-features-on-quit")), langConfig.getString("trollsettings.deactivate-features-on-quit-description")));
        settingsGUI.addItem(14, ItemBuilder.createItemWithLore(Material.ENDER_PEARL, ChatColor.WHITE + langConfig.getString("trollsettings.control-teleport-back") + ChatColor.DARK_GRAY + " " + settingsGUI.getStatusSettingsGUI(plugin.getConfig().getBoolean("control-teleport-back")), langConfig.getString("trollsettings.control-teleport-back-description")));
        settingsGUI.addItem(15, ItemBuilder.createItemWithLore(Material.FIRE_CHARGE, ChatColor.WHITE + langConfig.getString("trollsettings.set-fire") + ChatColor.DARK_GRAY + " " + settingsGUI.getStatusSettingsGUI(plugin.getConfig().getBoolean("set-fire")), langConfig.getString("trollsettings.set-fire-description")));
        settingsGUI.addItem(16, ItemBuilder.createItemWithLore(Material.DIAMOND_PICKAXE, ChatColor.WHITE + langConfig.getString("trollsettings.break-blocks") + ChatColor.DARK_GRAY + " " + settingsGUI.getStatusSettingsGUI(plugin.getConfig().getBoolean("break-blocks")), langConfig.getString("trollsettings.break-blocks-description")));

        // Add placeholders
        byte[] placeholderArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25};
        for (int slot : placeholderArray) {
            settingsGUI.addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", langConfig.getString("guis.placeholder-description")));
        }

        // Open the Settings GUI for the player
        player.openInventory(settingsGUI.getGUI());
    }
}