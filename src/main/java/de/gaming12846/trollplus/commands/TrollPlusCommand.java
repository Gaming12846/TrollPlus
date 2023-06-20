/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class TrollPlusCommand implements CommandExecutor {
    private final TrollPlus plugin;
    public GUIUtil settingsGUI;

    public TrollPlusCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Constants.PLUGIN_INVALID_SYNTAX + label + " <version|reload|blocklist|settings>");
            return true;
        }

        FileConfiguration langConfig = plugin.getLanguageConfig().getConfig();

        // Version subcommand
        if (args[0].equalsIgnoreCase("version")) {
            if (!sender.hasPermission(Constants.PERMISSION_VERSION)) {
                sender.sendMessage(Constants.PLUGIN_NO_PERMISSION);
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage(Constants.PLUGIN_INVALID_SYNTAX + label + " version");
                return true;
            }

            PluginDescriptionFile description = plugin.getDescription();
            String headerFooter = ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + StringUtils.repeat("-", 44);

            sender.sendMessage(headerFooter);
            sender.sendMessage("");
            sender.sendMessage(ChatColor.RED + langConfig.getString("trollplus.version") + " " + ChatColor.WHITE + description.getVersion());
            sender.sendMessage(ChatColor.RED + langConfig.getString("trollplus.developer") + " " + ChatColor.WHITE + description.getAuthors().get(0));
            sender.sendMessage(ChatColor.RED + langConfig.getString("trollplus.plugin-website") + " " + ChatColor.WHITE + description.getWebsite());
            sender.sendMessage(ChatColor.RED + langConfig.getString("trollplus.report-bugs") + ChatColor.WHITE + " https://github.com/Gaming12846/TrollPlus/issues");
            sender.sendMessage("");
            sender.sendMessage(headerFooter);

            if (plugin.updateAvailable) {
                sender.sendMessage(langConfig.getString("update-available") + " https://www.spigotmc.org/resources/81193");
            }
        }
        // Reload subcommand
        else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission(Constants.PERMISSION_RELOAD)) {
                sender.sendMessage(Constants.PLUGIN_NO_PERMISSION);
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage(Constants.PLUGIN_INVALID_SYNTAX + label + " reload");
                return true;
            }

            plugin.reloadConfig();
            plugin.getBlocklistConfig().reload();
            plugin.getLanguageConfig().reload();

            sender.sendMessage(Constants.PLUGIN_PREFIX + ChatColor.GREEN + langConfig.getString("trollplus.reload"));
        }
        // Blocklist subcommand
        else if (args[0].equalsIgnoreCase("blocklist")) {
            if (!sender.hasPermission(Constants.PERMISSION_BLOCKLIST_ADD) || !sender.hasPermission(Constants.PERMISSION_BLOCKLIST_REMOVE)) {
                sender.sendMessage(Constants.PLUGIN_NO_PERMISSION);
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage(Constants.PLUGIN_INVALID_SYNTAX + label + " blocklist <add|remove>");
                return true;
            }

            ConfigUtil blocklistConfig = plugin.getBlocklistConfig();

            // Blocklist add subcommand
            if (args[1].equalsIgnoreCase("add")) {
                if (!sender.hasPermission(Constants.PERMISSION_BLOCKLIST_ADD)) {
                    sender.sendMessage(Constants.PLUGIN_NO_PERMISSION);
                    return true;
                }

                if (args.length != 3) {
                    sender.sendMessage(Constants.PLUGIN_INVALID_SYNTAX + label + " blocklist add <player>");
                    return true;
                }

                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
                if (blocklistConfig.getConfig().contains(offlineTarget.getUniqueId().toString())) {
                    String alreadyInBlocklistReplace = Constants.PLUGIN_PREFIX + plugin.getLanguageConfig().getConfig().getString("trollplus.already-in-blocklist");
                    sender.sendMessage(alreadyInBlocklistReplace.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));
                    return true;
                }

                blocklistConfig.getConfig().set(offlineTarget.getUniqueId().toString(), offlineTarget.getName());
                String addedToBlocklistReplace = Constants.PLUGIN_PREFIX + plugin.getLanguageConfig().getConfig().getString("trollplus.added-to-blocklist");
                sender.sendMessage(addedToBlocklistReplace.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));

                blocklistConfig.save();
                blocklistConfig.reload();
            }
            // Blocklist remove subcommand
            else if (args[1].equalsIgnoreCase("remove")) {
                if (!sender.hasPermission(Constants.PERMISSION_BLOCKLIST_REMOVE)) {
                    sender.sendMessage(Constants.PLUGIN_NO_PERMISSION);
                    return true;
                }

                if (args.length != 3) {
                    sender.sendMessage(Constants.PLUGIN_INVALID_SYNTAX + label + " blocklist remove <player>");
                    return true;

                }

                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
                if (!blocklistConfig.getConfig().contains(offlineTarget.getUniqueId().toString())) {
                    String notInBlocklistReplace = Constants.PLUGIN_PREFIX + plugin.getLanguageConfig().getConfig().getString("trollplus.not-in-blocklist");
                    sender.sendMessage(notInBlocklistReplace.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));
                    return true;
                }

                blocklistConfig.getConfig().set(offlineTarget.getUniqueId().toString(), null);
                String removedFromBlocklistReplace = Constants.PLUGIN_PREFIX + plugin.getLanguageConfig().getConfig().getString("trollplus.removed-from-blocklist");
                sender.sendMessage(removedFromBlocklistReplace.replace("[player]", ChatColor.RED + ChatColor.BOLD.toString() + offlineTarget.getName() + ChatColor.RESET));

                blocklistConfig.save();
                blocklistConfig.reload();
            }
            // Settings subcommand
        } else if (args[0].equalsIgnoreCase("settings")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Constants.PLUGIN_PREFIX + label + " settings" + Constants.PLUGIN_NO_CONSOLE);
                return true;
            }

            if (!sender.hasPermission(Constants.PERMISSION_SETTINGS)) {
                sender.sendMessage(Constants.PLUGIN_NO_PERMISSION);
                return true;
            }

            Player player = (Player) sender;

            // Create troll GUI
            settingsGUI = new GUIUtil("TrollPlus settings", 27, plugin);

            settingsGUI.addItem(26, ItemBuilder.createItemWithLore(Material.BARRIER, ChatColor.RED + langConfig.getString("trollsettings.close"), langConfig.getString("trollsettings.close-description")));

            settingsGUI.addItem(10, ItemBuilder.createItemWithLore(Material.PAPER, ChatColor.WHITE + langConfig.getString("trollsettings.language") + ChatColor.DARK_GRAY + " " + plugin.getConfig().getString("language"), langConfig.getString("trollsettings.language-description")));
            settingsGUI.addItem(11, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + langConfig.getString("trollsettings.metrics-enabled") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("metrics-enabled")), langConfig.getString("trollsettings.metrics-enabled-description")));
            settingsGUI.addItem(12, ItemBuilder.createItemWithLore(Material.GLOWSTONE, ChatColor.WHITE + langConfig.getString("trollsettings.check-for-updates") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("check-for-updates")), langConfig.getString("trollsettings.check-for-updates-description")));
            settingsGUI.addItem(13, ItemBuilder.createItemWithLore(Material.REDSTONE_LAMP, ChatColor.WHITE + langConfig.getString("trollsettings.deactivate-features-on-quit") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("deactivate-features-on-quit")), langConfig.getString("trollsettings.deactivate-features-on-quit-description")));
            settingsGUI.addItem(14, ItemBuilder.createItemWithLore(Material.ENDER_PEARL, ChatColor.WHITE + langConfig.getString("trollsettings.control-teleport-back") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("control-teleport-back")), langConfig.getString("trollsettings.control-teleport-back-description")));
            settingsGUI.addItem(15, ItemBuilder.createItemWithLore(Material.FIRE_CHARGE, ChatColor.WHITE + langConfig.getString("trollsettings.set-fire") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("set-fire")), langConfig.getString("trollsettings.set-fire-description")));
            settingsGUI.addItem(16, ItemBuilder.createItemWithLore(Material.DIAMOND_PICKAXE, ChatColor.WHITE + langConfig.getString("trollsettings.break-blocks") + ChatColor.DARK_GRAY + " " + settingsGUI.getConfigStatus(plugin.getConfig().getBoolean("break-blocks")), langConfig.getString("trollsettings.break-blocks-description")));

            // Placeholders
            byte[] placeholderArray = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25};
            for (int slot : placeholderArray) {
                settingsGUI.addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", langConfig.getString("guis.placeholder-description")));
            }

            player.openInventory(settingsGUI.getGUI());
        }
        // Unknown command usage
        else {
            sender.sendMessage(Constants.PLUGIN_INVALID_SYNTAX + label + " <version|reload|blocklist|settings>");
            return true;
        }

        return true;
    }
}