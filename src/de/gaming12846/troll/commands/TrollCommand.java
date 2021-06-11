/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.gaming12846.troll.utilitys.Items;
import de.gaming12846.troll.utilitys.Vars;

public class TrollCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("troll")) {

			if (sender instanceof Player) {
				Player p = (Player) sender;

				if (p.hasPermission("troll.opentrollmenu")) {

					if (args.length == 1) {
						Player t = Bukkit.getPlayer(args[0]);

						if (t != null) {
							Vars.target = t;

							Vars.trollmenu = Bukkit.createInventory(null, 27, "Troll §4" + Vars.target.getName());
							Vars.trollmenu.setItem(26, Items.createItem(Material.BARRIER, 0, "§fClose"));

							Vars.trollmenu.setItem(0,
									Items.createItem(Material.ICE, 0, "§fFreeze " + Vars.freezestatus));
							Vars.trollmenu.setItem(2, Items.createItem(Material.CHEST, 0, "§fInvSee"));
							Vars.trollmenu.setItem(4, Items.createItem(Material.WITHER_SKELETON_SKULL, 0, "§fKill"));
							Vars.trollmenu.setItem(6, Items.createItem(Material.FEATHER, 0,
									"§fAuto Hand Item Drop " + Vars.handitemdropstatus));
							Vars.trollmenu.setItem(8, Items.createItem(Material.LEGACY_BOOK_AND_QUILL, 0,
									"§fSpam Messages " + Vars.spammessagesstatus));
							Vars.trollmenu.setItem(9,
									Items.createItem(Material.ENDER_PEARL, 0, "§fTeleport to Player"));
							Vars.trollmenu.setItem(11,
									Items.createItem(Material.ENDER_EYE, 0, "§fControl " + Vars.controlstatus));
							Vars.trollmenu.setItem(13,
									Items.createItem(Material.NOTE_BLOCK, 0, "§fPlay Random Scary Sound"));
							Vars.trollmenu.setItem(15, Items.createItem(Material.PAPER, 0, "§fFake Ban"));
							Vars.trollmenu.setItem(17, Items.createItem(Material.MUSIC_DISC_BLOCKS, 0,
									"§fSpam Sounds " + Vars.spamsoundsstatus));
							Vars.trollmenu.setItem(18, Items.createItem(Material.EGG, 0, "§fLook behind"));

							p.openInventory(Vars.trollmenu);
						} else {
							String notonline = "";
							notonline = Vars.targetnotonline.replace("[Player]", args[0]);
							p.sendMessage(notonline);
						}

					} else
						p.sendMessage(Vars.usagetrollmenu);

				} else
					p.sendMessage(Vars.nopermission);

			} else
				sender.sendMessage(Vars.noconsole);

		}

		return false;
	}

}
