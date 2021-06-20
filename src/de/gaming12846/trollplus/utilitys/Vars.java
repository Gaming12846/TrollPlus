/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.utilitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.gaming12846.trollplus.main.Main;

public class Vars {
	public static Player target = null;
	public static Inventory trollmenu = null;

	public static FileConfiguration blacklist = null;

	public static String prefix = "§8[§c§lTrollPlus§8]§f ";

	// Booleans
	public static class Booleans {
		public static boolean banMessageBroadcastOnOff = Main.getPlugin().getConfig().getBoolean("banmessage.banMessageBroadcastOnOff");
	}

	// Status
	public static class Status {
		public static String vanishStatus = "§c§lOFF";
		public static String freezeStatus = "§c§lOFF";
		public static String handitemdropStatus = "§c§lOFF";
		public static String controlStatus = "§c§lOFF";
		public static String spammessagesStatus = "§c§lOFF";
		public static String spamsoundsStatus = "§c§lOFF";
		public static String flipbehindStatus = "§c§lOFF";
		public static String semibanStatus = "§c§lOFF";
		public static String tnttrackStatus = "§c§lOFF";
	}

	// ArrayLists and Lists
	public static class Lists {
		public static ArrayList<String> vanishList = new ArrayList<>();
		public static ArrayList<String> freezeList = new ArrayList<>();
		public static ArrayList<String> handitemdropList = new ArrayList<>();
		public static ArrayList<String> controlList = new ArrayList<>();
		public static ArrayList<String> spammessagesList = new ArrayList<>();
		public static ArrayList<String> spamsoundsList = new ArrayList<>();
		public static ArrayList<String> flipbehindList = new ArrayList<>();
		public static ArrayList<String> semibanList = new ArrayList<>();
		public static ArrayList<String> tnttrackList = new ArrayList<>();

		public static List<String> spammessages = Main.getPlugin().getConfig().getStringList("spammessages");
	}

	// HashMaps
	public static class HashMaps {
		public static HashMap<Player, Player> controller = new HashMap<>();
	}

	// Messages
	public static class Messages {
		public static String noPermission = Main.getPlugin().getConfig().getString("messages.noPermission");
		public static String noConsole = prefix + Main.getPlugin().getConfig().getString("messages.noConsole");

		public static String usageTrollmenu = prefix + Main.getPlugin().getConfig().getString("messages.usageTrollmenu");
		public static String usageBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.usageBlacklist");;

		public static String targetNotOnline = prefix + Main.getPlugin().getConfig().getString("messages.targetNotOnline");
		public static String immune = prefix + Main.getPlugin().getConfig().getString("messages.immune");

		public static String semibanMessage = Main.getPlugin().getConfig().getString("messages.semibanMessage");

		public static String banMessagePlayer = Main.getPlugin().getConfig().getString("banmessage.banMessagePlayer");
		public static String banMessageBroadcast = Main.getPlugin().getConfig().getString("banmessage.banMessageBroadcast");

		public static String addedBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.addedBlacklist");
		public static String removedBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.removedBlacklist");
		public static String allreadyInBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.allreadyInBlacklist");
		public static String notInBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.notInBlacklist");
	}
}
