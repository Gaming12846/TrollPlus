/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.gaming12846.trollplus.utilitys.ConfigLoader;
import de.gaming12846.trollplus.utilitys.Vars;
import net.md_5.bungee.api.ChatColor;

public class BlacklistCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("trollblacklist")) {

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
								allreadyInBlacklist = Vars.Messages.allreadyInBlacklist.replace("[Player]",
										ChatColor.BOLD + args[1] + ChatColor.RESET);
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
