package com.github.gaming12846.trollplus.commands;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.ConfigWrapper;
import com.github.gaming12846.trollplus.utils.ItemBuilder;
import com.github.gaming12846.trollplus.utils.VMConstants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Collections;

/**
 * TrollPlus com.github.gaming12846.trollplus.commands TrollCommand.java
 *
 * @author Gaming12846
 */
public class TrollCommand implements CommandExecutor {

    private final TrollPlus plugin;

    public TrollCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + label + " command cannot be used from the console");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission(VMConstants.PERMISSION_ALL) || !sender.hasPermission(VMConstants.PERMISSION_TROLL)) {
            sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label + " <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + args[0] + ChatColor.RESET + " is currently not online");
            return true;
        }

        ConfigWrapper blocklistConfigWrapper = plugin.getBlocklistConfig();
        FileConfiguration blocklistConfig = blocklistConfigWrapper.asRawConfig();

        if (blocklistConfig.contains(target.getUniqueId().toString()) && !player.hasPermission(VMConstants.PERMISSION_IGNORE_IMMUNE)) {
            player.sendMessage(VMConstants.PLUGIN_PREFIX + "The player " + ChatColor.BOLD + target.getName() + ChatColor.RESET + " is immune");
            return true;
        }

        // Set target
        VMConstants.TARGET = target;

        // Create troll menu
        VMConstants.TROLL_MENU = Bukkit.createInventory(null, 54, "Troll " + ChatColor.GOLD + ChatColor.BOLD + target.getName());

        // Add features
        VMConstants.TROLL_MENU.setItem(4, ItemBuilder.createSkull(1, ChatColor.GOLD + target.getName(), target.getPlayer()));
        VMConstants.TROLL_MENU.setItem(47, ItemBuilder.createItemWithLore(Material.POTION, 1, ChatColor.WHITE + "Vanish " + VMConstants.STATUS_VANISH, Collections.singletonList("Disappear for the target or for all players")));
        VMConstants.TROLL_MENU.setItem(48, ItemBuilder.createItemWithLore(Material.ENDER_PEARL, 1, ChatColor.WHITE + "Teleport", Collections.singletonList("Teleport to the target")));
        VMConstants.TROLL_MENU.setItem(49, ItemBuilder.createItemWithLore(Material.WITHER_SKELETON_SKULL, 1, ChatColor.WHITE + "Kill", Collections.singletonList("Kill the target")));
        VMConstants.TROLL_MENU.setItem(50, ItemBuilder.createItemWithLore(Material.CHEST, 1, ChatColor.WHITE + "Invsee", Collections.singletonList("Open the target inventory")));
        VMConstants.TROLL_MENU.setItem(51, ItemBuilder.createItemWithLore(Material.ENDER_CHEST, 1, ChatColor.WHITE + "Invsee enderchest", Collections.singletonList("Open the target enderchest")));
        VMConstants.TROLL_MENU.setItem(53, ItemBuilder.createItemWithLore(Material.BARRIER, 1, ChatColor.RED + "Close", Collections.singletonList("Close the troll menu")));

        VMConstants.TROLL_MENU.setItem(10, ItemBuilder.createItemWithLore(Material.ICE, 1, ChatColor.WHITE + "Freeze " + VMConstants.STATUS_FREEZE, Collections.singletonList("Freeze the target")));
        VMConstants.TROLL_MENU.setItem(12, ItemBuilder.createItemWithLore(Material.SHEARS, 1, ChatColor.WHITE + "Hand item drop " + VMConstants.STATUS_HAND_ITEM_DROP, Collections.singletonList("Automatic dropping of the hand item from the target")));
        VMConstants.TROLL_MENU.setItem(14, ItemBuilder.createItemWithLore(Material.LEAD, 1, ChatColor.WHITE + "Control " + VMConstants.STATUS_CONTROL, Collections.singletonList("Completely control the target")));
        VMConstants.TROLL_MENU.setItem(16, ItemBuilder.createItemWithLore(Material.COMPASS, 1, ChatColor.WHITE + "Flip backwards " + VMConstants.STATUS_FLIP_BEHIND, Collections.singletonList("Flip the target backwards when interacting with something")));
        VMConstants.TROLL_MENU.setItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, 1, ChatColor.WHITE + "Spam messages " + VMConstants.STATUS_SPAM_MESSAGES, Collections.singletonList("Spam the target with random custom messages")));
        VMConstants.TROLL_MENU.setItem(22, ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, 1, ChatColor.WHITE + "Spam sounds " + VMConstants.STATUS_SPAM_SOUNDS, Collections.singletonList("Spam the target with random sounds")));
        VMConstants.TROLL_MENU.setItem(24, ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, 1, ChatColor.WHITE + "Semi ban " + VMConstants.STATUS_SEMI_BAN, Collections.singletonList("Prevents the target from building, interacting, causing damage and writing")));
        VMConstants.TROLL_MENU.setItem(28, ItemBuilder.createItemWithLore(Material.TNT, 1, ChatColor.WHITE + "TNT track " + VMConstants.STATUS_TNT_TRACK, Collections.singletonList("Spawn primed TNT at the target")));
        VMConstants.TROLL_MENU.setItem(30, ItemBuilder.createItemWithLore(Material.SPAWNER, 1, ChatColor.WHITE + "Mob spawner " + VMConstants.STATUS_MOB_SPAWNER, Collections.singletonList("Spawn random mobs at the target")));
        VMConstants.TROLL_MENU.setItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, 1, ChatColor.WHITE + "Slowly kill " + VMConstants.STATUS_SLOWLY_KILL, Collections.singletonList("Slowly kills the target")));
        VMConstants.TROLL_MENU.setItem(34, ItemBuilder.createItemWithLore(Material.MUSIC_DISC_11, 1, ChatColor.WHITE + "Random scary sound", Collections.singletonList("Play a random scary sound to scare the target")));
        VMConstants.TROLL_MENU.setItem(36, ItemBuilder.createItemWithLore(Material.EGG, 1, ChatColor.WHITE + "Inventory drop", Collections.singletonList("Drop all items in the inventory from the target")));
        VMConstants.TROLL_MENU.setItem(38, ItemBuilder.createItemWithLore(Material.FIREWORK_ROCKET, 1, ChatColor.WHITE + "Rocket", Collections.singletonList("Launch the target in the air YEET")));
        VMConstants.TROLL_MENU.setItem(40, ItemBuilder.createItemWithLore(Material.PAPER, 1, ChatColor.WHITE + "Fake ban", Collections.singletonList("Make the target think they got banned")));
        VMConstants.TROLL_MENU.setItem(42, ItemBuilder.createItemWithLore(Material.ENCHANTED_GOLDEN_APPLE, 1, ChatColor.WHITE + "Fake op", Collections.singletonList("Make the target think they got op")));

        // Placeholders
        byte[] placeholderArray = new byte[]{0, 1, 2, 3, 5, 6, 7, 8, 45, 46, 52};
        for (byte slot : placeholderArray) {
            VMConstants.TROLL_MENU.setItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, 1, " ", Collections.singletonList("Just a lonely placeholder :(")));
        }

        // Placeholders
        byte[] placeholderArray2 = new byte[]{9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43};
        for (byte slot : placeholderArray2) {
            VMConstants.TROLL_MENU.setItem(slot, ItemBuilder.createItemWithLore(Material.GRAY_STAINED_GLASS_PANE, 1, " ", Collections.singletonList("Just a lonely placeholder :(")));
        }

        player.openInventory(VMConstants.TROLL_MENU);

        return true;
    }

}