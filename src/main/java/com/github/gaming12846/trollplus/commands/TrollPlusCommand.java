package com.github.gaming12846.trollplus.commands;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.ConfigWrapper;
import com.github.gaming12846.trollplus.utils.VMConstants;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

/**
 * TrollPlus com.github.gaming12846.trollplus.commands TrollPlusCommand.java
 *
 * @author Gaming12846
 */
public class TrollPlusCommand implements CommandExecutor {

    private final TrollPlus plugin;

    public TrollPlusCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " <version|reload|blocklist>");
            return true;
        }

        // Version subcommand
        if (args[0].equalsIgnoreCase("version")) {
            if (args.length != 1) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " version");
                return true;
            }

            PluginDescriptionFile description = plugin.getDescription();
            String headerFooter = ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 44);

            sender.sendMessage(headerFooter);
            sender.sendMessage("");
            sender.sendMessage(ChatColor.RED + "Version: " + ChatColor.WHITE + description.getVersion());
            sender.sendMessage(ChatColor.RED + "Developer: " + ChatColor.WHITE + description.getAuthors().get(0));
            sender.sendMessage(ChatColor.RED + "Plugin website: " + ChatColor.WHITE + description.getWebsite());
            sender.sendMessage(ChatColor.RED + "Report bugs to: " + ChatColor.WHITE + "https://github.com/Gaming12846/TrollPlus/issues/");
            sender.sendMessage("");
            sender.sendMessage(headerFooter);

            if (plugin.updateAvailable) {
                sender.sendMessage("");
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + "A new update is available! To download it visit SpigotMC: https://www.spigotmc.org/resources/81193/");
            }
        }

        // Reload subcommand
        else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission(VMConstants.PERMISSION_ALL) || !sender.hasPermission(VMConstants.PERMISSION_RELOAD)) {
                sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command");
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " reload");
                return true;
            }

            plugin.reloadConfig();
            plugin.getBlocklistConfig().reload();

            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.GREEN + "Configuration and blocklist successfully reloaded");
        }

        // Blocklist subcommand
        else if (args[0].equalsIgnoreCase("blocklist")) {
            if (!sender.hasPermission(VMConstants.PERMISSION_ALL) || !sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_ALL) || !sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_ADD) || !sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_REMOVE)) {
                sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command");
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " blocklist <add|remove>");
                return true;
            }

            // Blocklist add subcommand
            if (args[1].equalsIgnoreCase("add")) {
                if (!sender.hasPermission(VMConstants.PERMISSION_ALL) || !sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_ALL) || !sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_ADD)) {
                    sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command");
                    return true;
                }

                if (args.length != 3) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " blocklist add <player>");
                    return true;
                }

                ConfigWrapper blocklistConfigWrapper = plugin.getBlocklistConfig();
                FileConfiguration blocklistConfig = blocklistConfigWrapper.asRawConfig();

                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
                if (blocklistConfig.contains(offlineTarget.getUniqueId().toString())) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + offlineTarget.getName() + ChatColor.RESET + " is already in the blocklist");
                    return true;
                }

                blocklistConfig.set(offlineTarget.getUniqueId().toString(), offlineTarget.getName());
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + offlineTarget.getName() + ChatColor.RESET + " has been added to the blocklist");

                blocklistConfigWrapper.save();
                blocklistConfigWrapper.reload();
            }

            // Blocklist remove subcommand
            else if (args[1].equalsIgnoreCase("remove")) {
                if (!sender.hasPermission(VMConstants.PERMISSION_ALL) || !sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_ALL) || !sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_REMOVE)) {
                    sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command");
                    return true;
                }

                if (args.length != 3) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " blocklist remove <player>");
                    return true;
                }

                ConfigWrapper blocklistConfigWrapper = plugin.getBlocklistConfig();
                FileConfiguration blocklistConfig = blocklistConfigWrapper.asRawConfig();


                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
                if (!blocklistConfig.contains(offlineTarget.getUniqueId().toString())) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + offlineTarget.getName() + ChatColor.RESET + " is not in the blocklist");
                    return true;
                }

                blocklistConfig.set(offlineTarget.getUniqueId().toString(), null);
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + offlineTarget.getName() + ChatColor.RESET + " has been removed from the blocklist");

                blocklistConfigWrapper.save();
                blocklistConfigWrapper.reload();
            }

        }

        // Unknown command usage
        else {
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " <version|reload|blocklist>");
            return true;
        }

        return true;
    }

}