/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.gaming12846.troll.utilitys.ConfigLoader;
import de.gaming12846.troll.utilitys.Vars;

public class BlacklistCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("trollblacklist")) {

			if (sender instanceof Player) {
				Player p = (Player) sender;

				if (p.hasPermission("troll.blacklist")) {

					if (args.length == 2) {
						Player t = Bukkit.getPlayer(args[1]);

						if (t != null) {
							if (args[0].equalsIgnoreCase("add")) {

								if (!Vars.blacklist.contains(t.getUniqueId().toString())) {
									Vars.blacklist.set(t.getUniqueId().toString(), t.getName());
									ConfigLoader.saveBlacklist();
									String addedBlacklist = "";
									addedBlacklist = Vars.Messages.addedBlacklist.replace("[Player]", args[1]);
									p.sendMessage(addedBlacklist);
								} else {
									String allreadyInBlacklist = "";
									allreadyInBlacklist = Vars.Messages.allreadyInBlacklist.replace("[Player]",
											args[1]);
									p.sendMessage(allreadyInBlacklist);
								}

							} else if (args[0].equalsIgnoreCase("remove")) {
								if (Vars.blacklist.contains(t.getUniqueId().toString())) {
									Vars.blacklist.set(t.getUniqueId().toString(), null);
									ConfigLoader.saveBlacklist();
									String removedBlacklist = "";
									removedBlacklist = Vars.Messages.removedBlacklist.replace("[Player]", args[1]);
									p.sendMessage(removedBlacklist);
								} else {
									String notInBlacklist = "";
									notInBlacklist = Vars.Messages.notInBlacklist.replace("[Player]", args[1]);
									p.sendMessage(notInBlacklist);
								}
							} else
								p.sendMessage(Vars.Messages.usageBlacklist);
						} else {
							String notOnline = "";
							notOnline = Vars.Messages.targetNotOnline.replace("[Player]", args[1]);
							p.sendMessage(notOnline);
						}
					} else
						p.sendMessage(Vars.Messages.usageBlacklist);
				} else
					p.sendMessage(Vars.Messages.noPermission);
			} else
				sender.sendMessage(Vars.Messages.noConsole);
		}
		return true;
	}
}
