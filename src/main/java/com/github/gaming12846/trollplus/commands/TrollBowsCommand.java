package com.github.gaming12846.trollplus.commands;

import com.github.gaming12846.trollplus.utils.ItemBuilder;
import com.github.gaming12846.trollplus.utils.VMConstants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Collections;

/**
 * TrollPlus com.github.gaming12846.trollplus.commands TrollBowsCommand.java
 *
 * @author Gaming12846
 */
public class TrollBowsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + label + " command cannot be used from the console.");
            return true;
        }

        if (!sender.hasPermission(VMConstants.PERMISSION_TROLLBOWS)) {
            sender.sendMessage(ChatColor.RED + "You have insufficient permissions to perform this command!");
            return true;
        }

        if (args.length != 0) {
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label);
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
        byte[] placeholderArray = new byte[]{5, 6, 7};
        for (byte slot : placeholderArray) {
            bows_menu.setItem(slot, ItemBuilder.createItemWithLore(Material.GRAY_STAINED_GLASS_PANE, 1, 0, " ", Collections.singletonList("Just lonely placeholders :(")));
        }

        Player player = (Player) sender;
        player.openInventory(bows_menu);

        return false;
    }

}