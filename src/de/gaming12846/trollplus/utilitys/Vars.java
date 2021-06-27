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

	// Status
	public static class Status {
		public static String vanishStatus = "§c§lOFF";
		public static String freezeStatus = "§c§lOFF";
		public static String handItemDropStatus = "§c§lOFF";
		public static String controlStatus = "§c§lOFF";
		public static String spamMessagesStatus = "§c§lOFF";
		public static String spamSoundsStatus = "§c§lOFF";
		public static String flipBehindStatus = "§c§lOFF";
		public static String semiBanStatus = "§c§lOFF";
		public static String tntTrackStatus = "§c§lOFF";
		public static String mobSpawnerStatus = "§c§lOFF";
	}

	// ArrayLists and Lists
	public static class Lists {
		public static ArrayList<String> vanishList = new ArrayList<>();
		public static ArrayList<String> freezeList = new ArrayList<>();
		public static ArrayList<String> handItemDropList = new ArrayList<>();
		public static ArrayList<String> controlList = new ArrayList<>();
		public static ArrayList<String> spamMessagesList = new ArrayList<>();
		public static ArrayList<String> spamSoundsList = new ArrayList<>();
		public static ArrayList<String> flipBehindList = new ArrayList<>();
		public static ArrayList<String> semiBanList = new ArrayList<>();
		public static ArrayList<String> tntTrackList = new ArrayList<>();
		public static ArrayList<String> mobSpawnerList = new ArrayList<>();

		public static List<String> spamMessages = Main.getPlugin().getConfig().getStringList("spamessages");
	}

	// HashMaps
	public static class HashMaps {
		public static HashMap<Player, Player> controller = new HashMap<>();
	}

	// Booleans
	public static class Booleans {
		public static boolean banMessageBroadcastSwitch = Main.getPlugin().getConfig().getBoolean("switches.banMessageBroadcastSwitch");
	}

	// Messages
	public static class Messages {
		public static String noPermission = Main.getPlugin().getConfig().getString("messages.noPermission");
		public static String noConsole = prefix + Main.getPlugin().getConfig().getString("messages.noConsole");

		public static String usageTrollmenu = prefix + Main.getPlugin().getConfig().getString("messages.usageTrollmenu");
		public static String usageBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.usageBlacklist");;

		public static String targetNotOnline = prefix + Main.getPlugin().getConfig().getString("messages.targetNotOnline");
		public static String immune = prefix + Main.getPlugin().getConfig().getString("messages.immune");

		public static String addedBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.addedBlacklist");
		public static String removedBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.removedBlacklist");
		public static String allreadyInBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.allreadyInBlacklist");
		public static String notInBlacklist = prefix + Main.getPlugin().getConfig().getString("messages.notInBlacklist");

		public static String banMessagePlayer = Main.getPlugin().getConfig().getString("messages.banMessagePlayer");
		public static String banMessageBroadcast = Main.getPlugin().getConfig().getString("messages.banMessageBroadcast");
		public static String semiBanMessage = Main.getPlugin().getConfig().getString("messages.semiBanMessage");
	}
}
