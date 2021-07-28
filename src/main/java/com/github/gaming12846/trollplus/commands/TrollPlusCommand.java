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

import java.util.Arrays;

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
                sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command!");
                return true;
            }

            plugin.reloadConfig();
            plugin.getBlocklistConfig().reload();

            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.GREEN + "Configuration and blocklist successfully reloaded.");
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
                    sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command!");
                    return true;
                }

                if (args.length < 3 || args.length > 3) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " blocklist add <player>");
                    return true;
                }

                ConfigWrapper blocklistConfigWrapper = plugin.getBlocklistConfig();
                FileConfiguration blocklistConfig = blocklistConfigWrapper.asRawConfig();

                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
                if (blocklistConfig.contains(offlineTarget.getUniqueId().toString())) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + offlineTarget.getName() + ChatColor.RESET + " is already in the blocklist.");
                    return true;
                }

                blocklistConfig.set(offlineTarget.getUniqueId().toString(), offlineTarget.getName());
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + offlineTarget.getName() + ChatColor.RESET + " has been added to the blocklist.");

                blocklistConfigWrapper.save();
                blocklistConfigWrapper.reload();
            }

            // Blocklist remove subcommand
            else if (args[1].equalsIgnoreCase("remove")) {
                if (!sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_REMOVE)) {
                    sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command!");
                    return true;
                }

                if (args.length < 3 || args.length > 3) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " blocklist remove <player>");
                    return true;
                }

                ConfigWrapper blocklistConfigWrapper = plugin.getBlocklistConfig();
                FileConfiguration blocklistConfig = blocklistConfigWrapper.asRawConfig();

                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[2]);
                if (!blocklistConfig.contains(offlineTarget.getUniqueId().toString())) {
                    sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + offlineTarget.getName() + ChatColor.RESET + " is not in the blocklist.");
                    return true;
                }

                blocklistConfig.set(offlineTarget.getUniqueId().toString(), null);
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + offlineTarget.getName() + ChatColor.RESET + " has been removed from the blocklist.");

                blocklistConfigWrapper.save();
                blocklistConfigWrapper.reload();
            }

        }

        // Troll subcommand
        else if (args[0].equalsIgnoreCase("troll")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("TrollPlus " + label + " subcommand cannot be used from the console.");
                return true;
            }

            if (!sender.hasPermission(VMConstants.PERMISSION_TROLL)) {
                sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command!");
                return true;
            }

            if (args.length < 2 || args.length > 2) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " troll <player>");
                return true;
            }

            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + args[1] + ChatColor.RESET + " is currently not online.");
                return true;
            }

            ConfigWrapper blocklistConfigWrapper = plugin.getBlocklistConfig();
            FileConfiguration blocklistConfig = blocklistConfigWrapper.asRawConfig();

            if (blocklistConfig.contains(target.getUniqueId().toString())) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + target.getName() + ChatColor.RESET + " is immune.");
                return true;
            }

            // Set target
            VMConstants.TARGET = target;

            // Create inventory
            VMConstants.TROLLMENU = Bukkit.createInventory(null, 54, "Trollmenu " + ChatColor.GOLD + ChatColor.BOLD + target.getName());

            // Add features
            VMConstants.TROLLMENU.setItem(53, ItemBuilder.createItemWithLore(Material.BARRIER, 1, 0, ChatColor.RED + "Close", Arrays.asList("Close the troll menu")));
            VMConstants.TROLLMENU.setItem(51, ItemBuilder.createItemWithLore(Material.CHEST, 1, 0, ChatColor.WHITE + "Invsee", Arrays.asList("Open the target inventory")));
            VMConstants.TROLLMENU.setItem(50, ItemBuilder.createItemWithLore(Material.WITHER_SKELETON_SKULL, 1, 0, ChatColor.WHITE + "Kill", Arrays.asList("Kill the target")));
            VMConstants.TROLLMENU.setItem(48, ItemBuilder.createItemWithLore(Material.ENDER_PEARL, 1, 0, ChatColor.WHITE + "Teleport", Arrays.asList("Teleport to the target")));
            VMConstants.TROLLMENU.setItem(47, ItemBuilder.createItemWithLore(Material.POTION, 1, 0, ChatColor.WHITE + "Vanish " + VMConstants.STATUS_VANISH, Arrays.asList("Disappear for the target or for all players")));
            VMConstants.TROLLMENU.setItem(4, ItemBuilder.createSkull(1, 3, ChatColor.GOLD + target.getName(), target.getName()));

            VMConstants.TROLLMENU.setItem(10, ItemBuilder.createItemWithLore(Material.ICE, 1, 0, ChatColor.WHITE + "Freeze " + VMConstants.STATUS_FREEZE, Arrays.asList("Freeze the target")));
            VMConstants.TROLLMENU.setItem(12,
                    ItemBuilder.createItemWithLore(Material.SHEARS, 1, 0, ChatColor.WHITE + "Hand item drop " + VMConstants.STATUS_HAND_ITEM_DROP, Arrays.asList("Automatic dropping of the hand item from the target")));
            VMConstants.TROLLMENU.setItem(14, ItemBuilder.createItemWithLore(Material.LEAD, 1, 0, ChatColor.WHITE + "Control " + VMConstants.STATUS_CONTROL, Arrays.asList("Completely control the target")));
            VMConstants.TROLLMENU.setItem(16,
                    ItemBuilder.createItemWithLore(Material.COMPASS, 1, 0, ChatColor.WHITE + "Flip backwards " + VMConstants.STATUS_FLIP_BEHIND, Arrays.asList("Flip the target backwards when interacting with something")));
            VMConstants.TROLLMENU.setItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, 1, 0,
                    ChatColor.WHITE + "Spam messages " + VMConstants.STATUS_SPAM_MESSAGES, Arrays.asList("Spam the target with random custom messages")));
            VMConstants.TROLLMENU.setItem(22,
                    ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, 1, 0, ChatColor.WHITE + "Spam sounds " + VMConstants.STATUS_SPAM_SOUNDS, Arrays.asList("Spam the target with random sounds")));
            VMConstants.TROLLMENU.setItem(24,
                    ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, 1, 0, ChatColor.WHITE + "Semi ban " + VMConstants.STATUS_SEMI_BAN, Arrays.asList("Prevents the target from building, interacting, causing damage and writing")));
            VMConstants.TROLLMENU.setItem(28,
                    ItemBuilder.createItemWithLore(Material.TNT, 1, 0, ChatColor.WHITE + "TNT track " + VMConstants.STATUS_TNT_TRACK, Arrays.asList("Spawn primed TNT at the target")));
            VMConstants.TROLLMENU.setItem(30,
                    ItemBuilder.createItemWithLore(Material.SPAWNER, 1, 0, ChatColor.WHITE + "Mob spawner " + VMConstants.STATUS_MOB_SPAWNER, Arrays.asList("Spawn random mobs at the target")));
            VMConstants.TROLLMENU.setItem(34, ItemBuilder.createItemWithLore(Material.MUSIC_DISC_11, 1, 0, ChatColor.WHITE + "Random scary sound", Arrays.asList("Play a random scary sound to scare the target")));
            VMConstants.TROLLMENU.setItem(38, ItemBuilder.createItemWithLore(Material.FIREWORK_ROCKET, 1, 0, ChatColor.WHITE + "Rocket", Arrays.asList("Launch the target in the air YEET")));
            VMConstants.TROLLMENU.setItem(40, ItemBuilder.createItemWithLore(Material.PAPER, 1, 0, ChatColor.WHITE + "Fake ban", Arrays.asList("Make the target think they got banned")));
            VMConstants.TROLLMENU.setItem(42, ItemBuilder.createItemWithLore(Material.ENCHANTED_GOLDEN_APPLE, 1, 0, ChatColor.WHITE + "Fake op", Arrays.asList("Make the target think they got op")));

            // Placeholder
            int[] placeholderArray = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 49, 52};
            for (int slot : placeholderArray) {
                VMConstants.TROLLMENU.setItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, 1, 0, " ", Arrays.asList("Just lonely placeholders :(")));
            }

            player.openInventory(VMConstants.TROLLMENU);
        }

        // Unknown command usage
        else {
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " <version|reload|blocklist|troll>");
            return true;
        }

        return true;
    }

}