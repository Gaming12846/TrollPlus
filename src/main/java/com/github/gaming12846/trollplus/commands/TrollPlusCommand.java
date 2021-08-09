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
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.Collections;

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
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " <version|reload|blocklist|troll|bows>");
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
            sender.sendMessage(ChatColor.RED + "Report bugs to: " + ChatColor.WHITE + "https://github.com/Gaming12846/TrollPlus/issues");
            sender.sendMessage("");
            sender.sendMessage(headerFooter);

            if (plugin.updateAvailable) {
                sender.sendMessage("");
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + "A new update is available! To download it visit SpigotMC: https://www.spigotmc.org/resources/81193/");
            }
        }

        // Reload subcommand
        else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission(VMConstants.PERMISSION_RELOAD)) {
                sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command!");
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " reload");
                return true;
            }

            plugin.reloadConfig();
            plugin.getBlocklistConfig().reload();

            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.GREEN + "Configuration and blocklist successfully reloaded.");
        }

        // Blocklist subcommand
        else if (args[0].equalsIgnoreCase("blocklist")) {
            if (!sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_ADD) || !sender.hasPermission(VMConstants.PERMISSION_BLOCKLIST_REMOVE)) {
                sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command!");
                return true;
            }

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

                if (args.length != 3) {
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

                if (args.length != 3) {
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
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + args[0] + " subcommand cannot be used from the console.");
                return true;
            }

            if (!sender.hasPermission(VMConstants.PERMISSION_TROLL)) {
                sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command!");
                return true;
            }

            if (args.length != 2) {
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

            // Create troll menu
            VMConstants.TROLL_MENU = Bukkit.createInventory(null, 54, "Troll menu " + ChatColor.GOLD + ChatColor.BOLD + target.getName());

            // Add features
            VMConstants.TROLL_MENU.setItem(53, ItemBuilder.createItemWithLore(Material.BARRIER, 1, 0, ChatColor.RED + "Close", Collections.singletonList("Close the troll menu")));

            VMConstants.TROLL_MENU.setItem(51, ItemBuilder.createItemWithLore(Material.CHEST, 1, 0, ChatColor.WHITE + "Invsee", Collections.singletonList("Open the target inventory")));
            VMConstants.TROLL_MENU.setItem(50, ItemBuilder.createItemWithLore(Material.WITHER_SKELETON_SKULL, 1, 0, ChatColor.WHITE + "Kill", Collections.singletonList("Kill the target")));
            VMConstants.TROLL_MENU.setItem(48, ItemBuilder.createItemWithLore(Material.ENDER_PEARL, 1, 0, ChatColor.WHITE + "Teleport", Collections.singletonList("Teleport to the target")));
            VMConstants.TROLL_MENU.setItem(47, ItemBuilder.createItemWithLore(Material.POTION, 1, 0, ChatColor.WHITE + "Vanish " + VMConstants.STATUS_VANISH, Collections.singletonList("Disappear for the target or for all players")));
            VMConstants.TROLL_MENU.setItem(4, ItemBuilder.createSkull(1, 3, ChatColor.GOLD + target.getName(), target.getName()));

            VMConstants.TROLL_MENU.setItem(10, ItemBuilder.createItemWithLore(Material.ICE, 1, 0, ChatColor.WHITE + "Freeze " + VMConstants.STATUS_FREEZE, Collections.singletonList("Freeze the target")));
            VMConstants.TROLL_MENU.setItem(12,
                    ItemBuilder.createItemWithLore(Material.SHEARS, 1, 0, ChatColor.WHITE + "Hand item drop " + VMConstants.STATUS_HAND_ITEM_DROP, Collections.singletonList("Automatic dropping of the hand item from the target")));
            VMConstants.TROLL_MENU.setItem(14, ItemBuilder.createItemWithLore(Material.LEAD, 1, 0, ChatColor.WHITE + "Control " + VMConstants.STATUS_CONTROL, Collections.singletonList("Completely control the target")));
            VMConstants.TROLL_MENU.setItem(16,
                    ItemBuilder.createItemWithLore(Material.COMPASS, 1, 0, ChatColor.WHITE + "Flip backwards " + VMConstants.STATUS_FLIP_BEHIND, Collections.singletonList("Flip the target backwards when interacting with something")));
            VMConstants.TROLL_MENU.setItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, 1, 0,
                    ChatColor.WHITE + "Spam messages " + VMConstants.STATUS_SPAM_MESSAGES, Collections.singletonList("Spam the target with random custom messages")));
            VMConstants.TROLL_MENU.setItem(22,
                    ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, 1, 0, ChatColor.WHITE + "Spam sounds " + VMConstants.STATUS_SPAM_SOUNDS, Collections.singletonList("Spam the target with random sounds")));
            VMConstants.TROLL_MENU.setItem(24,
                    ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, 1, 0, ChatColor.WHITE + "Semi ban " + VMConstants.STATUS_SEMI_BAN, Collections.singletonList("Prevents the target from building, interacting, causing damage and writing")));
            VMConstants.TROLL_MENU.setItem(28,
                    ItemBuilder.createItemWithLore(Material.TNT, 1, 0, ChatColor.WHITE + "TNT track " + VMConstants.STATUS_TNT_TRACK, Collections.singletonList("Spawn primed TNT at the target")));
            VMConstants.TROLL_MENU.setItem(30,
                    ItemBuilder.createItemWithLore(Material.SPAWNER, 1, 0, ChatColor.WHITE + "Mob spawner " + VMConstants.STATUS_MOB_SPAWNER, Collections.singletonList("Spawn random mobs at the target")));
            VMConstants.TROLL_MENU.setItem(32,
                    ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, 1, 0, ChatColor.WHITE + "Slowly kill " + VMConstants.STATUS_SLOWLY_KILL, Collections.singletonList("Slowly kills the target")));
            VMConstants.TROLL_MENU.setItem(34, ItemBuilder.createItemWithLore(Material.MUSIC_DISC_11, 1, 0, ChatColor.WHITE + "Random scary sound", Collections.singletonList("Play a random scary sound to scare the target")));
            VMConstants.TROLL_MENU.setItem(38, ItemBuilder.createItemWithLore(Material.FIREWORK_ROCKET, 1, 0, ChatColor.WHITE + "Rocket", Collections.singletonList("Launch the target in the air YEET")));
            VMConstants.TROLL_MENU.setItem(40, ItemBuilder.createItemWithLore(Material.PAPER, 1, 0, ChatColor.WHITE + "Fake ban", Collections.singletonList("Make the target think they got banned")));
            VMConstants.TROLL_MENU.setItem(42, ItemBuilder.createItemWithLore(Material.ENCHANTED_GOLDEN_APPLE, 1, 0, ChatColor.WHITE + "Fake op", Collections.singletonList("Make the target think they got op")));

            // Placeholders
            int[] placeholderArray = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 49, 52};
            for (int slot : placeholderArray) {
                VMConstants.TROLL_MENU.setItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, 1, 0, " ", Collections.singletonList("Just lonely placeholders :(")));
            }

            // Placeholders
            int[] placeholderArray2 = new int[]{11, 13, 15, 19, 21, 23, 25, 29, 31, 33, 37, 39, 41, 43};
            for (int slot : placeholderArray2) {
                VMConstants.TROLL_MENU.setItem(slot, ItemBuilder.createItemWithLore(Material.GRAY_STAINED_GLASS_PANE, 1, 0, " ", Collections.singletonList("Just lonely placeholders :(")));
            }

            player.openInventory(VMConstants.TROLL_MENU);
        }

        // Bows subcommand
        else if (args[0].equalsIgnoreCase("bows")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + args[0] + " subcommand cannot be used from the console.");
                return true;
            }

            if (!sender.hasPermission(VMConstants.PERMISSION_BOWS)) {
                sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command!");
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " bows");
                return true;
            }

            // Create bows menu
            Inventory bows_menu = Bukkit.createInventory(null, 9, "Bows menu");

            // Add bows
            bows_menu.setItem(8, ItemBuilder.createItemWithLore(Material.BARRIER, 1, 0, ChatColor.RED + "Close", Collections.singletonList("Close the bow menu")));

            bows_menu.setItem(0, ItemBuilder.createBow(1, 0, "Explosion bow", Collections.singletonList("Gives the explosion bow")));
            bows_menu.setItem(1, ItemBuilder.createBow(1, 0, "TNT bow", Collections.singletonList("Gives the TNT bow")));
            bows_menu.setItem(2, ItemBuilder.createBow(1, 0, "Lightning bolt bow", Collections.singletonList("Gives the lightning bolt bow")));
            bows_menu.setItem(3, ItemBuilder.createBow(1, 0, "Silverfish bow", Collections.singletonList("Gives the silverfish bow")));

            // Placeholders
            int[] placeholderArray = new int[]{4, 5, 6, 7};
            for (int slot : placeholderArray) {
                bows_menu.setItem(slot, ItemBuilder.createItemWithLore(Material.GRAY_STAINED_GLASS_PANE, 1, 0, " ", Collections.singletonList("Just lonely placeholders :(")));
            }

            Player player = (Player) sender;
            player.openInventory(bows_menu);
        }

        // Unknown command usage
        else {
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " <version|reload|blocklist|troll|bows>");
            return true;
        }

        return true;
    }

}