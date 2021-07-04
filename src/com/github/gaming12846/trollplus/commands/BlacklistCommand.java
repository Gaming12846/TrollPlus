package com.github.gaming12846.trollplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.gaming12846.trollplus.utils.ConfigLoader;
import com.github.gaming12846.trollplus.utils.Vars;

import net.md_5.bungee.api.ChatColor;

/**
 * TrollPlus com.github.gaming12846.trollplus.commands BlacklistCommand.java
 *
 * @author Gaming12846
 */
public class BlacklistCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("trollblacklist")) {

			if (sender.hasPermission("troll.blacklist")) {

				if (args.length == 2) {
					Player t = Bukkit.getPlayer(args[1]);

					if (t != null) {

						if (args[0].equalsIgnoreCase("add")) {

							if (!Vars.blacklist.contains(t.getUniqueId().toString())) {
								Vars.blacklist.set(t.getUniqueId().toString(), t.getName());
								ConfigLoader.saveBlacklist();
								String addedBlacklist = "";
								addedBlacklist = Vars.Messages.addedBlacklist.replace("[Player]", ChatColor.BOLD + args[1] + ChatColor.RESET);
								sender.sendMessage(addedBlacklist);
							} else {
								String allreadyInBlacklist = "";
								allreadyInBlacklist = Vars.Messages.allreadyInBlacklist.replace("[Player]", ChatColor.BOLD + args[1] + ChatColor.RESET);
								sender.sendMessage(allreadyInBlacklist);
							}

						} else if (args[0].equalsIgnoreCase("remove")) {

							if (Vars.blacklist.contains(t.getUniqueId().toString())) {
								Vars.blacklist.set(t.getUniqueId().toString(), null);
								ConfigLoader.saveBlacklist();
								String removedBlacklist = "";
								removedBlacklist = Vars.Messages.removedBlacklist.replace("[Player]", ChatColor.BOLD + args[1] + ChatColor.RESET);
								sender.sendMessage(removedBlacklist);
							} else {
								String notInBlacklist = "";
								notInBlacklist = Vars.Messages.notInBlacklist.replace("[Player]", ChatColor.BOLD + args[1] + ChatColor.RESET);
								sender.sendMessage(notInBlacklist);
							}

						} else
							sender.sendMessage(Vars.Messages.usageBlacklist);
					} else {
						String notOnline = Vars.Messages.targetNotOnline.replace("[Player]", ChatColor.BOLD + args[1] + ChatColor.RESET);
						sender.sendMessage(notOnline);
					}
				} else
					sender.sendMessage(Vars.Messages.usageBlacklist);
			} else
				sender.sendMessage(Vars.Messages.noPermission);
		}

		return true;
	}
}