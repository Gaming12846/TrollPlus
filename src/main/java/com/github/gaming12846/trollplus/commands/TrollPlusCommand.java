package com.github.gaming12846.trollplus.commands;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.ConfigWrapper;
import com.github.gaming12846.trollplus.utils.ItemBuilder;
import com.github.gaming12846.trollplus.utils.VMConstants;
import org.apache.commons.lang.StringUtils;
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

/**
 * TrollPlus com.github.gaming12846.trollplus.commands TrollPlusCommand.java
 *
 * @author Gaming12846
 */
public final class TrollPlusCommand implements CommandExecutor {

    private final TrollPlus plugin;

    public TrollPlusCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " <version|reload|blocklist|troll>");
            return true;
        }

        // Version subcommand
        if (args[0].equalsIgnoreCase("version")) {
            if (args.length < 1 || args.length > 1) {
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
            sender.sendMessage(ChatColor.RED + "Report bugs to: " + ChatColor.WHITE + "https://github.com/Gaming12846/TrollPlus/issues");
            sender.sendMessage("");
            sender.sendMessage(headerFooter);
        }

        // Reload subcommand
        else if (args[0].equalsIgnoreCase("reload")) {
            if (args.length < 1 || args.length > 1) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " reload");
                return true;
            }

            if (!sender.hasPermission(VMConstants.PERMISSION_RELOAD)) {
                sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command");
                return true;
            }

            plugin.reloadConfig();
            plugin.getBlocklistConfig().reload();

            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.GREEN + "Configuration successfully reloaded");
        }

        // Blocklist subcommand
        else if (args[0].equalsIgnoreCase("blocklist")) {
            if (args.length < 2) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " blocklist <add|remove>");
                return true;
            }

            // Blocklist add subcommand
            if (args[1].equalsIgnoreCase("add")) {
                if (!sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_ADD)) {
                    sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command");
                    return true;
                }

                if (args.length < 3 || args.length > 3) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " blocklist add <player>");
                    return true;
                }

                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
                if (!(offlineTarget instanceof Player)) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The target " + ChatColor.BOLD + args[2] + ChatColor.RESET + " is not a player");
                    return true;
                }

                ConfigWrapper blocklistConfigWrapper = plugin.getBlocklistConfig();
                FileConfiguration blocklistConfig = blocklistConfigWrapper.asRawConfig();

                if (blocklistConfig.contains(offlineTarget.getUniqueId().toString())) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + offlineTarget.getName() + ChatColor.RESET + " is allready in the blocklist");
                    return true;
                }

                blocklistConfig.set(offlineTarget.getUniqueId().toString(), offlineTarget.getName());
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + offlineTarget.getName() + ChatColor.RESET + " has been added to the blocklist");

                blocklistConfigWrapper.save();
                blocklistConfigWrapper.reload();
            }

            // Blocklist remove subcommand
            else if (args[1].equalsIgnoreCase("remove")) {
                if (!sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_REMOVE)) {
                    sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command");
                    return true;
                }

                if (args.length < 3 || args.length > 3) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " blocklist remove <player>");
                    return true;
                }

                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
                if (!(offlineTarget instanceof Player)) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The target " + ChatColor.BOLD + args[2] + ChatColor.RESET + " is not a player");
                    return true;
                }

                ConfigWrapper blocklistConfigWrapper = plugin.getBlocklistConfig();
                FileConfiguration blocklistConfig = blocklistConfigWrapper.asRawConfig();

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

        // Troll subcommand
        else if (args[0].equalsIgnoreCase("troll")) {
            if (!sender.hasPermission(VMConstants.PERMISSION_TROLL)) {
                sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command");
                return true;
            }

            if (args.length < 2 || args.length > 2) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " troll <player>");
                return true;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage("TrollPlus " + label + " subcommand cannot be used from the console");
                return true;
            }

            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + target.getName() + ChatColor.RESET + " is currently not online");
                return true;
            }

            ConfigWrapper blocklistConfigWrapper = plugin.getBlocklistConfig();
            FileConfiguration blocklistConfig = blocklistConfigWrapper.asRawConfig();

            if (blocklistConfig.contains(target.getUniqueId().toString())) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + target.getName() + ChatColor.RESET + " is immune");
                return true;
            }

            // Set target
            VMConstants.TARGET = target;

            // Create inventory
            VMConstants.TROLLMENU = Bukkit.createInventory(null, 54, "Trollmenu " + ChatColor.GOLD + ChatColor.BOLD + target.getName());

            // Add features
            VMConstants.TROLLMENU.setItem(53, ItemBuilder.createItem(Material.BARRIER, 1, 0, ChatColor.GRAY + "Close"));
            VMConstants.TROLLMENU.setItem(51, ItemBuilder.createItem(Material.CHEST, 1, 0, ChatColor.WHITE + "Invsee"));
            VMConstants.TROLLMENU.setItem(50, ItemBuilder.createItem(Material.WITHER_SKELETON_SKULL, 1, 0, ChatColor.WHITE + "Kill"));
            VMConstants.TROLLMENU.setItem(48, ItemBuilder.createItem(Material.ENDER_PEARL, 1, 0, ChatColor.WHITE + "Teleport to player"));
            VMConstants.TROLLMENU.setItem(47, ItemBuilder.createItem(Material.POTION, 1, 0, ChatColor.WHITE + "Vanish " + VMConstants.STATUS_VANISH));
            VMConstants.TROLLMENU.setItem(4, ItemBuilder.createSkull(1, 3, ChatColor.GOLD + target.getName(), target.getName()));

            VMConstants.TROLLMENU.setItem(10, ItemBuilder.createItem(Material.ICE, 1, 0, ChatColor.WHITE + "Freeze " + VMConstants.STATUS_FREEZE));
            VMConstants.TROLLMENU.setItem(12,
                    ItemBuilder.createItem(Material.SHEARS, 1, 0, ChatColor.WHITE + "Hand item drop " + VMConstants.STATUS_HAND_ITEM_DROP));
            VMConstants.TROLLMENU.setItem(14, ItemBuilder.createItem(Material.LEAD, 1, 0, ChatColor.WHITE + "Control " + VMConstants.STATUS_CONTROL));
            VMConstants.TROLLMENU.setItem(16,
                    ItemBuilder.createItem(Material.COMPASS, 1, 0, ChatColor.WHITE + "Flip behind " + VMConstants.STATUS_FLIP_BEHIND));
            VMConstants.TROLLMENU.setItem(20, ItemBuilder.createItem(Material.WRITABLE_BOOK, 1, 0,
                    ChatColor.WHITE + "Spam messages " + VMConstants.STATUS_SPAM_MESSAGES));
            VMConstants.TROLLMENU.setItem(22,
                    ItemBuilder.createItem(Material.NOTE_BLOCK, 1, 0, ChatColor.WHITE + "Spam sounds " + VMConstants.STATUS_SPAM_SOUNDS));
            VMConstants.TROLLMENU.setItem(24,
                    ItemBuilder.createItem(Material.TRIPWIRE_HOOK, 1, 0, ChatColor.WHITE + "Semi ban " + VMConstants.STATUS_SEMI_BAN));
            VMConstants.TROLLMENU.setItem(28,
                    ItemBuilder.createItem(Material.TNT, 1, 0, ChatColor.WHITE + "TNT track " + VMConstants.STATUS_TNT_TRACK));
            VMConstants.TROLLMENU.setItem(30,
                    ItemBuilder.createItem(Material.SPAWNER, 1, 0, ChatColor.WHITE + "Mob spawner " + VMConstants.STATUS_MOB_SPAWNER));
            VMConstants.TROLLMENU.setItem(34, ItemBuilder.createItem(Material.MUSIC_DISC_11, 1, 0, ChatColor.WHITE + "Random scary sound"));
            VMConstants.TROLLMENU.setItem(38, ItemBuilder.createItem(Material.FIREWORK_ROCKET, 1, 0, ChatColor.WHITE + "Rocket"));
            VMConstants.TROLLMENU.setItem(40, ItemBuilder.createItem(Material.PAPER, 1, 0, ChatColor.WHITE + "Fake ban"));
            VMConstants.TROLLMENU.setItem(42, ItemBuilder.createItem(Material.ENCHANTED_GOLDEN_APPLE, 1, 0, ChatColor.WHITE + "Fake op"));

            // Placeholder
            int[] placeholderArray = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 49, 52};
            for (int slot : placeholderArray) {
                VMConstants.TROLLMENU.setItem(slot, ItemBuilder.createItem(Material.RED_STAINED_GLASS_PANE, 1, 0, " "));
            }

            player.openInventory(VMConstants.TROLLMENU);
        }

        // Unknown command usage
        else {
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " <version|reload|blocklist|trollmenu>");
            return true;
        }

        return true;
    }

}