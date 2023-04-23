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
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + label + " command cannot be used from the console");
            return true;
        }

        if (!sender.hasPermission(VMConstants.PERMISSION_ALL) || !sender.hasPermission(VMConstants.PERMISSION_TROLLBOWS)) {
            sender.sendMessage(VMConstants.PLUGIN_NO_PERMISSION);
            return true;
        }

        if (args.length != 0) {
            sender.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.WHITE + "Use /" + label);
            return true;
        }

        // Create bows menu
        VMConstants.BOWS_MENU = Bukkit.createInventory(null, 9, "Trollbows");

        // Add bows
        VMConstants.BOWS_MENU.setItem(8, ItemBuilder.createItemWithLore(Material.BARRIER, 1, ChatColor.RED + "Close", Collections.singletonList("Close the bow menu")));

        VMConstants.BOWS_MENU.setItem(0, ItemBuilder.createBow(1, "Explosion bow", Collections.singletonList("Gives the explosion bow")));
        VMConstants.BOWS_MENU.setItem(1, ItemBuilder.createBow(1, "TNT bow", Collections.singletonList("Gives the TNT bow")));
        VMConstants.BOWS_MENU.setItem(2, ItemBuilder.createBow(1, "Lightning bolt bow", Collections.singletonList("Gives the lightning bolt bow")));
        VMConstants.BOWS_MENU.setItem(3, ItemBuilder.createBow(1, "Silverfish bow", Collections.singletonList("Gives the silverfish bow")));

        // Placeholders
        byte[] placeholderArray = new byte[]{4, 5, 6, 7};
        for (byte slot : placeholderArray) {
            VMConstants.BOWS_MENU.setItem(slot, ItemBuilder.createItemWithLore(Material.GRAY_STAINED_GLASS_PANE, 1, " ", Collections.singletonList("Just a lonely placeholder :(")));
        }

        Player player = (Player) sender;
        player.openInventory(VMConstants.BOWS_MENU);

        return true;
    }
}